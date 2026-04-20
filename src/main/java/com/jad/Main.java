package com.jad;

import com.jad.connector.DBConnector;
import com.jad.entity.Product;
import com.jad.service.ProductService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        ProductService productService = new ProductService(DBConnector.getInstance());
        Scanner scanner = new Scanner(System.in);

        Product product = null;
        int idProduct = 0;

        while (product == null) {
            System.out.print("Entrez l'ID du produit : ");
            idProduct = scanner.nextInt();

            product = productService.getById(idProduct);

            if (product == null) {
                System.out.println("Produit introuvable.");
            }
        }

        System.out.print("Entrez la quantité de " + product.getLabel() + " : ");
        double quantity = scanner.nextDouble();

        System.out.println("\nRésumé : " + quantity + " de " + product.getLabel() + " (ID: " + idProduct + ")");

        scanner.close();
        DBConnector.getInstance().disconnect();
    }
}