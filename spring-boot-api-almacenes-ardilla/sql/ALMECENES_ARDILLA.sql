DROP TABLE A_USUARIO;
DROP TABLE A_PRODUCTOS;
DROP TABLE A_TIPO_PRODUCTO;
DROP TABLE A_VENTAS;
DROP TABLE A_DETALLE_VENTA;
COMMIT;

CREATE TABLE A_USUARIO(
ID              NUMBER(10) NOT NULL,
USUARIO         VARCHAR2(100) NOT NULL,
CONSTRASENA     VARCHAR2(100) NOT NULL,
TIPO            VARCHAR2(20) NOT NULL, --ADMINISTRADOR O USUARIO
CONSTRAINT PK_USUARIO PRIMARY KEY(ID));

CREATE TABLE A_TIPO_PRODUCTO(
ID        NUMBER(10) NOT NULL,
NOMBRE    VARCHAR2(200) NOT NULL,
CONSTRAINT PK_TIPO_PRODUCTO PRIMARY KEY(ID));

CREATE TABLE A_PRODUCTOS(
ID        NUMBER(10) NOT NULL,
NOMBRE    VARCHAR2(200) NOT NULL,
PRECIO    NUMBER(10,2) NOT NULL,
STOCK     NUMBER(10,2) NOT NULL,
TIPO      NUMBER(10) NOT NULL, --TIPO DE PRODUCTO
CONSTRAINT PK_PRODUCTOS PRIMARY KEY(ID),
CONSTRAINT FK_PRODUCTOS FOREIGN KEY(TIPO) REFERENCES A_TIPO_PRODUCTO(ID));

CREATE TABLE A_VENTAS(
ID            NUMBER(10) NOT NULL,
FECHA         DATE,
ID_USUARIO    NUMBER(10) NOT NULL,        -- Empleado que realizó la venta
FORMA_PAGO    VARCHAR2(50) NOT NULL,      -- Efectivo, tarjeta, transferencia, etc.
TOTAL         NUMBER(10,2) NOT NULL,      -- Total general de la venta
CONSTRAINT PK_VENTAS PRIMARY KEY(ID),
CONSTRAINT FK_VENTAS FOREIGN KEY (ID_USUARIO) REFERENCES A_USUARIO(ID));

CREATE TABLE A_DETALLE_VENTA(
ID               NUMBER(10) NOT NULL,
ID_VENTA         NUMBER(10) NOT NULL,
ID_PRODUCTO      NUMBER(10) NOT NULL,
CANTIDAD         NUMBER(5) NOT NULL,
PRECIO_UNITARIO  NUMBER(10,2) NOT NULL,
SUBTOTAL         NUMBER(10,2) NOT NULL,
CONSTRAINT PK_DETALLE PRIMARY KEY(ID),
CONSTRAINT FK_DETALLE_VENTAS FOREIGN KEY (ID_VENTA) REFERENCES A_VENTAS(ID),
CONSTRAINT FK_DETALLE_PRODUCTOS FOREIGN KEY (ID_PRODUCTO) REFERENCES A_PRODUCTOS(ID));

CREATE SEQUENCE AUTO_INCREMENT
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
    
SELECT sequence_name FROM user_sequences WHERE sequence_name IN (
  'TIPO_PROD_SEQ',
  'A_DETALLE_VENTA_SEQ',
  'A_PRODUCTOS_SEQ',
  'A_USUARIO_SEQ',
  'A_VENTAS_SEQ'
);

DESC A_PRODUCTOS;

SELECT * FROM A_TIPO_PRODUCTO;

SELECT constraint_name, table_name, column_name, r_constraint_name
FROM user_cons_columns
WHERE table_name = 'A_PRODUCTOS';

COMMIT;

SELECT column_name, data_type
FROM user_tab_columns
WHERE table_name = 'A_PRODUCTOS' AND column_name = 'TIPO';

SELECT constraint_name, r_constraint_name, status
FROM user_constraints
WHERE table_name = 'A_PRODUCTOS' AND constraint_type = 'R';

INSERT INTO A_PRODUCTOS (id, nombre, precio, stock, tipo) VALUES (1, 'Coca Cola', 45, 100, 1);
SELECT * FROM A_TIPO_PRODUCTO WHERE id = 1;

SELECT column_name, data_type, nullable
FROM all_tab_columns
WHERE table_name = 'A_PRODUCTOS'
AND column_name = 'TIPO';


SELECT constraint_name, constraint_type, status
FROM user_constraints
WHERE table_name = 'A_PRODUCTOS'
AND constraint_type = 'R'; -- 'R' indica Foreign Key

SELECT a.constraint_name, a.table_name, a.column_name,
       c.owner AS r_owner,
       c_pk.table_name AS referenced_table,
       c_pk.constraint_name AS pk_constraint_name
FROM user_cons_columns a
JOIN user_constraints c ON a.constraint_name = c.constraint_name
JOIN user_constraints c_pk ON c.r_constraint_name = c_pk.constraint_name
WHERE a.constraint_name = 'FK7E18B4MMQ7I88B1QMT1OJRS6G';

SELECT column_name
FROM user_cons_columns
WHERE constraint_name = 'PK_TIPO_PRODUCTO';

ALTER TABLE A_PRODUCTOS DISABLE CONSTRAINT FK7E18B4MMQ7I88B1QMT1OJRS6G;
ALTER TABLE A_PRODUCTOS ENABLE CONSTRAINT FK7E18B4MMQ7I88B1QMT1OJRS6G;

SELECT * FROM A_TIPO_PRODUCTOS WHERE ID = 1;

commit;