package com.grovana.inventarios.controller;

import com.grovana.inventarios.dto.CategoriaDto;
import com.grovana.inventarios.dto.ProveedorDto;
import com.grovana.inventarios.service.CategoriaService;
import com.grovana.inventarios.service.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de compatibilidad para rutas sin prefijo (legacy) usadas por algunos clientes.
 * Redirige la lógica a los mismos servicios que usan los controladores con prefijo /api/v1.
 */
@RestController
@RequestMapping
public class LegacyApiCompatController {

    private static final Logger log = LoggerFactory.getLogger(LegacyApiCompatController.class);

    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;

    public LegacyApiCompatController(CategoriaService categoriaService, ProveedorService proveedorService) {
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDto>> getCategoriasLegacy(
            @org.springframework.data.web.PageableDefault(page = 0, size = 20) org.springframework.data.domain.Pageable pageable,
            @RequestParam(name = "limit", required = false) Integer limit
    ) {
        int page = Math.max(pageable.getPageNumber() + 1, 1);
        int size = limit != null ? limit : pageable.getPageSize();
        size = Math.max(1, Math.min(size, 100));
        List<CategoriaDto> result = categoriaService.listAll(page, size);
        log.info("[LEGACY] GET /categorias page={} size={} -> {} elementos", page, size, result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorDto>> getProveedoresLegacy(
            @org.springframework.data.web.PageableDefault(page = 0, size = 20) org.springframework.data.domain.Pageable pageable,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(required = false) Boolean activo
    ) {
        int page = Math.max(pageable.getPageNumber() + 1, 1);
        int size = limit != null ? limit : pageable.getPageSize();
        size = Math.max(1, Math.min(size, 100));
        List<ProveedorDto> result = proveedorService.listAll(page, size, activo);
        log.info("[LEGACY] GET /proveedores page={} size={} activo={} -> {} elementos", page, size, activo, result.size());
        return ResponseEntity.ok(result);
    }
}
