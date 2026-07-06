package com.davi.agenda_contatos.validation;

import com.davi.agenda_contatos.model.Contato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.davi.agenda_contatos.repository.ContatoRepository;

import java.util.Optional;

@Component // Avisa ao Spring que esta classe pode ser injetada em outras
public class ContatoValidator {

    @Autowired
    private ContatoRepository contatoRepository;

    public void validar(Contato contato) {

        // 1. Validação de telefone
        if (contato.getTelefone() != null) {
            // Remove letras e parênteses:
            String telefoneApenasNumeros = contato.getTelefone().replaceAll("\\D", "");
            if (contato.getTelefone().length() < 10 || telefoneApenasNumeros.length() > 11) {
                throw new IllegalArgumentException("Telefone inválido. Deve conter DDD + Número.");
            }
        }

        // 2. Validação se o telefone já existe
        Optional<Contato> contatoExistente = contatoRepository.findByTelefone(contato.getTelefone());

        // Se a caixa do Optional não estiver vazia, significa que alguém já utiliza o número
        if (contatoExistente.isPresent()) {
            if (!contatoExistente.get().getId().equals(contato.getId())) {
                throw new IllegalArgumentException("Este telefone está cadastrado em outro contato!");
            }
        }

        // 3. Validação se o email já existe
        Optional<Contato> emailExistente = contatoRepository.findByEmail(contato.getEmail());

        if (emailExistente.isPresent()) {
            throw new IllegalArgumentException(("Este email está cadastrado em outro contato!"));
        }
    }
}
