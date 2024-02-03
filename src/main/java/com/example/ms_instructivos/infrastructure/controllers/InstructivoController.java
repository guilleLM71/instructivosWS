package com.example.ms_instructivos.infrastructure.controllers;

import com.example.ms_instructivos.aplication.services.InstructivoService;
import com.example.ms_instructivos.aplication.usecases.AddImageToPDFUseCaseImpl;
import com.example.ms_instructivos.aplication.usecases.FileUploadUseCaseImpl;
import com.example.ms_instructivos.aplication.usecases.GeneratorQRUseCaseImpl;
import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.ports.outputs.ExternalMinioServicePort;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructivos")
public class InstructivoController {

    private final InstructivoService instructivoService;
    private final FileUploadUseCaseImpl fileUploadUseCase;
    private final GeneratorQRUseCaseImpl generatorQRUseCase;
    private final AddImageToPDFUseCaseImpl addImageToPDFUseCase;
    private final ExternalMinioServicePort externalMinioServicePort;

    public InstructivoController(InstructivoService instructivoService, FileUploadUseCaseImpl fileUploadUseCase, GeneratorQRUseCaseImpl generatorQRUseCase, AddImageToPDFUseCaseImpl addImageToPDFUseCase, ExternalMinioServicePort externalMinioServicePort) {
        this.instructivoService = instructivoService;
        this.fileUploadUseCase = fileUploadUseCase;
        this.generatorQRUseCase = generatorQRUseCase;
        this.addImageToPDFUseCase = addImageToPDFUseCase;
        this.externalMinioServicePort = externalMinioServicePort;
    }

    @PostMapping
    public ResponseEntity<Instructivo> create( @RequestParam("nombre") String nombre,
                                               @RequestParam("version") double version,
                                               @RequestParam("clasificacion") String clasificacion,
                                               @RequestParam("codigo") String codigo,
                                               @RequestParam("responsable") String responsable,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        Instructivo instructivoResponse = instructivoService.crearInstructivo(new Instructivo(nombre,version,clasificacion,codigo,responsable));
        String path = fileUploadUseCase.uploadFile(file.getInputStream(), (instructivoResponse.getId_instructivo()+".pdf"));
        generatorQRUseCase.generarQR(instructivoResponse.getId_instructivo()+"");
        String pathQR = addImageToPDFUseCase.generatePdfWithImage(instructivoResponse.getId_instructivo()+"");
        externalMinioServicePort.uploadFile(pathQR, "instructivos_originales");
        externalMinioServicePort.uploadFile(path, "instructivos_escaneados");
        return ResponseEntity.status(HttpStatus.CREATED).body(instructivoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructivo> findById(@PathVariable Integer id){
        return instructivoService.obtenerInstructivo(id)
                .map(Instructivo -> new ResponseEntity<>(Instructivo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Instructivo>> findAll(){
        List<Instructivo> instructivos = instructivoService.obtenerInstructivos();
        return new ResponseEntity<>(instructivos,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructivo> update(@PathVariable Integer id,
                                              @RequestParam("nombre") String nombre,
                                              @RequestParam("version") double version,
                                              @RequestParam("clasificacion") String clasificacion,
                                              @RequestParam("codigo") String codigo,
                                              @RequestParam("responsable") String responsable,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String path = fileUploadUseCase.uploadFile(file.getInputStream(), (id+".pdf"));
        generatorQRUseCase.generarQR(id+"");
        String pathQR = addImageToPDFUseCase.generatePdfWithImage(id+"");
        externalMinioServicePort.uploadFile(pathQR, "instructivos_originales");
        return instructivoService.actualizarInstructivo(id,new InstructivoDto(nombre,version,clasificacion,codigo,responsable))
                .map(Instructivo -> new ResponseEntity<>(Instructivo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/vigencia_aprobado/{id}")
    public ResponseEntity<Instructivo> updateVigenciaAprobado(@PathVariable Integer id,
                                                              @RequestParam("file") MultipartFile file) throws IOException {
        String path = fileUploadUseCase.uploadFile(file.getInputStream(), (id+".pdf"));
        externalMinioServicePort.uploadFile(path, "instructivos_escaneados");
        return instructivoService.actualizarVigenciaAprobado(id)
                .map(Instructivo -> new ResponseEntity<>(Instructivo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

