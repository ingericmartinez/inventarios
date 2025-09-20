package com.grovana.inventarios.service;

import com.grovana.inventarios.dto.CategoriaDto;
import com.grovana.inventarios.dto.CategoriaInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDto> listAll(int page, int limit);
    CategoriaDto create(CategoriaInputDto input);
    CategoriaDto getById(Long id);
    CategoriaDto update(Long id, CategoriaInputDto input);
    void delete(Long id);
}
