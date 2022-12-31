package NoSource;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.SubCategory1;

import java.util.Optional;

public interface SubCategory1Repository extends JpaRepository<SubCategory1, Long> {

    Optional<SubCategory1> findByTitle(String title);
}
