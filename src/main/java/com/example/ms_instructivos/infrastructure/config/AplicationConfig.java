package com.example.ms_instructivos.infrastructure.config;

import com.example.ms_instructivos.aplication.services.InstructivoService;
import com.example.ms_instructivos.aplication.usecases.InstructivoUseCaseImpl;
import com.example.ms_instructivos.domain.ports.outputs.IInstructivosRepositoryPort;
import com.example.ms_instructivos.infrastructure.adapters.ExternalServiceAdapter;
import com.example.ms_instructivos.infrastructure.repositorys.JpaInstructivoRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class AplicationConfig {

    @Bean
    public InstructivoService instructivoService(IInstructivosRepositoryPort iInstructivosRepositoryPort){
        return new InstructivoService(new InstructivoUseCaseImpl(iInstructivosRepositoryPort));
    }

    @Bean
    public IInstructivosRepositoryPort iInstructivosRepositoryPort(JpaInstructivoRepositoryAdapter jpaInstructivoRepositoryAdapter){
        return jpaInstructivoRepositoryAdapter;
    }

    @Bean
    public ExternalServiceAdapter externalServiceAdapter(){
        return new ExternalServiceAdapter();
    }



}
