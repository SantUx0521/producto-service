package co.empresa.productoservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// @Entity le dice a JPA: "esta clase corresponde a una tabla en la BD"
// Por convención, el nombre de la tabla será "producto" (nombre de la clase en minúscula)
@Entity
@Getter   // Lombok genera: getNombre(), getPrecio(), etc.
@Setter   // Lombok genera: setNombre(...), setPrecio(...), etc.
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotEmpty: el campo no puede ser null ni string vacío ""
    // @Size: el string debe tener entre min y max caracteres
    // @Column(nullable=false): restricción también en la BD (doble protección)
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 y 20 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    // @NotNull: diferente de @NotEmpty — valida que no sea null pero acepta 0.0
    // @Min: el valor numérico debe ser >= value
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // @Pattern: el valor debe cumplir la expresión regular
    // Esta regex acepta: "laptop.jpg", "foto-producto.png", "mi imagen.webp"
    // No acepta: "archivo.txt", "script.js", ".jpg" (sin nombre)
    @Size(max = 100, message = "El nombre del archivo no puede superar los 100 caracteres")
    @Pattern(
        regexp = "^[\\w,\\s-]+\\.(jpg|jpeg|png|gif|bmp|webp)$",
        message = "El archivo debe tener una extensión de imagen válida (jpg, png, gif, bmp, webp)"
    )
    private String foto;
}
