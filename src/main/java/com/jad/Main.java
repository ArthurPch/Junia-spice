package com.jad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jad.algorithm.OrderGenerator;
import com.jad.connector.DBConnector;
import com.jad.dto.MachineOrderDTO;
import com.jad.entity.Product;
import com.jad.service.ProductService;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnector db = DBConnector.getInstance();
        ProductService productService = new ProductService(db);
        Scanner scanner = new Scanner(System.in);

        Product product = null;
        while (product == null) {
            System.out.print("Entrez l'ID du produit (ex: 18646) : ");
            int idProduct = scanner.nextInt();
            product = productService.getById(idProduct);
            if (product == null) {
                System.out.println("Produit introuvable, réessayez.");
            }
        }

        System.out.printf("Entrez la quantité de '%s' à produire : ", product.getLabel());
        double quantity = scanner.nextDouble();
        scanner.close();

        OrderGenerator algorithm = new OrderGenerator(db);
        List<MachineOrderDTO> payload = algorithm.generate(product.getId(), quantity);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("Orders.json")) {
            gson.toJson(payload, writer);
            System.out.println("\nOrders.json généré avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur écriture fichier : " + e.getMessage());
        }

        db.disconnect();
    }
}