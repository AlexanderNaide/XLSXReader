package ru.gb.xlsxreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.Product;
import ru.gb.xlsxreader.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setCategoryRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProdByName(String title){
        return productRepository.findByTitle(title);
    }

    public void addProd(Product product){
        productRepository.save(product);
    }
}
