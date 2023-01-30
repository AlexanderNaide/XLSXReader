package ru.gb.xlsxreader.repository;

//import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.xlsxreader.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>{
//public interface CategoryRepository extends JpaRepository<Categories, Long>{

//    @Transactional
    Optional<Category> findByTitle(String title);

    Optional<Category> findFirstByTitle (String title);

    @Transactional
    List<Category> findAllByTitle (String title);

    @Modifying
    @Transactional
    @Query("select c from Category c join fetch c.categoriesList where c.id = ?1")
    List<Optional<Category>> mainFindCatById(Long id);

    @Modifying
    @Transactional
    @Query("select c.categoriesList from Category c where c.id = ?1")
    List<Category> getCatList(Long id);
}
