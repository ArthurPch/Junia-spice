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

public class MachineToolService extends AbstractService {
    public MachineToolService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<MachineTool> getAll() throws SQLException {
        List<MachineTool> machineTools = new ArrayList<>();
        String query = "SELECT * FROM MachineTool";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                machineTools.add(new MachineTool(resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getTime("installationDuration").toLocalTime(),
                        resultSet.getTime("cleaningDuration").toLocalTime(),
                        resultSet.getInt("minQuantity"),
                        resultSet.getInt("maxQuantity")));
            }
        }
        return machineTools;
    }

    public MachineTool getById(final int id) throws SQLException {
        String query = "SELECT * FROM MachineTool WHERE id = ?";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MachineTool(resultSet.getInt("id"),
                            resultSet.getString("label"),
                            resultSet.getTime("installationDuration").toLocalTime(),
                            resultSet.getTime("cleaningDuration").toLocalTime(),
                            resultSet.getInt("minQuantity"),
                            resultSet.getInt("maxQuantity"));
                }
            }
        }
        return null;
    }

    public List<OperationType> getOperationTypesForMachineTool(final MachineTool machineTool) throws SQLException {
        return this.getOperationTypesForMachineToolId(machineTool.getId());
    }

    public List<OperationType> getOperationTypesForMachineToolId(final int machineToolId) throws SQLException {
        List<OperationType> operationTypes = new ArrayList<>();
        String query = "SELECT OperationType.* FROM OperationType " +
                "JOIN MachineTool_OperationType ON OperationType.id = MachineTool_OperationType.operationTypeId " +
                "WHERE MachineTool_OperationType.machineToolId = ?";

        try (Connection conn = this.getDbConnector().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, machineToolId);
            try (ResultSet resultSet = statement.executeQuery()) {
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
        }
        return operationTypes;
    }
}