package io.github.marcusadriano.rinchabackend.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("pessoas")
public class PessoaDocument {

    @Id
    private String id;

    private String nome;

    @Indexed(unique = true)
    private String apelido;

    private LocalDate nascimento;

    private List<String> stack;

}
