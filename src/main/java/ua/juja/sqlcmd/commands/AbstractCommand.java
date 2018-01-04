package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.views.View;
import ua.juja.sqlcmd.model.DatabaseManager;

public abstract class AbstractCommand {
    private String commandName;
    private String helpText;
    private View view;
    private DatabaseManager databaseManager;

    public abstract boolean canExecute(String command);

    public abstract void execute(String command);
}
