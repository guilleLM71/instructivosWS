package com.example.ms_instructivos.domain.ports.outputs;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;

import java.util.List;
import java.util.Optional;

public interface IInstructivosRepositoryPort {

    Instructivo save(Instructivo instructivo);
    Optional<Instructivo> update(Integer id_instrutivo, InstructivoDto instructivo);
    boolean delete(Integer id_instructivo);
    Optional<Instructivo> findById(Integer id_instructivo);
    List<Instructivo> findAll();
    Optional<Instructivo> update(Integer id_instructivo, boolean anular);


}
