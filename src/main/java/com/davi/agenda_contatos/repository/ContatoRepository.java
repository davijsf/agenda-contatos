package com.davi.agenda_contatos.repository;

import com.davi.agenda_contatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{
    Optional<Contato> findByTelefone(String telefone);
    Optional<Contato> findByEmail(String email);
}
