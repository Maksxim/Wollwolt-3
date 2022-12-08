package by.tms.project.controller;

import by.tms.project.entities.Product;
import by.tms.project.services.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable int productId){
        return service.getProduct(productId);
    }

    @PostMapping("/product")
    Product newProduct(@RequestBody @Valid Product newProduct) {
        return service.createProduct(newProduct);
    }
}
