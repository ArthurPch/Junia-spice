package com.jad.algorithm;

import com.jad.connector.DBConnector;
import com.jad.dto.MachineOrderDTO;
import com.jad.dto.OrderDTO;
import com.jad.entity.MachineTool;
import com.jad.entity.OperationType;
import com.jad.entity.Product;
import com.jad.entity.ProductRecipe;
import com.jad.entity.RecipeLine;
import com.jad.service.OperationTypeService;
import com.jad.service.ProductRecipeService;
import com.jad.service.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderGenerator {

    private final ProductService productService;
    private final ProductRecipeService productRecipeService;
    private final OperationTypeService operationTypeService;

    private int orderCounter = 0;
    private final Map<Integer, List<OrderDTO>> machineOrders = new LinkedHashMap<>();

    public OrderGenerator(DBConnector dbConnector) {
        this.productService = new ProductService(dbConnector);
        this.productRecipeService = new ProductRecipeService(dbConnector);
        this.operationTypeService = new OperationTypeService(dbConnector);
    }

    public List<MachineOrderDTO> generate(int idProduct, double quantity) throws SQLException {
        Product product = productService.getById(idProduct);

        if (product == null) {
            System.err.println("[Erreur] Produit introuvable : id=" + idProduct + ". Génération annulée.");
            return new ArrayList<>();
        }

        process(product, quantity);

        return machineOrders.entrySet().stream()
                .map(entry -> new MachineOrderDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void process(Product product, double quantity) throws SQLException {
        ProductRecipe recipe = productRecipeService.getByIdProduct(product.getId());

        if (recipe == null || recipe.getIdProduct() == null) {
            System.out.printf("[Matière première] %s (%.4f kg)%n", product.getLabel(), quantity);
            return;
        }

        OperationType operation = operationTypeService.getById(recipe.getIdOperationType());
        if (operation == null) {
            System.err.println("[Erreur] Opération introuvable (id=" + recipe.getIdOperationType() + ") pour le produit " + product.getLabel() + ". Étape ignorée.");
            return;
        }

        double loss = operation.getLossOfQuantity() / 100.0;
        double quantiteAvantOp;

        if (loss >= 1.0) {
            quantiteAvantOp = quantity;
        } else {
            quantiteAvantOp = quantity / (1.0 - loss);
        }

        System.out.printf("[Opération] %s sur %s : %.4f -> %.4f (perte %d%%)%n",
                operation.getLabel(), product.getLabel(), quantiteAvantOp, quantity, operation.getLossOfQuantity());

        List<MachineTool> candidates = operationTypeService.getMachineToolsForOperationTypeId(operation.getId());
        if (candidates.isEmpty()) {
            System.err.println("[Erreur] Aucune machine dispo pour l'opération : " + operation.getLabel() + ". Commande non planifiée.");
        } else {
            MachineTool machine = candidates.getFirst();
            machineOrders
                    .computeIfAbsent(machine.getId(), k -> new ArrayList<>())
                    .add(new OrderDTO(orderCounter++, product.getId(), quantiteAvantOp));
        }

        for (RecipeLine line : recipe.getRecipeLines()) {
            Product component = productService.getById(line.getIdComponent());
            if (component == null) {
                System.err.println("[Erreur] Composant introuvable (id=" + line.getIdComponent() + "). Ligne ignorée.");
                continue;
            }

            process(component, quantiteAvantOp * (line.getPercentage() / 100.0));
        }
    }
}