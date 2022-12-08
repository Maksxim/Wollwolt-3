package by.tms.project.repositories;

import by.tms.project.entities.Item;
import by.tms.project.entities.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderRepository {

    private static Logger log = LogManager.getLogger(OrderRepository.class);
    private static final String INSERT_ORDERS = "INSERT INTO orders (Date_Time) VALUES (?)";
    private static final String SELECT_ORDERS = "SELECT * from orders o left join items i on o.id = i.order_id where o.id = ?";
    private static final String UPDATE_ORDERS = "UPDATE orders set Date_Time = ?, finished = ? where id = ?";

    private static final String INSERT_ITEMS = "INSERT INTO items (product_id, price, count, order_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ITEMS = "UPDATE items set product_id = ?, price = ?, count = ? where id = ?";

    public Order create(Order order){
        log.info("OrderRepository: Method create was called");
        try (Connection connection = ConnectionManager.open()) {
            String[] generatedColumns = {"id"};
            PreparedStatement insertOrders = connection.prepareStatement(INSERT_ORDERS, generatedColumns);
            insertOrders.setString(1,order.getDateTime().toString());
            insertOrders.executeUpdate();
            ResultSet resultSet = insertOrders.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
        return order;
    }

    public Order getById(int orderId) {
        log.info("OrderRepository: Method getById was called with orderId={}", orderId);
        Order order = null;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS);
            preparedStatement.setInt(1, orderId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                Integer itemId = resultSet.getInt("i.id");
                if (itemId != null) {
                    Item item = new Item(itemId,
                            resultSet.getInt("product_id"),resultSet.getDouble("price"),
                            resultSet.getInt("count"));
                    items.add(item);
                }
                if(order == null) {
                    String dateTimeString = resultSet.getString("Date_Time");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    order = new Order(resultSet.getInt("id"), dateTime, null, resultSet.getBoolean("finished"));
                }
            }
            if(order != null) {
                order.setItems(items);
            }
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
        return order;
    }

    public void update(Order order){
        log.info("OrderRepository: update was called for orderId={}", order.getId());
        Order existingOrder = getById(order.getId());
        existingOrder.setDateTime(order.getDateTime());
        existingOrder.setFinished(order.isFinished());
        existingOrder.setItems(order.getItems());

        List<Item> itemsToCreate = order.getItems().stream().filter(item -> item.getId()==null).toList();
        List<Item> itemsToUpdate = order.getItems().stream().filter(item -> item.getId()!=null).toList();
        //TODO: delete items, implement later
        List<Item> itemsToDelete = new ArrayList<>();

        try(Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(UPDATE_ORDERS);
            preparedStatement.setString(1,order.getDateTime().toString());
            preparedStatement.setBoolean(2,order.isFinished());
            preparedStatement.setInt(3,order.getId());
            preparedStatement.executeUpdate();

            for(Item item: itemsToCreate) {
                PreparedStatement ps = connection.prepareStatement(INSERT_ITEMS);
                ps.setInt(1,item.getProductId());
                ps.setDouble(2,item.getPrice());
                ps.setInt(3,item.getCount());
                ps.setInt(4,order.getId());
                ps.executeUpdate();
                log.info("Item was created orderId={}", order.getId());
            }
            for(Item item: itemsToUpdate) {
                PreparedStatement ps = connection.prepareStatement(UPDATE_ITEMS);
                ps.setInt(1,item.getProductId());
                ps.setDouble(2,item.getPrice());
                ps.setInt(3,item.getCount());
                ps.setInt(4,item.getId());
                ps.executeUpdate();
                log.info("Item id={}, orderId={} was updated", item.getId(), order.getId());
            }
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
    }
}
