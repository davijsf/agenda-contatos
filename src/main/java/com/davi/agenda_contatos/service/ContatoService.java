package com.davi.agenda_contatos.service;

import com.davi.agenda_contatos.model.Contato;
import com.davi.agenda_contatos.repository.ContatoRepository;
import com.davi.agenda_contatos.validation.ContatoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Avisa ao Spring que esta é uma classe de Serviço
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoValidator contatoValidator;

    // 1. Método para listar todos os contatos
    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    // 2. Método para Buscar um contato por ID
    public Optional<Contato> buscarPorId(Long id) {
        // Retorna "Optional" porque o contato com esse ID pode não existir
        // Optional evita o erro de "NullPointerException"
        return contatoRepository.findById(id);
    }

    // 3.Método para Salvar ou Criar um novo Contato
    public Contato Salvar(Contato contato) {
        // Chama a pasta de validação
        contatoValidator.validar(contato);

        // se não disparar nenhum erro, ele continua e salva
        return contatoRepository.save(contato);
    }

    // 4. Método para Deletar um contato
    public void deletar(Long id) {
        contatoRepository.deleteById(id);
    }
}
