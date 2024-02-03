package com.example.ms_instructivos.infrastructure.repositorys;

import com.example.ms_instructivos.domain.models.Instructivo;
import com.example.ms_instructivos.domain.ports.outputs.IInstructivosRepositoryPort;
import com.example.ms_instructivos.infrastructure.entitys.InstructivoEntity;
import com.example.ms_instructivos.infrastructure.repositorys.dto.InstructivoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaInstructivoRepositoryAdapter implements IInstructivosRepositoryPort {

    private final JpaInstructivoRepository jpaInstructivoRepository;

    public JpaInstructivoRepositoryAdapter(JpaInstructivoRepository jpaInstructivoRepository) {
        this.jpaInstructivoRepository = jpaInstructivoRepository;
    }

    @Override
    public Instructivo save(Instructivo instructivo) {
        InstructivoEntity instructivoEntity = InstructivoEntity.fromDomainModel(instructivo);
        InstructivoEntity instructivoEntitySaved = jpaInstructivoRepository.save(instructivoEntity);
        return instructivoEntitySaved.toDomainModel();
    }

    @Override
    public Optional<Instructivo> update(Integer id_instructivo, InstructivoDto instructivo) {
        if (jpaInstructivoRepository.existsById(id_instructivo)){
            Instructivo instructivoExiste = findById(id_instructivo).get();
            InstructivoDto instructivoDto = InstructivoEntity.fromDomainModel(instructivo);
            InstructivoEntity instructivoEntity = InstructivoEntity.fromDomainModel(instructivoExiste, instructivoDto);
            InstructivoEntity instructivoEntityUpdate = jpaInstructivoRepository.save(instructivoEntity);
            return Optional.of(instructivoEntityUpdate.toDomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id_instructivo) {
        if (jpaInstructivoRepository.existsById(id_instructivo)){
            jpaInstructivoRepository.deleteById(id_instructivo);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Instructivo> findById(Integer id_instructivo) {
        return jpaInstructivoRepository.findById(id_instructivo).map(InstructivoEntity::toDomainModel);
    }

    @Override
    public List<Instructivo> findAll() {
        return jpaInstructivoRepository.findAll().stream().map(InstructivoEntity::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Instructivo> update(Integer id_instructivo) {
        if (jpaInstructivoRepository.existsById(id_instructivo)){
            Instructivo instructivoExiste = findById(id_instructivo).get();
            instructivoExiste.setEstado(true);
            instructivoExiste.setVigencia(true);
            InstructivoEntity instructivoEntity = InstructivoEntity.fromDomainModel(instructivoExiste);
            InstructivoEntity instructivoEntityUpdate = jpaInstructivoRepository.save(instructivoEntity);
            return Optional.of(instructivoEntityUpdate.toDomainModel());
        }
        return Optional.empty();
    }
}
