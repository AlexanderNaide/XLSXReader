package NoSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.ProductData;
import ru.gb.xlsxreader.repository.ProductDataRepository;

@Service
public class ProductDataService {
    private ProductDataRepository productDataRepository;

    @Autowired
    public void setProductDataRepository(ProductDataRepository productDataRepository) {
        this.productDataRepository = productDataRepository;
    }

    public ProductData addData(ProductData productData){
        return productDataRepository.save(productData);
    }

}
