package io.github.marcusadriano.rinchabackend.dto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 32)
    private String apelido;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;

    private List<@NotBlank @Size(max = 32) String> stack;

}
