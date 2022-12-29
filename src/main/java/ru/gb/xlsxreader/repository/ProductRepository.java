package ru.gb.xlsxreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.Category;
import ru.gb.xlsxreader.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle(String title);
}
