package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.Product;
import com.jad.entity.ProductRecipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRecipeService extends AbstractService {
    public ProductRecipeService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<ProductRecipe> getAll() throws SQLException {
        final Statement statement = this.getStatement();
        List<ProductRecipe> productRecipes = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ProductRecipe");
        for (int i = 0; resultSet.next(); i++) {
            productRecipes.add(new ProductRecipe(resultSet.getInt("id_Product"),
                                                 resultSet.getInt("id_OperationType")));
        }
        return productRecipes;
    }

    public ProductRecipe getByProduct(final Product product) throws SQLException {
        return this.getByIdProduct(product.getId());
    }

    public ProductRecipe getByIdProduct(final int id) throws SQLException {
        final Statement statement = this.getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ProductRecipe WHERE id_Product = " + id);
        if (resultSet.next()) {
            return new ProductRecipe(resultSet.getInt("id_Product"),
                                     resultSet.getInt("id_OperationType"));
        }
        return null;
    }
}
