package com.example.ms_instructivos.infrastructure.entitys;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.models.Tipo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tipoinstructivo")
public class TipoInstructivoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    public static TipoInstructivoEntity fromDomainModel(Tipo tipo) {
        return new TipoInstructivoEntity(
                tipo.getId(),
                tipo.getNombre()
        );
    }

    public Tipo toDomainModel() {
        return new Tipo(
                id,
                nombre
        );
    }
}
