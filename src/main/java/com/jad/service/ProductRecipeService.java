package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.Product;
import com.jad.entity.ProductRecipe;
import com.jad.entity.RecipeLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRecipeService extends AbstractService {
    public ProductRecipeService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<ProductRecipe> getAll() throws SQLException {
        List<ProductRecipe> productRecipes = new ArrayList<>();
        String query = "SELECT * FROM ProductRecipe " +
                "INNER JOIN RecipeLine ON ProductRecipe.id_Product = RecipeLine.id_Product " +
                "ORDER BY ProductRecipe.id_Product";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<RecipeLine> recipeLines = new ArrayList<>();
            Integer currentIdProduct = null;
            Integer currentIdOperationType = null;

            while (resultSet.next()) {
                if (currentIdProduct == null) {
                    currentIdProduct = resultSet.getInt("id_Product");
                    currentIdOperationType = resultSet.getInt("id_OperationType");
                } else if (currentIdProduct != resultSet.getInt("id_Product")) {
                    productRecipes.add(new ProductRecipe(currentIdProduct,
                            currentIdOperationType,
                            new ArrayList<>(recipeLines)));
                    recipeLines.clear();
                    currentIdProduct = resultSet.getInt("id_Product");
                    currentIdOperationType = resultSet.getInt("id_OperationType");
                }
                recipeLines.add(new RecipeLine(resultSet.getInt("id_Product"),
                        resultSet.getInt("id_Component"),
                        resultSet.getFloat("percentage")));
            }

            if (currentIdProduct != null) {
                productRecipes.add(new ProductRecipe(currentIdProduct, currentIdOperationType, recipeLines));
            }
        }
        return productRecipes;
    }

    public ProductRecipe getByProduct(final Product product) throws SQLException {
        return this.getByIdProduct(product.getId());
    }

    public ProductRecipe getByIdProduct(final int id) throws SQLException {
        String query = "SELECT * FROM ProductRecipe " +
                "INNER JOIN RecipeLine ON ProductRecipe.id_Product = RecipeLine.id_Product " +
                "WHERE ProductRecipe.id_Product = ?";

        List<RecipeLine> recipeLines = new ArrayList<>();
        Integer currentIdProduct = null;
        Integer currentIdOperationType = null;

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id); // Sécurité SQL Injection
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (currentIdProduct == null) {
                        currentIdProduct = resultSet.getInt("id_Product");
                        currentIdOperationType = resultSet.getInt("id_OperationType");
                    }
                    recipeLines.add(new RecipeLine(resultSet.getInt("id_Product"),
                            resultSet.getInt("id_Component"),
                            resultSet.getFloat("percentage")));
                }
            }
        }

        return new ProductRecipe(currentIdProduct, currentIdOperationType, recipeLines);
    }
}