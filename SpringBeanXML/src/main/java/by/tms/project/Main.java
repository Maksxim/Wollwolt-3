package by.tms.project;

import by.tms.project.entities.Order;
import by.tms.project.entities.Product;
import by.tms.project.services.OrderService;
import by.tms.project.services.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        ProductService productService = context.getBean(ProductService.class);
        OrderService orderService = context.getBean(OrderService.class);

        Product iphone = new Product(null,"iphone 14",1000);
        Product xiaomi = new Product(null,"xiaomi redmi note pro 12",250);
        Product computer = new Product(null,"MultiGame",1000);
        Product computer1 = new Product(null,"X552M",300);
        productService.createProduct(iphone);
        productService.createProduct(xiaomi);

        System.out.println("iphone:" + iphone);
        System.out.println("xiaomi:" + xiaomi);

        productService.delete(xiaomi.getId());
        productService.createProduct(computer);
        iphone.setPrice(900);
        productService.updateProduct(iphone);

        Order newOrder = orderService.createOrder();

        orderService.addItem(newOrder.getId(),iphone.getId(),3);
        orderService.addItem(newOrder.getId(),computer.getId(),5);

        orderService.finishOrder(newOrder.getId());

        Order order = orderService.getOrder(newOrder.getId());
        System.out.println(order);

    }
}
