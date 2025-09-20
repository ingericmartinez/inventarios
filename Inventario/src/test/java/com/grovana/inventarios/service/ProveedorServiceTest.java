package com.grovana.inventarios.service;

import com.grovana.inventarios.exception.ConflictException;
import com.grovana.inventarios.exception.ResourceNotFoundException;
import com.grovana.inventarios.model.Proveedor;
import com.grovana.inventarios.repository.ProductoRepository;
import com.grovana.inventarios.repository.ProveedorRepository;
import com.grovana.inventarios.service.impl.ProveedorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class ProveedorServiceTest {

    private ProveedorRepository proveedorRepository;
    private ProductoRepository productoRepository;
    private ProveedorService service;

    @BeforeEach
    void setup() {
        proveedorRepository = Mockito.mock(ProveedorRepository.class);
        productoRepository = Mockito.mock(ProductoRepository.class);
        service = new ProveedorServiceImpl(proveedorRepository, productoRepository);
    }

    @Test
    void deleteShouldThrowConflictWhenHasProducts() {
        Mockito.when(proveedorRepository.findById(1L)).thenReturn(Optional.of(new Proveedor()));
        Mockito.when(productoRepository.countByProveedor_Id(1L)).thenReturn(1L);
        Assertions.assertThrows(ConflictException.class, () -> service.delete(1L));
    }

    @Test
    void deleteShouldThrowNotFound() {
        Mockito.when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }
}
