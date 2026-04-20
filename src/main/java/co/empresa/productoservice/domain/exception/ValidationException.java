package co.empresa.productoservice.domain.exception;

import org.springframework.validation.BindingResult;

// Encapsula los errores de validación para transportarlos desde el controlador
// hasta el GlobalExceptionHandler sin duplicar código
public class ValidationException extends RuntimeException {

    private final BindingResult result;

    public ValidationException(BindingResult result) {
        super("Error de validación en los datos recibidos");
        this.result = result;
    }

    public BindingResult getResult() {
        return result;
    }
}