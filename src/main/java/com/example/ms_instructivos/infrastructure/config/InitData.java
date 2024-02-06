package com.example.ms_instructivos.infrastructure.config;

import com.example.ms_instructivos.domain.models.Tipo;
import com.example.ms_instructivos.infrastructure.entitys.TipoInstructivoEntity;
import com.example.ms_instructivos.infrastructure.repositorys.JpaTipoInstructivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private JpaTipoInstructivoRepository jpaTipoInstructivoRepository;
    @Override
    public void run(String... args) throws Exception {
        if (jpaTipoInstructivoRepository.count() == 0){
            jpaTipoInstructivoRepository.save(TipoInstructivoEntity.fromDomainModel(new Tipo("Guia")));
            jpaTipoInstructivoRepository.save(TipoInstructivoEntity.fromDomainModel(new Tipo("Instructivo")));
            jpaTipoInstructivoRepository.save(TipoInstructivoEntity.fromDomainModel(new Tipo("Politica")));

        }

    }
}
