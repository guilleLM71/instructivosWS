package com.example.ms_instructivos.domain.models;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Instructivo {
    private int id_instructivo;
    private String nombre;
    private double version;
    private boolean vigencia;
    private boolean estado;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String clasificacion;
    private String codigo;
    private String responsable;

    public Instructivo(String nombre, double version, String clasificacion, String codigo, String responsable) {
        this.nombre = nombre;
        this.version = version;
        this.vigencia = false;
        this.estado = false;
        this.fecha_inicio = new Date();
        this.fecha_fin = null;
        this.clasificacion = clasificacion;
        this.codigo = codigo;
        this.responsable = responsable;
    }

}
