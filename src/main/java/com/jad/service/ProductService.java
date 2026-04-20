package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService extends AbstractService {
    public ProductService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getString("createdBy"),
                        resultSet.getInt("id_TypeProduct"),
                        resultSet.getBoolean("isAtomic")));
            }
        }
        return products;
    }

    public Product getById(final int id) throws SQLException {
        String query = "SELECT * FROM Product WHERE id = ?";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(resultSet.getInt("id"),
                            resultSet.getString("label"),
                            resultSet.getString("createdBy"),
                            resultSet.getInt("id_TypeProduct"),
                            resultSet.getBoolean("isAtomic"));
                }
            }
        }
        return null;
    }
}