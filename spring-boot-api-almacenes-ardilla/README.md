# 🛡️ API de Almacén - Spring Boot + JWT + Postman

Este proyecto es una API REST desarrollada con **Spring Boot**, que permite manejar usuarios, productos, tipos de producto y ventas, autenticando con **JWT (JSON Web Token)**.

---

## 🚀 Requisitos

- Java 17+
- Maven 3.8+
- Postman
- Base de datos configurada (ej: H2, MySQL, PostgreSQL)

---

## 🔐 Autenticación JWT

### 1. Registrar usuario

```
POST /auth/register
Content-Type: application/json
```

```json
{
  "usuario": "admin",
  "contrasena": "1234"
}
```

📌 Por defecto, se registra con el tipo `USUARIO`.

---

### 2. Iniciar sesión (Login)

```
POST /auth/login
Content-Type: application/json
```

```json
{
  "usuario": "admin",
  "contrasena": "1234"
}
```

🔁 Respuesta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}
```

💡 Copia este token para usarlo en los endpoints protegidos.

---

### 3. Agregar el Token en Postman

Para acceder a rutas protegidas (`/api/**`):

- En la pestaña **Authorization** de Postman
- Tipo: `Bearer Token`
- Pega el token en el campo: `Token`

---

## 📦 Endpoints de Producto

### Obtener todos los productos

```
GET /api/productos
```

---

### Crear un producto

```
POST /api/productos
Content-Type: application/json
```

```json
{
  "nombre": "Laptop",
  "precio": 950.0,
  "stock": 10,
  "tipoProducto": {
    "id": 1
  }
}
```

---

### Obtener producto por ID

```
GET /api/productos/{id}
```

---

### Actualizar producto

```
PUT /api/productos/{id}
Content-Type: application/json
```

```json
{
  "nombre": "Laptop Gamer",
  "precio": 1200.0,
  "stock": 5,
  "tipoProducto": {
    "id": 1
  }
}
```

---

### Eliminar producto

```
DELETE /api/productos/{id}
```

---

### Obtener estadísticas de inventario

```
GET /api/productos/estadisticas
```

📊 Devuelve el resumen del inventario total.

---

## 🗂️ Endpoints de Tipo de Producto

### Obtener todos

```
GET /api/tipo-producto
```

---

### Crear nuevo tipo

```
POST /api/tipo-producto
Content-Type: application/json
```

```json
{
  "nombre": "Electrónica"
}
```

---

## 🧾 Endpoints de Ventas

### Crear venta con detalles

```
POST /api/ventas
Content-Type: application/json
```

```json
{
  "idUsuario": 1,
  "formaPago": "EFECTIVO",
  "detalles": [
    {
      "idProducto": 1,
      "cantidad": 2,
      "precioUnitario": 950.0
    },
    {
      "idProducto": 2,
      "cantidad": 1,
      "precioUnitario": 150.0
    }
  ]
}
```

---

### Obtener todas las ventas

```
GET /api/ventas
```

---

### Obtener venta por ID

```
GET /api/ventas/{id}
```

---

## 📁 Estructura del Proyecto

```
src/
├── controller/
│   ├── JwtController.java
│   ├── AProductosController.java
│   ├── ATipoProductoController.java
│   └── AVentasController.java
├── dto/
│   ├── AuthRequest.java
│   ├── VentaDTO.java
│   └── DetalleVentaDTO.java
├── model/
├── repository/
├── security/   <-- JWT Configuración
├── util/
└── ...
```

---

## 🛠️ Notas Técnicas

- Contraseñas se almacenan en hash usando `PasswordEncoder`.
- JWT contiene el usuario y sus roles.
- Los endpoints bajo `/api/**` requieren token JWT válido.
- Puedes modificar los roles (`ADMINISTRADOR`, `USUARIO`) en la entidad `A_Usuario`.

---

## 📫 Contacto

Si tienes dudas o sugerencias:

**Autor:** 	Omar Flores Castillo 
**GitHub:** 	https://github.com/OmarFC08  
**Correo:** 	omarflores8596@gmail.com

---

## ✅ ¡Listo para probar en Postman!

Puedes importar este proyecto en Postman como colección y probar:

- Autenticación (`/auth/login`)
- CRUD de productos
- Registro de ventas