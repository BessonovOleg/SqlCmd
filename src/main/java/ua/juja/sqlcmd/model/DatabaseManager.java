package ua.juja.sqlcmd.model;

import java.sql.ResultSet;

public interface DatabaseManager {
    void connect(String dbName, String userName, String password);
    String tables();
    String clear(String tableName);
    String drop(String tableName);
    String create(String command);
    ResultSet find(String tableName);
    String insert(String command);
    ResultSet update(String command);
    ResultSet delete(String command);
    void closeConnection();
}
