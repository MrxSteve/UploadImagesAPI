package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ImageUploadException;
import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.response.ImagenResponse;
import com.stevedev.uploadimages.models.entities.ImagenEntity;
import com.stevedev.uploadimages.models.entities.ProductoEntity;
import com.stevedev.uploadimages.models.mappers.ImagenMapper;
import com.stevedev.uploadimages.models.repositories.ImagenRepository;
import com.stevedev.uploadimages.models.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagenService {
    private final S3Client s3Client;
    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;
    private final ImagenMapper imagenMapper;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Transactional
    public String subirImagen(Long productoId, MultipartFile file) {
        ProductoEntity producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto con id " + productoId + " no encontrado"));

        // Definir los formatos permitidos
        List<String> formatosPermitidos = Arrays.asList("image/jpeg", "image/png", "image/webp", "image/jpg");

        // Validar tipo de archivo
        String contentType = file.getContentType();
        if (contentType == null || !formatosPermitidos.contains(contentType)) {
            throw new ImageUploadException("Formato de archivo no permitido. Solo se permiten imágenes (JPG, PNG, WEBP, JPG).");
        }

        // Validar tamaño del archivo (maximo 5mb)
        long maxSizeInBytes = 5 * 1024 * 1024;  // 5MB
        if (file.getSize() > maxSizeInBytes) {
            throw new ImageUploadException("El archivo es demasiado grande. El tamaño máximo permitido es 5MB.");
        }

        // Generar un nombre unico para la imagen
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            // Subir la imagen al bucket de S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            // Generar la URL publica de la imagen
            String publicUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;

            // Guardar en la base de datos
            ImagenEntity imagen = new ImagenEntity();
            imagen.setUrl(publicUrl);
            imagen.setProducto(producto);
            imagenRepository.save(imagen);

            return publicUrl;
        } catch (IOException e) {
            throw new ImageUploadException(String.format("Error al subir la imagen: %s", e));
        }
    }

    @Transactional
    public void eliminarImagen(Long imagenId) {
        ImagenEntity imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen con id " + imagenId + " no encontrada"));

        // Extraer el key del archivo desde la URL
        String key = imagen.getUrl().substring(imagen.getUrl().lastIndexOf("/") + 1);

        // Eliminar la imagen del bucket de S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());

        // Eliminar de la base de datos
        imagenRepository.delete(imagen);
    }

    @Transactional(readOnly = true)
    public Page<ImagenResponse> findAll(Pageable pageable) {
        Page<ImagenEntity> imagenes = imagenRepository.findAll(pageable);
        return imagenes.map(imagenMapper::toResponse);
    }

    @Transactional
    public void eliminarImagenDeProducto(Long productoId, Long imagenId) {
        ProductoEntity producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + productoId + " no encontrado"));

        ImagenEntity imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen con id " + imagenId + " no encontrada"));

        // Verificar que la imagen pertenezca al producto
        if (!imagen.getProducto().getId().equals(producto.getId())) {
            throw new ResourceNotFoundException("La imagen con id " + imagenId + " no pertenece al producto con id " + productoId);
        }

        // Extraer el key del archivo desde la URL
        String key = imagen.getUrl().substring(imagen.getUrl().lastIndexOf("/") + 1);

        // Eliminar la imagen del bucket de S3
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());

        // Eliminar de la base de datos
        imagenRepository.delete(imagen);
    }

    @Transactional
    public void eliminarTodasLasImagenesDeProducto(Long productoId) {
        ProductoEntity producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + productoId + " no encontrado"));

        List<ImagenEntity> imagenes = imagenRepository.findByProducto_Id(productoId);

        for (ImagenEntity imagen : imagenes) {
            // Extraer el key del archivo desde la URL
            String key = imagen.getUrl().substring(imagen.getUrl().lastIndexOf("/") + 1);

            // Eliminar la imagen del bucket de S3
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());

            // Eliminar de la base de datos
            imagenRepository.delete(imagen);
        }
    }
}
