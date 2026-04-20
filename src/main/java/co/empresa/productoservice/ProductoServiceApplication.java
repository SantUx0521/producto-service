package co.empresa.productoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication es un atajo que combina tres anotaciones:
//   @Configuration         → esta clase puede definir beans de Spring
//   @EnableAutoConfiguration → activa la auto-configuración basada en dependencias
//   @ComponentScan         → escanea este paquete y sus sub-paquetes buscando @Component, @Service, etc.
@SpringBootApplication
public class ProductoServiceApplication {
    public static void main(String[] args) {
        // Arranca el contexto de Spring y el servidor Tomcat embebido
        SpringApplication.run(ProductoServiceApplication.class, args);
    }
}
