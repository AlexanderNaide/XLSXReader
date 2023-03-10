package ru.gb.xlsxreader.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.xlsxreader.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    Optional<Product> findByTitle(String title);

    @Transactional
    <S extends Product> S saveAndFlush(S entity);
}
