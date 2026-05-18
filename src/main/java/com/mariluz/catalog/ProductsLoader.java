package com.mariluz.catalog;

import com.mariluz.catalog.model.Product;
import com.mariluz.catalog.repository.CatalogRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductsLoader {

    @Bean
    CommandLineRunner init(CatalogRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                List<Product> initialProducts = List.of(
                    Product.builder()
                        .name("Hilos de Coser Poliéster (Set de 10 colores)")
                        .price(4990)
                        .quantity(50)
                        .build(),
                    Product.builder()
                        .name("Tijeras Profesionales de Sastre 10 pulgadas")
                        .price(12500)
                        .quantity(20)
                        .build(),
                    Product.builder()
                        .name("Cinta Métrica Flexible de Costura (1.5m)")
                        .price(1200)
                        .quantity(100)
                        .build(),
                    Product.builder()
                        .name("Alfileres de Cabeza de Vidrio (Caja x100)")
                        .price(2500)
                        .quantity(40)
                        .build(),
                    Product.builder()
                        .name("Tiza de Sastre para Marcar Tela (Pack de 4)")
                        .price(1800)
                        .quantity(60)
                        .build(),
                    Product.builder()
                        .name("Tela Algodón Premium por Metro")
                        .price(3500)
                        .quantity(150)
                        .build(),
                    Product.builder()
                        .name("Tela Lino Nacional por Metro")
                        .price(5800)
                        .quantity(80)
                        .build(),
                    Product.builder()
                        .name("Descosedor de Costuras Ergonómico")
                        .price(1500)
                        .quantity(45)
                        .build(),
                    Product.builder()
                        .name("Botones de Madera Artesanales (Bolsa x20)")
                        .price(2990)
                        .quantity(70)
                        .build(),
                    Product.builder()
                        .name("Cierre Invisible para Vestidos 50cm")
                        .price(990)
                        .quantity(120)
                        .build()
                );

                repo.saveAll(initialProducts);
                System.out.println(
                    "[INFO] Catálogo inicial de costura cargado exitosamente."
                );
            }
        };
    }
}
