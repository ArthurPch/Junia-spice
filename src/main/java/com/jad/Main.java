package com.jad;

import com.jad.connector.DBConnector;
import com.jad.service.MachineToolService;
import com.jad.service.OperationTypeService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MachineToolService machineToolService = new MachineToolService(DBConnector.getInstance());
//        machineToolService.getAll().forEach(System.out::println);
//        System.out.println(machineToolService.getById(1));

        OperationTypeService operationTypeService = new OperationTypeService(DBConnector.getInstance());
        //operationTypeService.getAll().forEach(System.out::println);
        operationTypeService.getMachineToolsForOperationTypeId(1).forEach(System.out::println);
//        System.out.println(operationTypeService.getById(1));
//
//        ProductService productService = new ProductService(DBConnector.getInstance());
//        productService.getAll().forEach(System.out::println);
//        System.out.println(productService.getById(1).toPrettyJson());
//
//        ProductRecipeService productReportService = new ProductRecipeService(DBConnector.getInstance());
//        productReportService.getAll().forEach(System.out::println);
//        System.out.println(productReportService.getByIdProduct(200).toPrettyJson());

        DBConnector.getInstance().disconnect();
    }
}