package NoSource;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String title);
}
