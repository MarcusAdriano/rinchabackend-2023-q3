package io.github.marcusadriano.rinhabackend.controller;

import io.github.marcusadriano.rinhabackend.dto.api.CreatePessoaRequest;
import io.github.marcusadriano.rinhabackend.dto.api.PessoaResponse;
import io.github.marcusadriano.rinhabackend.service.PessoaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class PessoasController {

    private final PessoaService pessoaService;

    public PessoasController(final PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping(path = "/pessoas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> createPessoa(@RequestBody final CreatePessoaRequest request) {

        if (StringUtils.isEmpty(request.getApelido()) || request.getApelido().length() > 32) {
            return ResponseEntity.status(400).build();
        }

        if (StringUtils.isEmpty(request.getNome()) || request.getNome().length() > 100) {
            return ResponseEntity.status(400).build();
        }

        if (request.getNascimento() == null) {
            return ResponseEntity.status(400).build();
        }

        try {
            LocalDate.parse(request.getNascimento(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (final Exception e) {
            return ResponseEntity.status(400).build();
        }

        if (request.getStack() != null) {
            for (final var stack : request.getStack()) {
                if (StringUtils.isEmpty(stack) || stack.length() > 32) {
                    return ResponseEntity.status(400).build();
                }
            }
        }

        final var createdId = pessoaService.create(request);
        if (createdId == null) {
            return ResponseEntity.status(422).build();
        }
        return ResponseEntity.status(201).header(HttpHeaders.LOCATION, "/pessoas/" + createdId).build();
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
