package com.grovana.inventarios.service.impl;

import com.grovana.inventarios.dto.CategoriaDto;
import com.grovana.inventarios.dto.CategoriaInputDto;
import com.grovana.inventarios.exception.ConflictException;
import com.grovana.inventarios.exception.ResourceNotFoundException;
import com.grovana.inventarios.model.Categoria;
import com.grovana.inventarios.repository.CategoriaRepository;
import com.grovana.inventarios.service.CategoriaService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;
    private final com.grovana.inventarios.repository.ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository repository, com.grovana.inventarios.repository.ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<CategoriaDto> listAll(int page, int limit) {
        return repository.findAll(PageRequest.of(Math.max(page - 1, 0), limit))
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDto create(CategoriaInputDto input) {
        repository.findByNombreIgnoreCase(input.getNombre()).ifPresent(c -> {
            throw new ConflictException("La categoría ya existe");
        });
        Categoria entity = new Categoria();
        entity.setNombre(input.getNombre());
        entity.setDescripcion(input.getDescripcion());
        Categoria saved = repository.save(entity);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDto getById(Long id) {
        Categoria c = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return toDto(c);
    }

    @Override
    public CategoriaDto update(Long id, CategoriaInputDto input) {
        Categoria c = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        if (!c.getNombre().equalsIgnoreCase(input.getNombre())) {
            repository.findByNombreIgnoreCase(input.getNombre()).ifPresent(other -> {
                if (!other.getId().equals(id)) throw new ConflictException("La categoría ya existe");
            });
        }
        c.setNombre(input.getNombre());
        c.setDescripcion(input.getDescripcion());
        return toDto(repository.save(c));
    }

    @Override
    public void delete(Long id) {
        Categoria c = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        long asociados = productoRepository.countByCategoria_Id(id);
        if (asociados > 0) {
            throw new ConflictException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        repository.delete(c);
    }

    private CategoriaDto toDto(Categoria c) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setCreado_en(c.getCreadoEn());
        dto.setActualizado_en(c.getActualizadoEn());
        return dto;

    }
}
