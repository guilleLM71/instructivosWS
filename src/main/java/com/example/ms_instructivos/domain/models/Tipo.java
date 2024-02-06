package com.example.ms_instructivos.domain.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Tipo {
    private Integer id;

    private String nombre;

    public Tipo(String nombre) {
        this.nombre = nombre;
    }
}
