package ru.gb.xlsxreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByTitle(String title);
}
