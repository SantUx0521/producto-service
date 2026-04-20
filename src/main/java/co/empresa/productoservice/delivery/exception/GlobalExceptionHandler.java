// src/.../delivery/exception/GlobalExceptionHandler.java
package co.empresa.productoservice.delivery.exception;

import co.empresa.productoservice.domain.exception.ProductoNoEncontradoException;
import co.empresa.productoservice.domain.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestControllerAdvice: intercepta excepciones lanzadas desde cualquier @RestController
// Es un @Component — Spring lo detecta y registra automáticamente
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler: este método se ejecuta cuando se lanza ValidationException
    // en cualquier controlador de la aplicación
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        List<String> errores = ex.getResult().getFieldErrors().stream()
                .map(err -> "El campo '" + err.getField() + "': " + err.getDefaultMessage())
                .toList();
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("errores", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNoEncontrado(
            ProductoNoEncontradoException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // Captura genérica: cualquier excepción no manejada específicamente
    // Evita que el usuario vea stack traces en la respuesta HTTP
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", "Ocurrió un error interno. Por favor contacta al soporte.");
        // Loguear el error real (sin exponerlo al cliente)
        // log.error("Error interno", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
