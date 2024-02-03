package com.example.ms_instructivos.aplication.services;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.ports.inputs.IInstructivosUseCase;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;

import java.util.List;
import java.util.Optional;

public class InstructivoService implements IInstructivosUseCase {

   private final IInstructivosUseCase iInstructivosUseCase;

    public InstructivoService(IInstructivosUseCase iInstructivosUseCase) {
        this.iInstructivosUseCase = iInstructivosUseCase;
    }

    @Override
    public Instructivo crearInstructivo(Instructivo instructivo) {
        return iInstructivosUseCase.crearInstructivo(instructivo);
    }

    @Override
    public Optional<Instructivo> actualizarInstructivo(Integer id_instructivo, InstructivoDto instructivo) {
        return iInstructivosUseCase.actualizarInstructivo(id_instructivo, instructivo);
    }

    @Override
    public boolean eliminarInstructivo(Integer id_instructivo) {
        return iInstructivosUseCase.eliminarInstructivo(id_instructivo);
    }

    @Override
    public Optional<Instructivo> obtenerInstructivo(Integer id_instructivo) {
        return iInstructivosUseCase.obtenerInstructivo(id_instructivo);
    }

    @Override
    public List<Instructivo> obtenerInstructivos() {
        return iInstructivosUseCase.obtenerInstructivos();
    }

    @Override
    public Optional<Instructivo> actualizarVigenciaAprobado(Integer id_instructivo) {
        return iInstructivosUseCase.actualizarVigenciaAprobado(id_instructivo);

    }
}
