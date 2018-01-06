package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.model.RecordsTable;
import ua.juja.sqlcmd.model.RecordsTableImpl;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Find implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Find(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "find";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String tableName;
        try {
            tableName = CommandParser.getTableName(command);
        }catch (Exception ex){
            view.write("Ошибка формата команды");
            return;
        }

        try {
            RecordsTable recordsTable = new RecordsTableImpl(databaseManager.find(tableName));
            recordsTable.printTable(view);
        }catch (Exception e){
            view.write("Ошибка: " + e.getMessage());
        }

    }

    @Override
    public void printHelp() {
        String helpText = "find\n"+
                "Команда для получения содержимого указанной таблицы\n"+
                "Формат: find | tableName\n"+
                "\tгде tableName - имя таблицы\n"+
                "--------------------------------------------------";

        view.write(helpText);
    }
}
