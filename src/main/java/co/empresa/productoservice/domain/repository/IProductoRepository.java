package co.empresa.productoservice.domain.repository;

import co.empresa.productoservice.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository extiende CrudRepository y agrega:
//   findAll(Pageable pageable) → retorna Page<T> en vez de List<T>
//   saveAll(Iterable<T>)
//   flush() y saveAndFlush()
// El cambio es compatible: todo lo que usabas de CrudRepository sigue funcionando
public interface IProductoRepository extends JpaRepository<Producto, Long> {
}
