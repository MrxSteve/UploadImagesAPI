# UploadImagesAPI

## Descripción
Esta API esta diseñada para subir imágenes a un servidor de AWS y obtener la URL de la imagen subida.

## Tecnologías
- Java 
- Spring Boot
- AWS S3
- Maven

## Features
- Subir imágenes a un servidor de AWS
- Obtener la URL de la imagen subida
- Eliminar imágenes del servidor de AWS
- Asignar imagenes a un producto
- Eliminar imagenes de un producto

## Endpoints

### Productos
- **Obtener Producto por ID**: `GET /api/productos/{id}`
- **Obtener Todos los Productos**: `GET /api/productos`
- **Crear Producto**: `POST /api/productos`
- **Actualizar Producto**: `PUT /api/productos/{id}`
- **Eliminar Producto**: `DELETE /api/productos/{id}`
- **Buscar Productos por Nombre**: `GET /api/productos/nombre`

### Imágenes
- **Subir Imagen**: `POST /api/imagenes/{productoId}`
- **Eliminar Imagen**: `DELETE /api/imagenes/{imagenId}`
- **Eliminar Imagen de Producto**: `DELETE /api/imagenes/producto/{productoId}/imagen/{imagenId}`
- **Eliminar Todas las Imágenes de Producto**: `DELETE /api/imagenes/producto/{productoId}`

## Configuración
Para poder utilizar la API es necesario configurar las siguientes variables de entorno:
- **AWS_ACCESS_KEY_ID**: Access Key ID de AWS
- **AWS_SECRET_ACCESS_KEY**: Secret Access Key de AWS
- **AWS_REGION**: Región de AWS
- **AWS_BUCKET_NAME**: Nombre del Bucket de AWS
- **DATABASE_URL**: URL de la base de datos
- **DATABASE_USERNAME**: Usuario de la base de datos
- **DATABASE_PASSWORD**: Contraseña de la base de datos

## Probar la API
Una vez configuradas las variables de entorno, ejecutar el proyecto y probar los endpoints con Postman o 
cualquier otra herramienta de pruebas de APIs.

### Para probarla en Swagger
Link: http://localhost:8080/swagger-ui/index.html#/