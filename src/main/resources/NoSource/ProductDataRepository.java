package NoSource;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.xlsxreader.model.ProductData;

public interface ProductDataRepository extends JpaRepository<ProductData, Long> {

}
