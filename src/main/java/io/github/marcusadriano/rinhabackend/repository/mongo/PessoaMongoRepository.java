package io.github.marcusadriano.rinhabackend.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoaMongoRepository extends MongoRepository<PessoaDocument, String> {

}
