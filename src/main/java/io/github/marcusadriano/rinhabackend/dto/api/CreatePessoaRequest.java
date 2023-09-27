package io.github.marcusadriano.rinhabackend.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePessoaRequest {

    private String apelido;

    private String nome;

    private String nascimento;

    private List<String> stack;

}
