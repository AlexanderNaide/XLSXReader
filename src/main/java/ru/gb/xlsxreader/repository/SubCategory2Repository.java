package ru.gb.xlsxreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.SubCategory2;

import java.util.Optional;

public interface SubCategory2Repository extends JpaRepository<SubCategory2, Long> {

    Optional<SubCategory2> findByTitle(String title);
}
