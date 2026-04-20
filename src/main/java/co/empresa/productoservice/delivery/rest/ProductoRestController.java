package co.empresa.productoservice.delivery.rest;

import co.empresa.productoservice.domain.exception.ValidationException;
import co.empresa.productoservice.domain.model.Producto;
import co.empresa.productoservice.domain.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/producto-service")
public class ProductoRestController {

    private final IProductoService productoService;

    // Constantes para las claves del Map de respuesta
    // Evita errores de tipeo ("prodcuto" vs "producto") y facilita el refactoring
    private static final String KEY_MENSAJE   = "mensaje";
    private static final String KEY_PRODUCTO  = "producto";
    private static final String KEY_PRODUCTOS = "productos";
    private static final String KEY_PAGINA    = "pagina";

    public ProductoRestController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/productos")
    public ResponseEntity<Map<String, Object>> save(
            @Valid @RequestBody Producto producto,
            BindingResult result) {

        // Si hay errores, lanza la excepción — el GlobalExceptionHandler la captura
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        Producto nuevo = productoService.save(producto);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(KEY_MENSAJE, "El producto ha sido creado con éxito");
        respuesta.put(KEY_PRODUCTO, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    
    @PutMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new ValidationException(result);  // mismo patrón, una línea
        }

        producto.setId(id);
        // Si el ID no existe, ProductoServiceImpl.findById lanzará ProductoNoEncontradoException
        // GlobalExceptionHandler la captura y retorna 404 automáticamente
        Producto actualizado = productoService.update(producto);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(KEY_MENSAJE, "El producto ha sido actualizado con éxito");
        respuesta.put(KEY_PRODUCTO, actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        // Si el producto no existe, findById lanza ProductoNoEncontradoException
        // No necesitas manejarla aquí — el GlobalExceptionHandler lo hace
        Producto producto = productoService.findById(id);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(KEY_PRODUCTO, producto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Producto producto = productoService.findById(id);  // lanza 404 si no existe
        productoService.delete(producto);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put(KEY_MENSAJE, "El producto ha sido eliminado con éxito");
        return ResponseEntity.ok(respuesta);
    }

}
