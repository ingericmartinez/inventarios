package com.grovana.inventarios.service.impl;

import com.grovana.inventarios.dto.ProveedorDto;
import com.grovana.inventarios.dto.ProveedorInputDto;
import com.grovana.inventarios.exception.ResourceNotFoundException;
import com.grovana.inventarios.model.Proveedor;
import com.grovana.inventarios.repository.ProveedorRepository;
import com.grovana.inventarios.service.ProveedorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository repository;
    private final com.grovana.inventarios.repository.ProductoRepository productoRepository;

    public ProveedorServiceImpl(ProveedorRepository repository, com.grovana.inventarios.repository.ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ProveedorDto> listAll(int page, int limit, Boolean activo) {
        Specification<Proveedor> spec = (root, query, cb) -> {
            if (activo == null) return cb.conjunction();
            return cb.equal(root.get("activo"), activo);
        };
        return repository.findAll(spec, PageRequest.of(Math.max(page - 1, 0), limit))
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorDto create(ProveedorInputDto input) {
        Proveedor p = new Proveedor();
        p.setNombre(input.getNombre());
        p.setContactoNombre(input.getContacto_nombre());
        p.setContactoEmail(input.getContacto_email());
        p.setContactoTelefono(input.getContacto_telefono());
        p.setDireccion(input.getDireccion());
        p.setActivo(input.getActivo() == null ? Boolean.TRUE : input.getActivo());
        return toDto(repository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorDto getById(Long id) {
        Proveedor p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        return toDto(p);
    }

    @Override
    public ProveedorDto update(Long id, ProveedorInputDto input) {
        Proveedor p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        p.setNombre(input.getNombre());
        p.setContactoNombre(input.getContacto_nombre());
        p.setContactoEmail(input.getContacto_email());
        p.setContactoTelefono(input.getContacto_telefono());
        p.setDireccion(input.getDireccion());
        if (input.getActivo() != null) p.setActivo(input.getActivo());
        return toDto(repository.save(p));
    }

    @Override
    public void delete(Long id) {
        Proveedor p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        long asociados = productoRepository.countByProveedor_Id(id);
        if (asociados > 0) {
            throw new com.grovana.inventarios.exception.ConflictException("No se puede eliminar el proveedor porque tiene productos asociados");
        }
        repository.delete(p);
    }

    private ProveedorDto toDto(Proveedor p) {
        ProveedorDto dto = new ProveedorDto();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setContacto_nombre(p.getContactoNombre());
        dto.setContacto_email(p.getContactoEmail());
        dto.setContacto_telefono(p.getContactoTelefono());
        dto.setDireccion(p.getDireccion());
        dto.setActivo(p.getActivo());
        dto.setCreado_en(p.getCreadoEn());
        dto.setActualizado_en(p.getActualizadoEn());
        return dto;
    }
}
