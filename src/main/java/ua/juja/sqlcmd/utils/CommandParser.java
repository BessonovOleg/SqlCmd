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
}
