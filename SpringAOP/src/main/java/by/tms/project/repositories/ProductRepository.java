package by.tms.project.repositories;

import by.tms.project.Logger;
import by.tms.project.entities.Product;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {

    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(ProductRepository.class);

    private static final String INSERT_PRODUCTS = "INSERT INTO products (description, price) VALUES (?,?);";

    @Logger
    public Product create(Product product){
        try (Connection connection = ConnectionManager.open()) {
            String[] generatedColumns = {"id"};
            PreparedStatement insertProducts = connection.prepareStatement(INSERT_PRODUCTS, generatedColumns);
            insertProducts.setString(1,product.getDescription());
            insertProducts.setDouble(2,product.getPrice());
            insertProducts.executeUpdate();
            ResultSet resultSet = insertProducts.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
        return product;
    }

    @Logger
    public Product getById(int productId) {
        Product product = null;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products where id = ?");
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product(resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
        return product;
    }

    @Logger
    public void update(Product product){

        try(Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update products set description = ?, price = ? where id = ?;");
            preparedStatement.setString(1,product.getDescription());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
    }

    @Logger
    public void delete(int productId){
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products where id = ?");
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Exception during DB connection: {}", e.getMessage(), e);
        }
    }
}
