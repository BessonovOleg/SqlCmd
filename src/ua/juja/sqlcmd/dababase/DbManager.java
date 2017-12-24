package ua.juja.sqlcmd.dababase;

import java.sql.*;
import java.util.ArrayList;


public class DbManager implements Dao{

    Connection connection = null;

    @Override
    public String connect(String command) {
        String result = "";

        String dbName = "";
        String userName = "";
        String password = "";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            result = e.getMessage();
            return result;
        }

        try {
            String[] arrayCommand = command.split("[|]");
            dbName = arrayCommand[0];
            userName = arrayCommand[1];
            password = arrayCommand[2];
        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dbName, userName, password);
            result = "подключение успешно установлено!";
        } catch (SQLException ex) {
            result = "ошибка подключеня\n" + ex.getMessage() ;
        }

        return result;
    }

    @Override
    public String tables() {
        if (isConnetionNull()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name as tblName\n" +
                    " FROM information_schema.tables\n" +
                    " WHERE table_schema='public'\n" +
                    " AND table_type='BASE TABLE';\n");

            while (rs.next()) {
                result.append("\t").append(rs.getString("tblName")).append("\n");
            }

            rs.close();
            stmt.close();

        }catch (SQLException ex){
            result.append(ex.getMessage());
        }

        return result.toString();
    }

    @Override
    public String clear(String tableName) {
        if (isConnetionNull()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int countDelRows = 0;

        try{
            String sql = "delete from " + tableName;
            Statement stm = connection.createStatement();
            countDelRows = stm.executeUpdate(sql);
            result.append("Команда выполнена. Удалено: ").append(countDelRows).append(" строк");
            stm.close();
        }catch (Exception ex){
            result.append("Ошибка выполнения!");
        }

        return result.toString();
    }

    @Override
    public String drop(String tableName) {
        if (isConnetionNull()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        try{
            String sql = "drop table " + tableName;
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql);
            result.append("Команда выполнена. Таблица: ").append(tableName).append(" удалена.");
            stm.close();
        }catch (Exception ex){
            result.append("Ошибка выполнения!");
        }

        return result.toString();
    }



    @Override
    public String create(String command) {
        if (isConnetionNull()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sql = new StringBuilder();

        try{
            tableName = arrayCommand[0];
            sql.append("CREATE TABLE IF NOT EXISTS ");
            sql.append(tableName);
            sql.append("(");
            sql.append(tableName+"_id ");
            sql.append("SERIAL NOT NULL PRIMARY KEY");

            for (int i = 1; i < arrayCommand.length; i++) {
                sql.append(",");
                sql.append(arrayCommand[i]);
                sql.append(" varchar(225)");
            }
            sql.append(")");

        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try{
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql.toString());
            result.append("команда выполнена успешно");
            stm.close();
        }catch (Exception ex){
            result.append("Ошибка выполнения!");
        }

        return result.toString();
    }

    @Override
    public String find(String tableName) {
        if (isConnetionNull()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int countColumns;
        int[] sizeColumn;
        String[] columnHeaders;


        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData md = rs.getMetaData();

            countColumns = md.getColumnCount();
            sizeColumn = new int[countColumns];
            columnHeaders = new String[countColumns];

            for (int i = 1; i <= countColumns; i++) {
                columnHeaders[i-1] = md.getColumnName(i);
                sizeColumn[i-1] = md.getColumnName(i).length();
            }

           ArrayList<String> rows = new ArrayList<>();
           String tmpRow = "";
/*
            while (rs.next()) {
                for (int i = 1; i <= countColumns; i++) {
                    if(i > 0){
                        tmpRow = tmpRow + "|" + rs.getString(i);
                    }else {
                        tmpRow = tmpRow + rs.getString(i);
                    }

                    if(rs.getString(i).length() > sizeColumn[i-1] ){
                        sizeColumn[i-1] = rs.getString(i).length();
                    }
                }
                rows.add(tmpRow);
            }
*/

                while (rs.next()) {
                    for (int i = 1; i <= countColumns; i++) {
                        if (i > 1) System.out.print("+\t");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + md.getColumnName(i));
                    }
                    System.out.println("");
                }


            rs.close();
            stmt.close();

        }catch (SQLException ex){
            result.append(ex.getMessage());
        }



        return result.toString();
    }

    @Override
    public String insert(String command) {
        return null;
    }

    @Override
    public String update(String command) {
        return null;
    }

    @Override
    public String delete(String command) {
        return null;
    }



    private boolean isConnetionNull(){
        if(connection == null){
            System.out.println("Не установлено соединение с базой данных");
            return true;
        }else {
            return false;
        }
    }


}
