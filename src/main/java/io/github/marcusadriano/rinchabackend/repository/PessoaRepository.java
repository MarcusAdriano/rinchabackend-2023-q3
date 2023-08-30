package io.github.marcusadriano.rinchabackend.repository;

import io.github.marcusadriano.rinchabackend.repository.entity.PessoaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PessoaRepository extends MongoRepository<PessoaDocument, String> {

    @Query("{$or: [{nome: {$regex: ?0, $options: 'i'}}, {apelido: {$regex: ?0, $options: 'i'}}, {stack: {$regex: ?0, $options: 'i'}}]}")
    List<PessoaDocument> findAllByPalavraChave(String palavraChave);

}
