package io.github.marcusadriano.rinhabackend.repository;

import io.github.marcusadriano.rinhabackend.repository.mongo.PessoaDocument;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository {

    PessoaDocument save(PessoaDocument pessoa);

    Optional<PessoaDocument> findById(String id);

    List<PessoaDocument> findAllByFilter(String palavraChave);

    Long count();

}
