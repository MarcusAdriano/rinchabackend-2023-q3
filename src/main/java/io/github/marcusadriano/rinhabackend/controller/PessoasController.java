package io.github.marcusadriano.rinhabackend.controller;

import io.github.marcusadriano.rinhabackend.dto.api.CreatePessoaRequest;
import io.github.marcusadriano.rinhabackend.dto.api.PessoaResponse;
import io.github.marcusadriano.rinhabackend.service.PessoaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PessoasController {

    private final PessoaService pessoaService;

    public PessoasController(final PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping(path = "/pessoas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> createPessoa(@Validated @RequestBody final CreatePessoaRequest request) {

        final var pessoaCreated = pessoaService.create(request);
        if (pessoaCreated == null) {
            return ResponseEntity.status(422).build();
        }
        return ResponseEntity.status(201).header(HttpHeaders.LOCATION, "/pessoas/" + pessoaCreated.getId()).build();
    }

    @GetMapping(value = "/pessoas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> getPessoaById(@PathVariable final String id) {
        return pessoaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PessoaResponse>> getPessoasByFilter(@RequestParam("t") final String filter) {
        final var pessoas = pessoaService.findByFilter(filter);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping(value = "/contagem-pessoas")
    public ResponseEntity<Long> getCountPessoas() {
        final var countResult = pessoaService.count();
        return ResponseEntity.ok(countResult);
    }
}
