package io.github.marcusadriano.rinhabackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.marcusadriano.rinhabackend.dto.api.PessoaResponse;
import io.github.marcusadriano.rinhabackend.service.PessoaService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PessoasControllerTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private MockMvc mockMvc;

    @Mock
    private PessoaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(service.create(any())).thenReturn("abc");
        when(service.count()).thenReturn(10L);
        when(service.findById(anyString())).thenReturn(Optional.of(PessoaResponse.builder().id("abc").build()));
        when(service.findByFilter(anyString())).thenReturn(List.of(PessoaResponse.builder().id("abc").build()));

        mockMvc = MockMvcBuilders.standaloneSetup(new PessoasController(service))
                .alwaysDo(print())
                .setControllerAdvice(new PessoaControllerAdvisor()).build();
    }

    private Map<String, Object> createPessoaRequest(final String nome, final String apelido, final String nascimento,
                                                    final String... stack) {

        final Map<String, Object> json = new HashMap<>();
        json.put("nome", nome);
        json.put("apelido", apelido);
        json.put("nascimento", nascimento);
        json.put("stack", List.of(stack));
        return json;
    }


    private void testValidRequest(final Map<String, Object> req, final Integer expectedStatusCode) throws Exception {
        mockMvc.perform(
                        post("/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(req)))
                .andExpect(status().is(expectedStatusCode));
    }

    @Test
    void test_create_pessoa_validation() throws Exception {

        var createReq = createPessoaRequest("marcus", null, null);
        testValidRequest(createReq, 400);

        createReq = createPessoaRequest(null, "adriano", null);
        testValidRequest(createReq, 400);

        createReq = createPessoaRequest("marcus", "adriano", null);
        testValidRequest(createReq, 400);

        createReq = createPessoaRequest("marcus", "adriano", "2021-21-21");
        testValidRequest(createReq, 400);

        final var invalidStack = StringUtils.leftPad("", 33, "a");
        createReq = createPessoaRequest("marcus", "adriano", "2021-01-21", invalidStack);
        testValidRequest(createReq, 400);

        createReq = createPessoaRequest("marcus", "adriano", "2021-01-21");
        testValidRequest(createReq, 201);

        createReq = createPessoaRequest("marcus", "adriano", "2021-01-21", "java");
        testValidRequest(createReq, 201);

        createReq = createPessoaRequest("marcus", "adriano", "2021-01-21", "java", "python");
        testValidRequest(createReq, 201);
    }

    @Test
    void test_create_with_success() throws Exception{

        final var req = createPessoaRequest("marcus", "marcus", "1997-02-25", "Java", "StringBoot");

        mockMvc.perform(
                        post("/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(req)))
                .andExpect(status().is(201))
                .andExpect(header().string("Location", "/pessoas/abc"));
    }

    @Test
    void test_count_pessoas() throws Exception {

        mockMvc.perform(
                        get("/contagem-pessoas"))
                .andExpect(status().is(200))
                .andExpect(content().string("10"));
    }

    @Test
    void test_get_pessoa_by_id() throws Exception {

        mockMvc.perform(
                        get("/pessoas/abc"))
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":\"abc\"}"));
    }

    @Test
    void test_get_pessoas_by_filter() throws Exception {

        mockMvc.perform(
                        get("/pessoas?t=abc"))
                .andExpect(status().is(200))
                .andExpect(content().json("[{\"id\":\"abc\"}]"));
    }

}