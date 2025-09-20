package com.grovana.inventarios.repository;

import com.grovana.inventarios.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    long countByCategoria_Id(Long categoriaId);
    long countByProveedor_Id(Long proveedorId);
}
