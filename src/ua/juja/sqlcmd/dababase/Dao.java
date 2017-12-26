package ua.juja.sqlcmd.dababase;

public interface Dao {
    public void connect(String command);
    public String tables();
    public String clear(String tableName);
    public String drop(String tableName);
    public String create(String command);
    public String find(String tableName);
    public String insert(String command);
    public String update(String command);
    public String delete(String command);
}
