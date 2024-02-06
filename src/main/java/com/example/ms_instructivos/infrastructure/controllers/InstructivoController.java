package com.example.ms_instructivos.infrastructure.controllers;

import com.example.ms_instructivos.aplication.services.InstructivoService;
import com.example.ms_instructivos.aplication.services.TipoInstructivoService;
import com.example.ms_instructivos.aplication.usecases.AddImageToPDFUseCaseImpl;
import com.example.ms_instructivos.aplication.usecases.FileUploadUseCaseImpl;
import com.example.ms_instructivos.aplication.usecases.GeneratorQRUseCaseImpl;
import com.example.ms_instructivos.aplication.usecases.PDFWaterMarkUseCaseImpl;
import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.ports.inputs.IAddImageToPDFUseCase;
import com.example.ms_instructivos.domain.ports.inputs.IFileUploadUseCase;
import com.example.ms_instructivos.domain.ports.inputs.IGeneratorQRUseCase;
import com.example.ms_instructivos.domain.ports.inputs.IPDFWaterMarkUseCase;
import com.example.ms_instructivos.domain.ports.outputs.ExternalMinioServicePort;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;
import jakarta.transaction.Transactional;
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
    private final TipoInstructivoService tipoInstructivoService;
    private final IFileUploadUseCase fileUploadUseCase;
    private final IGeneratorQRUseCase generatorQRUseCase;
    private final IAddImageToPDFUseCase addImageToPDFUseCase;
    private final ExternalMinioServicePort externalMinioServicePort;
    private final IPDFWaterMarkUseCase pdfWaterMarkUseCase;

    public InstructivoController(InstructivoService instructivoService, TipoInstructivoService tipoInstructivoService, FileUploadUseCaseImpl fileUploadUseCase, GeneratorQRUseCaseImpl generatorQRUseCase, AddImageToPDFUseCaseImpl addImageToPDFUseCase, ExternalMinioServicePort externalMinioServicePort, PDFWaterMarkUseCaseImpl pdfWaterMarkUseCase) {
        this.instructivoService = instructivoService;
        this.tipoInstructivoService = tipoInstructivoService;
        this.fileUploadUseCase = fileUploadUseCase;
        this.generatorQRUseCase = generatorQRUseCase;
        this.addImageToPDFUseCase = addImageToPDFUseCase;
        this.externalMinioServicePort = externalMinioServicePort;
        this.pdfWaterMarkUseCase = pdfWaterMarkUseCase;
    }

    @PostMapping
    public ResponseEntity<Instructivo> create( @RequestParam("nombre") String nombre,
                                               @RequestParam("version") double version,
                                               @RequestParam("clasificacion") String clasificacion,
                                               @RequestParam("codigo") String codigo,
                                               @RequestParam("responsable") String responsable,
                                               @RequestParam("tipo") Integer tipo,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        Instructivo instructivoResponse = instructivoService.crearInstructivo(new Instructivo(nombre,version,clasificacion,codigo,responsable, tipoInstructivoService.obtenerTipoInstructivo(tipo).get()));
        String path = fileUploadUseCase.uploadFile(file.getInputStream(), (instructivoResponse.getId_instructivo()+".pdf"));
        generatorQRUseCase.generarQR(instructivoResponse.getId_instructivo()+"");
        String pathQR = addImageToPDFUseCase.generatePdfWithImage(instructivoResponse.getId_instructivo()+"");
        externalMinioServicePort.uploadFile(pathQR, "instructivos_originales");
        externalMinioServicePort.uploadFile(path, "instructivos_escaneados");
        return ResponseEntity.status(HttpStatus.CREATED).body(instructivoResponse);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Instructivo> findById(@PathVariable Integer id){
        return instructivoService.obtenerInstructivo(id)
                .map(Instructivo -> new ResponseEntity<>(Instructivo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @Transactional
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
                                              @RequestParam("tipo") Integer tipo,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String path = fileUploadUseCase.uploadFile(file.getInputStream(), (id+".pdf"));
        generatorQRUseCase.generarQR(id+"");
        String pathQR = addImageToPDFUseCase.generatePdfWithImage(id+"");
        externalMinioServicePort.uploadFile(pathQR, "instructivos_originales");
        return instructivoService.actualizarInstructivo(id,new InstructivoDto(nombre,version,clasificacion,codigo,responsable, tipoInstructivoService.obtenerTipoInstructivo(tipo).get()))
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Instructivo> delete(@PathVariable Integer id){
        return instructivoService.eliminarInstructivo(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<Instructivo> updateAnular(@PathVariable Integer id) throws IOException {
        String path = externalMinioServicePort.getFile(id+".pdf");
        pdfWaterMarkUseCase.addWatermark(path,path,"NO VIGENTE");
        externalMinioServicePort.uploadFile(path, "instructivos_escaneados");
        return instructivoService.anularInstructivo(id)
                .map(Instructivo -> new ResponseEntity<>(Instructivo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/download_originales/{id}")
    public ResponseEntity<String> downloadOriginal(@PathVariable Integer id) {
        String path = externalMinioServicePort.getPresignedObjectUrl(id+".pdf","instructivos_originales");
        return new ResponseEntity<>(path,HttpStatus.OK);
    }
    @GetMapping("/download_escaneados/{id}")
    public ResponseEntity<String> downloadEscaneado(@PathVariable Integer id) {
        String path = externalMinioServicePort.getPresignedObjectUrl(id+".pdf","instructivos_escaneados");
        return new ResponseEntity<>(path,HttpStatus.OK);
    }


}

