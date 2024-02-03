package com.example.ms_instructivos.infrastructure.repositorys;

import com.example.ms_instructivos.infrastructure.entitys.InstructivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInstructivoRepository extends JpaRepository<InstructivoEntity, Integer>{
}
