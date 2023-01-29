package ru.gb.xlsxreader.repository;

//import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.xlsxreader.model.Categories;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long>, JpaSpecificationExecutor<Categories>{
//public interface CategoryRepository extends JpaRepository<Categories, Long>{

//    @Transactional
    Optional<Categories> findByTitle(String title);

    Optional<Categories> findFirstByTitle (String title);

    List<Categories> findAllByTitle (String title);

    @Modifying
    @Transactional
    @Query("select c from Categories c join fetch c.categoriesList where c.id = ?1")
    List<Optional<Categories>> mainFindCatById(Long id);

    @Modifying
    @Transactional
    @Query("select c.categoriesList from Categories c where c.id = ?1")
    List<Categories> getCatList(Long id);

//    List<Categories> findCategoriesByIdById (Long id);

//    Optional<Categories> mainFindCatById(Long id);
}
