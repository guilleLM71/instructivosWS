package com.example.ms_instructivos.aplication.usecases;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.ports.inputs.IInstructivosUseCase;
import com.example.ms_instructivos.domain.ports.outputs.IInstructivosRepositoryPort;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;

import java.util.List;
import java.util.Optional;

public class InstructivoUseCaseImpl implements IInstructivosUseCase {

    private final IInstructivosRepositoryPort iInstructivosRepositoryPort;

    public InstructivoUseCaseImpl(IInstructivosRepositoryPort iInstructivosRepositoryPort) {
        this.iInstructivosRepositoryPort = iInstructivosRepositoryPort;
    }


    @Override
    public Instructivo crearInstructivo(Instructivo instructivo) {
        return iInstructivosRepositoryPort.save(instructivo);
    }

    @Override
    public Optional<Instructivo> actualizarInstructivo(Integer id_instructivo, InstructivoDto instructivo) {
        return iInstructivosRepositoryPort.update(id_instructivo,instructivo);
    }

    @Override
    public boolean eliminarInstructivo(Integer id_instructivo) {
        return iInstructivosRepositoryPort.delete(id_instructivo);
    }

    @Override
    public Optional<Instructivo> obtenerInstructivo(Integer id_instructivo) {
        return iInstructivosRepositoryPort.findById(id_instructivo);
    }
    @Override
    public List<Instructivo> obtenerInstructivos() {
        return iInstructivosRepositoryPort.findAll();
    }

    @Override
    public Optional<Instructivo> actualizarVigenciaAprobado(Integer id_instructivo) {
        return iInstructivosRepositoryPort.update(id_instructivo);

    }
}
