package co.empresa.productoservice.domain.service;

import co.empresa.productoservice.domain.exception.ProductoNoEncontradoException;
import co.empresa.productoservice.domain.model.Producto;
import co.empresa.productoservice.domain.repository.IProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa los métodos de la interfaz IProductoService
 * para realizar las operaciones de negocio sobre la entidad Producto
 */
@Service
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository productoRepository;

    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // @Transactional(readOnly = true) para consultas:
    //   - Hibernate optimiza (no rastrea cambios en las entidades)
    //   - La BD puede usar réplicas de lectura
    //   - Evita actualizaciones accidentales
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();  // JpaRepository ya retorna List<T>
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    // @Transactional para escrituras: garantiza rollback si algo falla
    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        // orElseThrow: lanza la excepción si el Optional está vacío
        // El Supplier (lambda) solo se ejecuta si el producto NO existe
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    @Override
    @Transactional
    public Producto update(Producto producto) {
        return productoRepository.save(producto);
    }
}