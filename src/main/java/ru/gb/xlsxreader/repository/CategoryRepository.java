package ru.gb.xlsxreader.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.xlsxreader.model.Categories;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    @Transactional
    Optional<Categories> findByTitle(String title);

    @Transactional
    Categories saveAndFlush(Categories categories);

    @Override
    void flush();
}
