package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.MachineTool;
import com.jad.entity.OperationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MachineToolService extends AbstractService {
    public MachineToolService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<MachineTool> getAll() throws SQLException {
        final Statement statement = this.getStatement();
        List<MachineTool> machineTools = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MachineTool");
        for (int i = 0; resultSet.next(); i++) {
            machineTools.add(new MachineTool(resultSet.getInt("id"),
                                             resultSet.getString("label"),
                                             resultSet.getTime("installationDuration").toLocalTime(),
                                             resultSet.getTime("cleaningDuration").toLocalTime(),
                                             resultSet.getInt("minQuantity"),
                                             resultSet.getInt("maxQuantity")));
        }
        return machineTools;
    }

    public MachineTool getById(final int id) throws SQLException {
        final Statement statement = this.getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MachineTool WHERE id = " + id);
        if (resultSet.next()) {
            return new MachineTool(resultSet.getInt("id"),
                                   resultSet.getString("label"),
                                   resultSet.getTime("installationDuration").toLocalTime(),
                                   resultSet.getTime("cleaningDuration").toLocalTime(),
                                   resultSet.getInt("minQuantity"),
                                   resultSet.getInt("maxQuantity"));
        }
        return null;
    }

    public List<OperationType> getOperationTypesForMachineTool(final MachineTool machineTool) throws SQLException {
        return this.getOperationTypesForMachineToolId(machineTool.getId());
    }

    public List<OperationType> getOperationTypesForMachineToolId(final int machineToolId) throws SQLException {
        final Statement statement = this.getStatement();
        List<OperationType> operationTypes = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT OperationType.* FROM OperationType " +
                                                             "JOIN MachineTool_OperationType ON OperationType.id = MachineTool_OperationType.operationTypeId " +
                                                             "WHERE MachineTool_OperationType.machineToolId = " + machineToolId);
        for (int i = 0; resultSet.next(); i++) {
            operationTypes.add(new OperationType(resultSet.getInt("id"),
                                                 resultSet.getString("label"),
                                                 resultSet.getByte("minNbComponents"),
                                                 resultSet.getByte("maxNbComponents"),
                                                 resultSet.getTime("duration").toLocalTime(),
                                                 resultSet.getInt("lossOfQuantity"),
                                                 resultSet.getInt("id_ProductType")));
        }
        return operationTypes;
    }
}
