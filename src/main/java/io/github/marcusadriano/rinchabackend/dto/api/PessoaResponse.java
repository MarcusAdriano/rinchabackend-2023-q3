package io.github.marcusadriano.rinchabackend.dto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PessoaResponse {

    private String id;
    private String nome;
    private String apelido;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;
    private List<String> stack;

}
