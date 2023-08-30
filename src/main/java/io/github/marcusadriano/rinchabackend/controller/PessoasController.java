package io.github.marcusadriano.rinchabackend.controller;

import io.github.marcusadriano.rinchabackend.dto.api.CreatePessoaRequest;
import io.github.marcusadriano.rinchabackend.dto.api.PessoaResponse;
import io.github.marcusadriano.rinchabackend.service.PessoaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/pessoas/" + pessoaCreated.getId()).body(pessoaCreated);
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
