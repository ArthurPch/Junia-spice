package com.jad;

import com.jad.connector.DBConnector;
import com.jad.service.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnector.getInstance().getConnection(); // Teste la connexion

        MachineToolService machineToolService = new MachineToolService(DBConnector.getInstance());
        machineToolService.getAll().forEach(System.out::println);

        System.out.println("\nTest opération");
        OperationTypeService operationTypeService = new OperationTypeService(DBConnector.getInstance());
        System.out.println(operationTypeService.getById(1).toPrettyJson());

        System.out.println("\nTest recette");
        ProductRecipeService productRecipeService = new ProductRecipeService(DBConnector.getInstance());
        System.out.println(productRecipeService.getByIdProduct(200).toPrettyJson());

        DBConnector.getInstance().disconnect();
    }
}