package by.tms.project.controller;

import by.tms.project.entities.Product;
import by.tms.project.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
       this.service = service;
    }

    @GetMapping("/product")
    public List<Product> all(){
        return service.getAll();
    }

    @PostMapping("/product")
    Product newProduct(@RequestBody Product newProduct) {
        return service.createProduct(newProduct);
    }
}
