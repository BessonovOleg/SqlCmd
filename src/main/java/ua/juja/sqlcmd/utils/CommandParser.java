package ua.juja.sqlcmd.utils;

public class CommandParser {

    public static String getParams(String command){
        int indexSeparator;
        String params = "";
        indexSeparator = command.indexOf("|");

        if(indexSeparator > 0){
            params = command.substring(indexSeparator+1,command.length());
        }

        return params;
    }

    public static String[] getArray(String command){
        return command.split("[|]");
    }

    public static String getTableName(String command){
        String result;
        try {
            String[] arrayCommand = getArray(command);
            result = arrayCommand[1];
        }catch (Exception e){
            throw new RuntimeException("Ошибка формата команды");
        }
        return result;
    }
}
