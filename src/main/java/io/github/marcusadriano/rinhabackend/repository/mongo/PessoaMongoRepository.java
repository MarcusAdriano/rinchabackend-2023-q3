package io.github.marcusadriano.rinhabackend.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PessoaMongoRepository extends MongoRepository<PessoaDocument, String> {

    @Query("{ $or : [ { stack : { $regex: ?0, $options: 'i' } }, " +
            "{ apelido : { $regex: ?0, $options: 'i' } }, " +
            "{ nome : { $regex: ?0, $options: 'i' } } ] }")
    List<PessoaDocument> findAllByFilter(String palavraChave);
}
