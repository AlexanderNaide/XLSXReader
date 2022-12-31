package NoSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.Category;
import ru.gb.xlsxreader.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findCatByName(String title){
        return categoryRepository.findByTitle(title);
    }

    public void addCat(Category category){
        categoryRepository.save(category);
    }
}
