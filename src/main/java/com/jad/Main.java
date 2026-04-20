package com.jad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jad.connector.DBConnector;
import com.jad.dto.MachineOrderDTO;
import com.jad.dto.OrderDTO;
import com.jad.entity.Product;
import com.jad.service.ProductService;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        ProductService productService = new ProductService(DBConnector.getInstance());
        Scanner scanner = new Scanner(System.in);

        Product product = null;
        int idProduct = 0;

        while (product == null) {
            System.out.print("Entrez l'ID du produit (ex: 147) : ");
            idProduct = scanner.nextInt();
            product = productService.getById(idProduct);
            if (product == null) {
                System.out.println("Produit introuvable.");
            }
        }

        System.out.print("Entrez la quantité de " + product.getLabel() + " : ");
        double quantity = scanner.nextDouble();

        System.out.print("Entrez l'ID de la machine à utiliser (ex: 1 pour Four 01) : ");
        int idMachine = scanner.nextInt();

        OrderDTO maCommande = new OrderDTO(0, idProduct, quantity);
        MachineOrderDTO commandeMachine = new MachineOrderDTO(idMachine, List.of(maCommande));
        List<MachineOrderDTO> payloadFinal = List.of(commandeMachine);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("Orders.json")) {
            gson.toJson(payloadFinal, writer);
            System.out.println("Le fichier Orders.json a été créé.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }

        scanner.close();
        DBConnector.getInstance().disconnect();
    }
}