package io.github.marcusadriano.rinhabackend.service;

import io.github.marcusadriano.rinhabackend.dto.api.CreatePessoaRequest;
import io.github.marcusadriano.rinhabackend.dto.api.PessoaResponse;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    String create(CreatePessoaRequest createPessoaRequest);

    Optional<PessoaResponse> findById(String id);

    List<PessoaResponse> findByFilter(String filter);

    Long count();
}
