package com.grovana.inventarios.controller;

import com.grovana.inventarios.dto.ProveedorDto;
import com.grovana.inventarios.dto.ProveedorInputDto;
import com.grovana.inventarios.service.ProveedorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> getProveedores(
            @org.springframework.data.web.PageableDefault(page = 0, size = 20) org.springframework.data.domain.Pageable pageable,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(name = "page", required = false) Integer pageParam) {
        int pageOneBased = (pageParam != null ? Math.max(pageParam, 1) : Math.max(pageable.getPageNumber() + 1, 1));
        int size = limit != null ? limit : pageable.getPageSize();
        size = Math.max(1, Math.min(size, 100));
        List<ProveedorDto> result = service.listAll(pageOneBased, size, activo);
        log.info("GET /api/v1/proveedores page(one-based)={} size={} activo={} -> {} elementos", pageOneBased, size, activo, result.size());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProveedorDto> createProveedor(@Valid @RequestBody ProveedorInputDto input) {
        ProveedorDto created = service.create(input);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{proveedorId}")
    public ResponseEntity<ProveedorDto> getProveedorById(@PathVariable Long proveedorId) {
        return ResponseEntity.ok(service.getById(proveedorId));
    }

    @PutMapping("/{proveedorId}")
    public ResponseEntity<ProveedorDto> updateProveedor(@PathVariable Long proveedorId,
                                                        @Valid @RequestBody ProveedorInputDto input) {
        return ResponseEntity.ok(service.update(proveedorId, input));
    }

    @DeleteMapping("/{proveedorId}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long proveedorId) {
        service.delete(proveedorId);
        return ResponseEntity.noContent().build();
    }
}
