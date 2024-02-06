package com.example.ms_instructivos.aplication.services;

import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.domain.ports.inputs.ITipoInstructivoUseCase;

import java.util.Optional;

public class TipoInstructivoService implements ITipoInstructivoUseCase{
    private final ITipoInstructivoUseCase iTipoInstructivoUseCase;

    public TipoInstructivoService(ITipoInstructivoUseCase iTipoInstructivoUseCase) {
        this.iTipoInstructivoUseCase = iTipoInstructivoUseCase;
    }

    @Override
    public Tipo crearTipoInstructivo(Tipo tipo) {
        return iTipoInstructivoUseCase.crearTipoInstructivo(tipo);
    }

    @Override
    public Optional<Tipo> actualizarTipoInstructivo(Integer id, Tipo tipo) {
        return iTipoInstructivoUseCase.actualizarTipoInstructivo(id, tipo);
    }

    @Override
    public boolean eliminarTipoInstructivo(Integer id) {
        return iTipoInstructivoUseCase.eliminarTipoInstructivo(id);
    }

    @Override
    public Optional<Tipo> obtenerTipoInstructivo(Integer id) {
        return iTipoInstructivoUseCase.obtenerTipoInstructivo(id);
    }

    @Override
    public Iterable<Tipo> obtenerTiposInstructivos() {
        return iTipoInstructivoUseCase.obtenerTiposInstructivos();
    }
}
