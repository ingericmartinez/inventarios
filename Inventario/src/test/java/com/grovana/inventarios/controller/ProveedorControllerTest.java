package com.grovana.inventarios.controller;

import com.grovana.inventarios.dto.ProveedorDto;
import com.grovana.inventarios.dto.ProveedorInputDto;
import com.grovana.inventarios.service.ProveedorService;
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

class ProveedorControllerTest {

    private org.springframework.test.web.servlet.MockMvc mockMvc;
    private ProveedorService service;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        service = org.mockito.Mockito.mock(ProveedorService.class);
        ProveedorController controller = new ProveedorController(service);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new org.springframework.data.web.PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldListProveedores() throws Exception {
        ProveedorDto dto = new ProveedorDto();
        dto.setId(1L);
        dto.setNombre("Proveedor Uno");
        dto.setCreado_en(OffsetDateTime.now());
        Mockito.when(service.listAll(1, 20, null)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/proveedores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldValidateOnCreate() throws Exception {
        // Falta contacto_email => debe 400 por Bean Validation
        String body = "{\n  \"nombre\": \"Proveedor X\"\n}";
        mockMvc.perform(post("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
