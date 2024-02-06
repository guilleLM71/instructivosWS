package com.example.ms_instructivos.infrastructure.entitys;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "instructivo")

public class InstructivoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_instructivo_id")
    private TipoInstructivoEntity tipoInstructivo;


    public static InstructivoEntity fromDomainModel(Instructivo instructivo){
        return new InstructivoEntity(
                instructivo.getId_instructivo(),
                instructivo.getNombre(),
                instructivo.getVersion(),
                instructivo.isVigencia(),
                instructivo.isEstado(),
                instructivo.getFecha_inicio(),
                instructivo.getFecha_fin(),
                instructivo.getClasificacion(),
                instructivo.getCodigo(),
                instructivo.getResponsable(),
                TipoInstructivoEntity
                        .builder()
                        .id(instructivo.getTipoInstructivo().getId())
                        .nombre(instructivo.getTipoInstructivo().getNombre())
                        .build()
        );
    }

    public static InstructivoDto fromDomainModel(InstructivoDto instructivo){
        return new InstructivoDto(
                instructivo.getNombre(),
                instructivo.getVersion(),
                instructivo.getClasificacion(),
                instructivo.getCodigo(),
                instructivo.getResponsable(),
                instructivo.getTipo()
        );
    }

    public static InstructivoEntity fromDomainModel(Instructivo instructivoExiste, InstructivoDto instructivoDto) {
        return new InstructivoEntity(
                instructivoExiste.getId_instructivo(),
                instructivoDto.getNombre(),
                instructivoDto.getVersion(),
                instructivoExiste.isVigencia(),
                instructivoExiste.isEstado(),
                instructivoExiste.getFecha_inicio(),
                instructivoExiste.getFecha_fin(),
                instructivoDto.getClasificacion(),
                instructivoDto.getCodigo(),
                instructivoDto.getResponsable(),
                TipoInstructivoEntity.builder().id(instructivoDto.getTipo().getId())
                        .nombre(instructivoDto.getTipo().getNombre())
                        .build()

        );
    }

    public Instructivo toDomainModel(){
        return new Instructivo(
                id_instructivo,
                nombre,
                version,
                vigencia,
                estado,
                fecha_inicio,
                fecha_fin,
                clasificacion,
                codigo,
                responsable,
                tipoInstructivo.toDomainModel()



        );

    }


}
