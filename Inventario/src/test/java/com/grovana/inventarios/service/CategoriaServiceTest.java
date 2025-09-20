package com.grovana.inventarios.service;

import com.grovana.inventarios.exception.ConflictException;
import com.grovana.inventarios.exception.ResourceNotFoundException;
import com.grovana.inventarios.model.Categoria;
import com.grovana.inventarios.repository.CategoriaRepository;
import com.grovana.inventarios.repository.ProductoRepository;
import com.grovana.inventarios.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class CategoriaServiceTest {

    private CategoriaRepository categoriaRepository;
    private ProductoRepository productoRepository;
    private CategoriaService service;

    @BeforeEach
    void setup() {
        categoriaRepository = Mockito.mock(CategoriaRepository.class);
        productoRepository = Mockito.mock(ProductoRepository.class);
        service = new CategoriaServiceImpl(categoriaRepository, productoRepository);
    }

    @Test
    void deleteShouldThrowConflictWhenHasProducts() {
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(new Categoria()));
        Mockito.when(productoRepository.countByCategoria_Id(1L)).thenReturn(2L);
        Assertions.assertThrows(ConflictException.class, () -> service.delete(1L));
    }

    @Test
    void deleteShouldThrowNotFound() {
        Mockito.when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }
}
