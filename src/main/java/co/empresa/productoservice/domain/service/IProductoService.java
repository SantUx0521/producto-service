package co.empresa.productoservice.domain.service;

import co.empresa.productoservice.domain.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

// Esta interfaz define el CONTRATO de lo que el servicio puede hacer.
// El controlador solo conoce esta interfaz, nunca la implementación concreta.
public interface IProductoService {
    Producto save(Producto producto);
    void delete(Producto producto);
    Producto findById(Long id);         // lanza excepción si no existe (ver Etapa 12)
    Producto update(Producto producto);
    List<Producto> findAll();
    Page<Producto> findAll(Pageable pageable);
}
