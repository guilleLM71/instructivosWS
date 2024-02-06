package com.example.ms_instructivos.infrastructure.controllers;

import com.example.ms_instructivos.aplication.services.InstructivoService;
import com.example.ms_instructivos.aplication.services.TipoInstructivoService;
import com.example.ms_instructivos.domain.models.Tipo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipo-instructivos")
public class TipoInstructivoController {
    private final TipoInstructivoService tipoInstructivoService;

    public TipoInstructivoController(TipoInstructivoService tipoInstructivoService) {
        this.tipoInstructivoService = tipoInstructivoService;

    }

    @PostMapping
    public ResponseEntity<Tipo> create(@RequestBody Tipo tipo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoInstructivoService.crearTipoInstructivo(tipo));
    }

    @GetMapping
    public ResponseEntity<Iterable<Tipo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tipoInstructivoService.obtenerTiposInstructivos());
    }


}
