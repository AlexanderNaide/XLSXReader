package ru.gb.xlsxreader.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.xlsxreader.model.Manufacturer;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Transactional
    Optional<Manufacturer> findByTitle(String title);

    @Transactional
    <S extends Manufacturer> S saveAndFlush(S entity);
}
