package ua.juja.sqlcmd.model;
import java.sql.*;

public class PostgresDatabaseManager implements DatabaseManager {

    private String socket;
    private String notConnectedText = "Подключение к базе не установлено!";
    Connection connection = null;

    public PostgresDatabaseManager(String socket) {
        this.socket = socket;
    }

    @Override
    public void connect(String dbName, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://"+socket+"/" + dbName+"?loggerLevel=OFF", userName, password);
        } catch (Exception ex) {
                connection = null;
                throw new RuntimeException(ex);
        }
    }

    @Override
    public String tables() {
        if (isConnectionNull()) {
            return notConnectedText;
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
        if (isConnectionNull()) {
            return notConnectedText;
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
        if (isConnectionNull()) {
            return notConnectedText;
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
        if (isConnectionNull()) {
            return notConnectedText;
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

            for (int i = 1; i < arrayCommand.length; i++) {
                if(i > 1){
                    sql.append(",");
                }
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
    public ResultSet find(String tableName) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        ResultSet rs = null;
        StringBuilder result = new StringBuilder();
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from " + tableName);
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return rs;
    }



    @Override
    public String insert(String command) {
        if (isConnectionNull()) {
            return notConnectedText;
        }

        StringBuilder result = new StringBuilder();
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sql = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sql.append("INSERT INTO ");
            sql.append(tableName);
            sql.append("(");

            for (int indexCloumns = 1; indexCloumns < arrayCommand.length; indexCloumns += 2) {
                if (indexCloumns > 1) {
                    sql.append(",");
                }
                sql.append(arrayCommand[indexCloumns]);
            }

            sql.append(") VALUES (");

            for (int indexValues = 2; indexValues < arrayCommand.length; indexValues += 2) {
                if (indexValues > 2) {
                    sql.append(",");
                }
                sql.append("'");
                sql.append(arrayCommand[indexValues]);
                sql.append("'");
            }

            sql.append(");");

        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql.toString());
            result.append("Команда выполнена успешно");
        }catch (SQLException ex){
            result.append("Ошибка вставки:" + ex.getMessage());
        }
        return result.toString();
    }


    @Override
    public String update(String command) {
        if (isConnectionNull()) {
            return notConnectedText;
        }

        StringBuilder result = new StringBuilder();
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sqlUpdate = new StringBuilder();
        StringBuilder sqlWhere  = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sqlUpdate.append("UPDATE ");
            sqlUpdate.append(tableName);
            sqlUpdate.append(" set ");
            sqlSelect.append("select ");

            for (int indexCloumns = 1; indexCloumns < arrayCommand.length; indexCloumns += 4) {
                if (indexCloumns > 1) {
                    sqlUpdate.append(",");
                    sqlSelect.append(",");
                }

                sqlUpdate.append(arrayCommand[indexCloumns]);
                sqlUpdate.append("='");
                sqlUpdate.append(arrayCommand[indexCloumns+1]);
                sqlUpdate.append("'");

                sqlSelect.append(arrayCommand[indexCloumns]);
            }

            sqlWhere.append(" where ");

            for (int indexValues = 3; indexValues < arrayCommand.length; indexValues += 4) {
                if (indexValues > 3) {
                    sqlWhere.append(" and ");
                }
                sqlWhere.append(arrayCommand[indexValues]);
                sqlWhere.append(" = '");
                sqlWhere.append(arrayCommand[indexValues+1]);
                sqlWhere.append("'");
            }

            sqlUpdate.append(sqlWhere.toString());

            sqlSelect.append(" from ");
            sqlSelect.append(tableName);
            sqlSelect.append(sqlWhere.toString());

        }catch (Exception ex){
            return "Ошибка формата команды";
        }

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect.toString());
            //result.append(printRecordSet(rs));
        }catch (SQLException ex){
            result.append("Ошибка:" + ex.getMessage());
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sqlUpdate.toString());
            result.append("Команда выполнена успешно");
        }catch (SQLException ex){
            result.append("Ошибка обновления:" + ex.getMessage());
        }

        return result.toString();
    }


    @Override
    public ResultSet delete(String command) {
        if (isConnectionNull()) {
            throw new RuntimeException(notConnectedText);
        }

        ResultSet rs;
        String[] arrayCommand = command.split("[|]");
        String tableName;
        StringBuilder sqlDelete = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();
        StringBuilder sqlWhere  = new StringBuilder();

        try {
            tableName = arrayCommand[0];
            sqlDelete.append("Delete from ");
            sqlDelete.append(tableName);
            sqlWhere.append(" where ");
            sqlSelect.append("select ");

            for (int indexColumns = 1; indexColumns < arrayCommand.length; indexColumns += 2) {
                if (indexColumns > 1) {
                    sqlWhere.append(" and ");
                    sqlSelect.append(",");
                }

                sqlWhere.append(arrayCommand[indexColumns]);
                sqlWhere.append("='");
                sqlWhere.append(arrayCommand[indexColumns+1]);
                sqlWhere.append("'");

                sqlSelect.append(arrayCommand[indexColumns]);
            }

            sqlSelect.append(" from ");
            sqlSelect.append(tableName);
            sqlSelect.append(sqlWhere.toString());
            sqlDelete.append(sqlWhere.toString());

        }catch (Exception ex){
            throw new RuntimeException("Ошибка формата команды");
        }

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlSelect.toString());
        }catch (SQLException ex){
            throw new RuntimeException("Ошибка:" + ex.getMessage());
        }

        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(sqlDelete.toString());
        }catch (SQLException ex){
            throw new RuntimeException("Ошибка обновления:" + ex.getMessage());
        }

        return rs;
    }



    private boolean isConnectionNull(){
        if(connection == null){
            return true;
        }else {
            return false;
        }
    }

/*
    public String printRecordSet(ResultSet rs) {
        StringBuilder result = new StringBuilder();
        StringBuilder separate = new StringBuilder();
        int countColumns = 0;
        int[] sizeColumn = null;
        String[] columnHeaders = null;
        ArrayList<String> rows = new ArrayList<>();

        try {
            ResultSetMetaData md = rs.getMetaData();
            countColumns = md.getColumnCount();
            sizeColumn = new int[countColumns];
            columnHeaders = new String[countColumns];

            for (int i = 1; i <= countColumns; i++) {
                columnHeaders[i-1] = md.getColumnName(i);
                sizeColumn[i-1] = md.getColumnName(i).length() + 2;
            }

            String tmpRow = "";

            while (rs.next()) {
                tmpRow = "";
                for (int i = 1; i <= countColumns; i++) {
                    if (rs.getObject(i) != null) {
                        if (i > 1) {
                            tmpRow = tmpRow + "|" + rs.getString(i);
                        } else {
                            tmpRow = tmpRow + rs.getString(i);
                        }

                        if ((rs.getString(i).length() + 2) > sizeColumn[i - 1]) {
                            sizeColumn[i - 1] = rs.getString(i).length() + 2;
                        }
                    }else {
                        if(i > 1){
                            tmpRow = tmpRow + " | ";
                        }else {
                            tmpRow = " ";
                        }
                    }
                }
                rows.add(tmpRow);
            }
            rs.close();
        }catch (SQLException ex){
            result.append(ex.getMessage());
        }

        //формироуем заголовочнуя строку
        separate.append("+");
        for (int i = 0; i < sizeColumn.length; i++) {
            for (int j = 0; j < sizeColumn[i]; j++) {
                separate.append("-");
            }
            separate.append("+");
        }
        separate.append("\n");

        result.append(separate.toString());

        int index = 0;
        for(String header:columnHeaders){
            result.append("+ ");
            result.append(header);

            if((header.length()) < sizeColumn[index]){
                for(int j = 0; j < (sizeColumn[index] - header.length()-1);j++){
                    result.append(" ");
                }
            }
            index++;
        }

        result.append("+");
        result.append("\n");
        result.append(separate.toString());

        for(String data:rows){
            String[] cols = data.split("[|]");

            for(int colIndex = 0;colIndex < cols.length ;colIndex++){
                result.append("+ ");
                result.append(cols[colIndex]);

                if(cols[colIndex].length() < sizeColumn[colIndex]){
                    for(int j = 0; j < (sizeColumn[colIndex] - cols[colIndex].length()-1);j++){
                        result.append(" ");
                    }
                }
            }
            result.append("+\n");
        }
        result.append(separate.toString());
        return result.toString();
    }
*/



    @Override
    public void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
