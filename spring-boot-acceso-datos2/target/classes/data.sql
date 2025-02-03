/*Populate tables*/
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(1, 'Andres', 'Guzman', 'profesor@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(2, 'Jhon', 'Doe', 'jhon@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(3, 'Julio', 'Guzman', 'julio@gmail.com', '2016-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(4, 'Mario', 'Irrivarren', 'iri@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(5, 'Ana', 'Maldini', 'ana@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(6, 'Lucas', 'Plata', 'lucas@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(7, 'Mariano', 'Bayon', 'mariano@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(8, 'Bondag', 'with', 'bon@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(9, 'Karim', 'Benzema', 'lucas@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(10, 'Tony', 'Kross', 'mariano@gmail.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(11, 'Vinicius', 'Junior', 'bon@gmail.com', '2017-08-28', '');

/*Populate tabla productos*/
INSERT INTO productos (nombre, precio, create_at) VALUES
('Laptop Lenovo', 850.00, '2024-01-15'),
('Mouse Inalámbrico', 20.50, '2024-02-10'),
('Teclado Mecánico', 45.00, '2024-03-05'),
('Monitor 24 pulgadas', 180.00, '2024-04-20'),
('Disco Duro Externo 1TB', 60.00, '2024-05-10'),
('Auriculares Bluetooth', 35.00, '2024-06-15'),
('Impresora Multifuncional', 150.00, '2024-07-30'),
('Silla Ergonomica', 120.00, '2024-08-25');

/*Creamos algunas facturas*/
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES
('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items(cantidad, factura_id, producto_id) VALUES
(1,1,1),
(2,1,4),
(1,1,5),
(1,1,7);
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES
('Factura Bicicleta', 'Alguna nota importante', 1, NOW());
INSERT INTO facturas_items(cantidad, factura_id, producto_id) VALUES (3,2,6);
