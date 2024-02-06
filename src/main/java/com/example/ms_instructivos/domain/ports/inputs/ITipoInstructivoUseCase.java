package com.example.ms_instructivos.domain.ports.inputs;

import com.example.ms_instructivos.domain.models.Tipo;

import java.util.Optional;

public interface ITipoInstructivoUseCase {
    Tipo crearTipoInstructivo(Tipo tipo);
    Optional<Tipo> actualizarTipoInstructivo(Integer id, Tipo tipo);
    boolean eliminarTipoInstructivo(Integer id);
    Optional<Tipo> obtenerTipoInstructivo(Integer id);
    Iterable<Tipo> obtenerTiposInstructivos();

}
