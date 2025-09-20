package com.grovana.inventarios.controller;

import com.grovana.inventarios.dto.CategoriaDto;
import com.grovana.inventarios.dto.CategoriaInputDto;
import com.grovana.inventarios.service.CategoriaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private static final Logger log = LoggerFactory.getLogger(CategoriaController.class);

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getCategorias(
            @org.springframework.data.web.PageableDefault(page = 0, size = 20) org.springframework.data.domain.Pageable pageable,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "page", required = false) Integer pageParam
    ) {
        int pageOneBased = (pageParam != null ? Math.max(pageParam, 1) : Math.max(pageable.getPageNumber() + 1, 1));
        int size = limit != null ? limit : pageable.getPageSize();
        size = Math.max(1, Math.min(size, 100)); // limitar tamaño máximo
        List<CategoriaDto> result = service.listAll(pageOneBased, size);
        log.info("GET /api/v1/categorias page(one-based)={} size={} -> {} elementos", pageOneBased, size, result.size());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@Valid @RequestBody CategoriaInputDto input) {
        CategoriaDto created = service.create(input);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(service.getById(categoriaId));
    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long categoriaId,
                                                        @Valid @RequestBody CategoriaInputDto input) {
        return ResponseEntity.ok(service.update(categoriaId, input));
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long categoriaId) {
        service.delete(categoriaId);
        return ResponseEntity.noContent().build();
    }
}
