package by.tms.project.services;

import by.tms.project.entities.Product;
import by.tms.project.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductService {

    private static Logger log = LogManager.getLogger(ProductService.class);
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        Product createdProduct = productRepository.create(product);
        log.info("ProductService: Product was created: {}",product.getId());
        return createdProduct;
    }

    public void updateProduct(Product product){
        log.info("ProductService: Method updateProduct was called with product={}", product);
        productRepository.update(product);
    }

    public Product getProduct(int productID){
        log.info("ProductService: Method getProduct was called with productId={}", productID);
       return productRepository.getById(productID);
    }

    public void delete(int productId){
        log.info("ProductService: Method delete was called");
        productRepository.delete(productId);
    }
}
