package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.model.RecordsTable;
import ua.juja.sqlcmd.model.RecordsTableImpl;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Delete implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Delete(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "delete";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String params = CommandParser.getParams(command);

        try {
            RecordsTable recordsTable = new RecordsTableImpl(databaseManager.delete(params));
            recordsTable.printTable(view);
        }catch (Exception e){
            view.write("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void printHelp() {
        String helpText = "delete\n"+
                "Команда удаляет одну или несколько записей для которых соблюдается условие column = value\n"+
                "Формат: delete|tableName|column|value\n"+
                "\tгде: tableName - имя таблицы\n"+
                "\tColumn - имя столбца записи которое проверяется\n"+
                "\tvalue - значение которому должен соответствовать столбец column1 для удаляемой записи\n"+
                "--------------------------------------------------";

        view.write(helpText);
    }
}
