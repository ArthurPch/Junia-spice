package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.OperationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OperationTypeService extends AbstractService {
    public OperationTypeService(final DBConnector dbConnector) {
        super(dbConnector);
    }

    public List<OperationType> getAll() throws SQLException {
        final Statement statement = this.getStatement();
        List<OperationType> operationTypes = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM OperationType");
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

    public OperationType getById(final int id) throws SQLException {
        final Statement statement = this.getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM OperationType WHERE id = " + id);
        if (resultSet.next()) {
            return new OperationType(resultSet.getInt("id"),
                                     resultSet.getString("label"),
                                     resultSet.getByte("minNbComponents"),
                                     resultSet.getByte("maxNbComponents"),
                                     resultSet.getTime("duration").toLocalTime(),
                                     resultSet.getInt("lossOfQuantity"),
                                     resultSet.getInt("id_ProductType"));
        }
        return null;
    }
}
