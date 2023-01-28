package ru.gb.xlsxreader.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.Categories;
import ru.gb.xlsxreader.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//    @Transactional
    public Optional<Categories> findCatByName(String title){
        return categoryRepository.findByTitle(title);
    }

    public Categories addCat(Categories category){
        return categoryRepository.saveAndFlush(category);
    }

    public void flush(){
        categoryRepository.flush();
    }
}
