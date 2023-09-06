package io.github.marcusadriano.rinhabackend.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marcusadriano.rinhabackend.repository.PessoaRepository;
import io.github.marcusadriano.rinhabackend.repository.mongo.PessoaDocument;
import io.github.marcusadriano.rinhabackend.repository.mongo.PessoaMongoRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PessoaCachedRepositoryImpl implements PessoaRepository {

    private final PessoaMongoRepository mongoRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper;
    private final MongoTemplate mongoTemplate;

    public PessoaCachedRepositoryImpl(final PessoaMongoRepository mongoRepository, final RedisTemplate<String, Object> redisTemplate, final ObjectMapper mapper, final MongoTemplate mongoTemplate) {
        this.mongoRepository = mongoRepository;
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public PessoaDocument save(final PessoaDocument pessoa) {

        if (BooleanUtils.isTrue(redisTemplate.hasKey(pessoa.getApelido()))) {
            return null;
        }

        redisTemplate.opsForValue().set(pessoa.getApelido(), "");
        redisTemplate.opsForHash().put("pessoa", pessoa.getId(), pessoa);

        mongoRepository.save(pessoa);

        return pessoa;
    }

    @Override
    public Optional<PessoaDocument> findById(final String id) {

        if (BooleanUtils.isFalse(redisTemplate.opsForHash().hasKey("pessoa", id))) {
            return Optional.empty();
        }

        final var value = redisTemplate.opsForHash().get("pessoa", id);
        return Optional.of(mapper.convertValue(value, PessoaDocument.class));
    }

    @Override
    public List<PessoaDocument> findAllByFilter(final String palavraChave) {

        final var criteria = TextCriteria.forDefaultLanguage().matchingAny(palavraChave);
        final var query = TextQuery.queryText(criteria)
                .sortByScore()
                .with(PageRequest.ofSize(50));

        return mongoTemplate.find(query, PessoaDocument.class);
    }

    @Override
    public Long count() {
        return mongoRepository.count();
    }
}
