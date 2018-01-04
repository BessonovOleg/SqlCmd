package ua.juja.sqlcmd.model;

public interface DatabaseManager {
    void connect(String dbName, String userName, String password);
    String tables();
    String clear(String tableName);
    String drop(String tableName);
    String create(String command);
    String find(String tableName);
    String insert(String command);
    String update(String command);
    String delete(String command);
    void closeConnection();
}
