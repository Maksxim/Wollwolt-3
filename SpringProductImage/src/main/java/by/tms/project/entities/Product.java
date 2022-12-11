package by.tms.project.entities;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class Product {

    private Integer id;
    @NotNull
    @Size(min=3, max=256)
    private String description;
    @DecimalMin(value = "0.0",inclusive = false)
    private double price;
    private String imagePath;

    public Product(Integer id, String description, double price, String imagePath) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
