package io.github.marcusadriano.rinchabackend.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private String id;
    private String nome;
    private String apelido;
    private String nascimento;
    private List<String> stack;

}
