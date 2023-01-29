package ru.gb.xlsxreader.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.Categories;
import ru.gb.xlsxreader.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Categories findByTitle(String title){
        Optional<Categories> opt = categoryRepository.findFirstByTitle(title);
        return opt.orElse(null);
    }

    public List<Categories> findAllByTitle(String title){
        return categoryRepository.findAllByTitle(title);
    }

//    @Transactional
    public Categories addCat(Categories category){
        return categoryRepository.saveAndFlush(category);
    }

//    @Transactional
    public Categories findById(Long id){
//        return categoryRepository.findById(id).orElse(null);
//        List<Categories> list = categoryRepository.mainFindCatById(id);
        System.out.println("Запрос категории с id " + id);
        List<Optional<Categories>> list = categoryRepository.mainFindCatById(3L);
//        Optional<Categories> list = categoryRepository.mainFindCatById(3L);
//        Categories categories = categoryRepository.mainFindCatById(id).stream().findFirst().orElse(null);
        for (Optional<Categories> categories : list) {
            categories.ifPresent(value -> System.out.println("Получено: " + value.getTitle()));

        }
//        list.ifPresent(categories -> System.out.println("Получено: " + categories.getTitle()));

        System.out.println();
        System.out.println();
        System.out.println();

        return list.get(0).orElse(null);
    }



    public List<Categories> getCatList(Long id){
        return categoryRepository.getCatList(id);
    }
}
