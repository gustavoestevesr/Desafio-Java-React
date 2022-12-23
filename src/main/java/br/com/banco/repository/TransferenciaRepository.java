package br.com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.banco.models.TransferenciaModel;

@Repository
public interface TransferenciaRepository extends JpaRepository <TransferenciaModel, Long>{
       
}
