package by.tms.project.services;

import by.tms.project.Logger;
import by.tms.project.entities.Item;
import by.tms.project.entities.Order;
import by.tms.project.entities.Product;
import by.tms.project.repositories.OrderRepository;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDateTime;
public class OrderService {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(OrderService.class);

    private OrderRepository orderRepository;
    private ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService){
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Logger
    public Order createOrder(){
        Order order = new Order();
        Order createdOrder = orderRepository.create(order);
        return createdOrder;
    }

    @Logger
    public void addItem(int orderId, int productId, int count){
        Item item = new Item();
        item.setCount(count);
        Product product = productService.getProduct(productId);
        if(product == null) {
            log.error("OrderService: Product id={} not found.", productId);
            return;
        }
        item.setProductId(product.getId());
        item.setPrice(product.getPrice());

        Order order = getOrder(orderId);
        if(order == null) {
            log.error("OrderService: Order id={} not found.", orderId);
            return;
        }
        order.getItems().add(item);
        orderRepository.update(order);
    }

    @Logger
    public void finishOrder(int orderId) {
       Order order = getOrder(orderId);
       if (order == null) {
           log.error("OrderService: Order not found.");
         return;
       }
        order.setFinished(true);
        order.setDateTime(LocalDateTime.now());
        orderRepository.update(order);
    }

    @Logger
    public Order getOrder(int orderId) {
        return orderRepository.getById(orderId);
    }
}
