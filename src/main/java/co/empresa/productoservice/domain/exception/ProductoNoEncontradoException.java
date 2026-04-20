package co.empresa.productoservice.domain.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }
}