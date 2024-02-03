package com.example.ms_instructivos.domain.ports.inputs;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;
import io.micrometer.observation.ObservationFilter;

import java.util.List;
import java.util.Optional;

public interface IInstructivosUseCase {
    Instructivo crearInstructivo(Instructivo instructivo);
    Optional<Instructivo> actualizarInstructivo(Integer id_instructivo, InstructivoDto instructivo);

    boolean eliminarInstructivo(Integer id_instructivo);

    Optional<Instructivo> obtenerInstructivo(Integer id_instructivo);

    List<Instructivo> obtenerInstructivos();
    Optional<Instructivo> actualizarVigenciaAprobado(Integer id_instructivo);
}
