package by.tms.project.controller;

import by.tms.project.entities.Product;
import by.tms.project.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
       this.service = service;
    }

    @GetMapping("/")
    public String showProductList(Model model) {
        model.addAttribute("products", service.getAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Product product) {
        return "add-product";
    }

    @PostMapping("/addproduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }
        service.createProduct(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Product product = service.getProduct(id);
        model.addAttribute("product", product);

        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-product";
        }

        service.updateProduct(product);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model) {
        service.delete(id);

        return "redirect:/";
    }
}
