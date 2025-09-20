INSERT INTO categorias (nombre, descripcion) VALUES
  ('Electrónicos', 'Dispositivos y accesorios electrónicos'),
  ('Hogar', 'Artículos para el hogar');

INSERT INTO proveedores (nombre, contacto_nombre, contacto_email, contacto_telefono, direccion, activo)
VALUES ('Proveedor Uno', 'Ana', 'ana@proveedor.com', '555-111', 'Calle 1', TRUE),
       ('Proveedor Dos', 'Luis', 'luis@proveedor.com', '555-222', 'Calle 2', TRUE);

INSERT INTO productos (nombre, categoria_id, proveedor_id)
VALUES ('Televisor', 1, 1), ('Licuadora', 2, 2);
