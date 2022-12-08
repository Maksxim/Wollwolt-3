package by.tms.project.services;

import by.tms.project.Logger;
import by.tms.project.entities.Product;
import by.tms.project.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(ProductService.class);
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Logger
    public Product createProduct(Product product) {
        Product createdProduct = productRepository.create(product);
        return createdProduct;
    }

    @Logger
    public void updateProduct(Product product){
        productRepository.update(product);
    }

    @Logger
    public Product getProduct(int productID){
       return productRepository.getById(productID);
    }

    @Logger
    public void delete(int productId){

        productRepository.delete(productId);
    }
    @Logger
    public List<Product> getAll(){
        return productRepository.getAll();
    }
}
