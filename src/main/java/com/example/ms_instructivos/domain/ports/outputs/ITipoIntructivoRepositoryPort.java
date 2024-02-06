package com.example.ms_instructivos.domain.ports.outputs;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;

import java.util.List;
import java.util.Optional;

public interface ITipoIntructivoRepositoryPort {
    Tipo save(Tipo tipo);
    Optional<Tipo> update(Integer id, Tipo tipo);
    boolean delete(Integer id);
    Optional<Tipo> findById(Integer id);
    List<Tipo> findAll();


}
