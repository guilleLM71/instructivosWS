package com.example.ms_instructivos.infrastructure.repositorys.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructivoDto {
    private String nombre;
    private double version;
    private String clasificacion;
    private String codigo;
    private String responsable;
}
