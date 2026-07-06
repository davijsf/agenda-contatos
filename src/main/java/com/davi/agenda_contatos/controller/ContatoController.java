package com.davi.agenda_contatos.controller;

import com.davi.agenda_contatos.model.Contato;
import com.davi.agenda_contatos.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    private ContatoService service;

    // Listar todos os contatos
    @GetMapping
    public List<Contato> listar() {
        return service.listarTodos();
    }

    // Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        // O método do service retorna um optional
        // se o contato existir, retorna 200 OK com o contato
        // se não, retorna 404 Not Found para o usuário
        return service.buscarPorId(id)
                .map(contato -> ResponseEntity.ok(contato))
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar ou Salvar contato
    @PostMapping
    public ResponseEntity<Contato> salvar (
        @Valid @RequestBody Contato contato) {
        return ResponseEntity.status(201).body( // retorna 201 Created
            service.Salvar(contato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Primeiro verifica se o contato existe antes de deletar
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(id);
        // status 204 No Content ideal para 'deletes' com sucesso
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @Valid @RequestBody Contato contatoAtualizado) {

        // Verifica se o contato existe no banco, antes de atualizar
        return service.buscarPorId(id).map(contatoExistente -> {

            // Atualiza os dados do contato do banco com os novos dados
            contatoExistente.setNome(contatoAtualizado.getNome());
            contatoExistente.setTelefone(contatoAtualizado.getTelefone());
            contatoExistente.setEmail(contatoAtualizado.getEmail());

            // Passa o objeto atualizado para o service.salvar()
            Contato contatoSalvo = service.Salvar(contatoExistente);
            return ResponseEntity.ok(contatoSalvo); // retorna 200 OK com o contato atualizado

        }).orElse(ResponseEntity.notFound().build()); // retorna 404 se o ID não existir
    }

}
