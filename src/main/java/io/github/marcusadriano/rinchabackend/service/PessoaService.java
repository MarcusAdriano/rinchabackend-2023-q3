package io.github.marcusadriano.rinchabackend.service;

import io.github.marcusadriano.rinchabackend.dto.api.CreatePessoaRequest;
import io.github.marcusadriano.rinchabackend.dto.api.PessoaResponse;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    PessoaResponse create(CreatePessoaRequest createPessoaRequest);

    Optional<PessoaResponse> findById(String id);

    List<PessoaResponse> findByFilter(String filter);

    Long count();
}
