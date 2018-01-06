package ua.juja.sqlcmd.utils;

public class CommandChecker {
    public static boolean check(String command,String template){
        return command.toLowerCase().startsWith(template);
    }
}
