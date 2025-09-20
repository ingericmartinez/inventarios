package com.grovana.inventarios.controller;

import com.grovana.inventarios.dto.CategoriaDto;
import com.grovana.inventarios.dto.CategoriaInputDto;
import com.grovana.inventarios.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoriaControllerTest {

    private org.springframework.test.web.servlet.MockMvc mockMvc;
    private CategoriaService service;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        service = org.mockito.Mockito.mock(CategoriaService.class);
        CategoriaController controller = new CategoriaController(service);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new org.springframework.data.web.PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldListCategorias() throws Exception {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(1L);
        dto.setNombre("Electrónicos");
        dto.setCreado_en(OffsetDateTime.now());
        Mockito.when(service.listAll(1, 20)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
