package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.MachineTool;
import com.jad.entity.OperationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationTypeService extends AbstractService {
    public OperationTypeService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<OperationType> getAll() throws SQLException {
        List<OperationType> operationTypes = new ArrayList<>();
        String query = "SELECT * FROM OperationType";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                operationTypes.add(new OperationType(resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getByte("minNbComponents"),
                        resultSet.getByte("maxNbComponents"),
                        resultSet.getTime("duration").toLocalTime(),
                        resultSet.getInt("lossOfQuantity"),
                        resultSet.getInt("id_ProductType")));
            }
        }
        return operationTypes;
    }

    public OperationType getById(final int id) throws SQLException {
        String query = "SELECT * FROM OperationType WHERE id = ?";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new OperationType(resultSet.getInt("id"),
                            resultSet.getString("label"),
                            resultSet.getByte("minNbComponents"),
                            resultSet.getByte("maxNbComponents"),
                            resultSet.getTime("duration").toLocalTime(),
                            resultSet.getInt("lossOfQuantity"),
                            resultSet.getInt("id_ProductType"));
                }
            }
        }
        return null;
    }

    public List<MachineTool> getMachineToolsForOperationType(final OperationType operationType) throws SQLException {
        return this.getMachineToolsForOperationTypeId(operationType.getId());
    }

    public List<MachineTool> getMachineToolsForOperationTypeId(final int id) throws SQLException {
        List<MachineTool> machineTools = new ArrayList<>();
        String query = "SELECT MachineTool.* FROM OperationType_MachineTool " +
                "JOIN MachineTool ON OperationType_MachineTool.id_MachineTool = MachineTool.id " +
                "WHERE OperationType_MachineTool.id_OperationType = ?";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    machineTools.add(new MachineTool(resultSet.getInt("id"),
                            resultSet.getString("label"),
                            resultSet.getTime("installationDuration").toLocalTime(),
                            resultSet.getTime("cleaningDuration").toLocalTime(),
                            resultSet.getInt("minQuantity"),
                            resultSet.getInt("maxQuantity")));
                }
            }
        }
        return machineTools;
    }
}