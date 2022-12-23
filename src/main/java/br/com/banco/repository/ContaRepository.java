package br.com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.banco.models.ContaModel;

@Repository
public interface ContaRepository extends JpaRepository <ContaModel, Long>{
    
}
