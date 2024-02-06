package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.domain.ports.inputs.ITipoInstructivoUseCase;
import com.example.ms_instructivos.domain.ports.outputs.ITipoIntructivoRepositoryPort;

import java.util.Optional;

public class TipoInstructivoUseCaseImpl implements ITipoInstructivoUseCase {
     private final ITipoIntructivoRepositoryPort iTipoInstructivoRepositoryPort;

    public TipoInstructivoUseCaseImpl(ITipoIntructivoRepositoryPort iTipoInstructivoRepositoryPort) {
        this.iTipoInstructivoRepositoryPort = iTipoInstructivoRepositoryPort;
    }

    @Override
    public Tipo crearTipoInstructivo(Tipo tipo) {
        return iTipoInstructivoRepositoryPort.save(tipo);
    }

    @Override
    public Optional<Tipo> actualizarTipoInstructivo(Integer id, Tipo tipo) {
        return iTipoInstructivoRepositoryPort.update(id, tipo);
    }

    @Override
    public boolean eliminarTipoInstructivo(Integer id) {
        return iTipoInstructivoRepositoryPort.delete(id);
    }

    @Override
    public Optional<Tipo> obtenerTipoInstructivo(Integer id) {
        return iTipoInstructivoRepositoryPort.findById(id);
    }

    @Override
    public Iterable<Tipo> obtenerTiposInstructivos() {
        return iTipoInstructivoRepositoryPort.findAll();
    }
}
