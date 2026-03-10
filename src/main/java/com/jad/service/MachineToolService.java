package com.jad.service;

import com.jad.connector.DBConnector;
import com.jad.entity.MachineTool;

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
}
