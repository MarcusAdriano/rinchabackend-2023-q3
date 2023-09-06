package io.github.marcusadriano.rinhabackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marcusadriano.rinhabackend.repository.mongo.PessoaDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final String host;
    private final Integer port;

    public RedisConfig(@Value("${spring.data.redis.host}") final String host,
                       @Value("${spring.data.redis.port}") final Integer port) {
        this.host = host;
        this.port = port;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final var standAloneConfig = new RedisStandaloneConfiguration();
        standAloneConfig.setHostName(host);
        standAloneConfig.setPort(port);
        return new JedisConnectionFactory(standAloneConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final ObjectMapper mapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        final var pessoaSerializer = new Jackson2JsonRedisSerializer<>(mapper, PessoaDocument.class);
        template.setHashValueSerializer(pessoaSerializer);

        return template;
    }

}
