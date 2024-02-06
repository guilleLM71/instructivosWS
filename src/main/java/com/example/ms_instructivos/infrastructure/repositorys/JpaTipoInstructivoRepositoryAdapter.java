package com.example.ms_instructivos.infrastructure.repositorys;

import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.domain.ports.outputs.ITipoIntructivoRepositoryPort;
import com.example.ms_instructivos.infrastructure.entitys.InstructivoEntity;
import com.example.ms_instructivos.infrastructure.entitys.TipoInstructivoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaTipoInstructivoRepositoryAdapter implements ITipoIntructivoRepositoryPort {

    public final JpaTipoInstructivoRepository jpaTipoInstructivoRepository;

    public JpaTipoInstructivoRepositoryAdapter(JpaTipoInstructivoRepository jpaTipoInstructivoRepository) {
        this.jpaTipoInstructivoRepository = jpaTipoInstructivoRepository;
    }

    @Override
    public Tipo save(Tipo tipo) {
        TipoInstructivoEntity tipoInstructivoEntity = TipoInstructivoEntity.fromDomainModel(tipo);
        TipoInstructivoEntity tipoInstructivoEntitySaved = jpaTipoInstructivoRepository.save(tipoInstructivoEntity);
        return tipoInstructivoEntitySaved.toDomainModel();
    }

    @Override
    public Optional<Tipo> update(Integer id, Tipo tipo) {
        if (jpaTipoInstructivoRepository.existsById(id)) {
            Tipo tipoExiste = findById(id).get();
            TipoInstructivoEntity tipoInstructivoEntity = TipoInstructivoEntity.fromDomainModel(tipoExiste);
            TipoInstructivoEntity tipoInstructivoEntityUpdate = jpaTipoInstructivoRepository.save(tipoInstructivoEntity);
            return Optional.of(tipoInstructivoEntityUpdate.toDomainModel());
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        if (jpaTipoInstructivoRepository.existsById(id)) {
            jpaTipoInstructivoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Tipo> findById(Integer id) {
        return jpaTipoInstructivoRepository.findById(id).map(TipoInstructivoEntity::toDomainModel);
    }

    @Override
    public List<Tipo> findAll() {
        return jpaTipoInstructivoRepository.findAll().stream().map(TipoInstructivoEntity::toDomainModel).collect(Collectors.toList());
    }
}
