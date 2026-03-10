package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService extends AbstractService {
    public ProductService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<Product> getAll() throws SQLException {
        final Statement statement = this.getStatement();
        List<Product> products = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Product");
        for (int i = 0; resultSet.next(); i++) {
            products.add(new Product(resultSet.getInt("id"),
                                     resultSet.getString("label"),
                                     resultSet.getString("createdBy"),
                                     resultSet.getInt("id_TypeProduct"),
                                     resultSet.getBoolean("isAtomic")));
        }
        return products;
    }

    public Product getById(final int id) throws SQLException {
        final Statement statement = this.getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Product WHERE id = " + id);
        if (resultSet.next()) {
            return new Product(resultSet.getInt("id"),
                               resultSet.getString("label"),
                               resultSet.getString("createdBy"),
                               resultSet.getInt("id_TypeProduct"),
                               resultSet.getBoolean("isAtomic"));
        }
        return null;
    }
}
