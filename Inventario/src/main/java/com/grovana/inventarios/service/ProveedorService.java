package com.grovana.inventarios.service;

import com.grovana.inventarios.dto.ProveedorDto;
import com.grovana.inventarios.dto.ProveedorInputDto;

import java.util.List;

public interface ProveedorService {
    List<ProveedorDto> listAll(int page, int limit, Boolean activo);
    ProveedorDto create(ProveedorInputDto input);
    ProveedorDto getById(Long id);
    ProveedorDto update(Long id, ProveedorInputDto input);
    void delete(Long id);
}
