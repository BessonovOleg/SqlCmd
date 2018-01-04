package ua.juja.sqlcmd.commands;


public interface Command {
    boolean canExecute(String command);
    void execute(String command);
    void printHelp();
}
