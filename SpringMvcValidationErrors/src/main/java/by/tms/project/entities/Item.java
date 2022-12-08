package by.tms.project.entities;

public class Item {

    private Integer id;
    private int productId;
    private double price;
    private int count;

    public Item(Integer id, int productId, double price, int count) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.count = count;
    }

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
