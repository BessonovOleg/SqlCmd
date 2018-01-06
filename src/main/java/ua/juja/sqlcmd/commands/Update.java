package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.model.RecordsTable;
import ua.juja.sqlcmd.model.RecordsTableImpl;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Update implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Update(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "update";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String params = CommandParser.getParams(command);

        try {
            RecordsTable recordsTable = new RecordsTableImpl(databaseManager.update(params));
            recordsTable.printTable(view);
        }catch (Exception e){
            view.write("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void printHelp() {
        String helpText = "update\n"+
                "Команда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n"+
                "Формат: update | tableName | column1 | value1 | column2 | value2\n"+
                "\tгде: tableName - имя таблицы\n"+
                "\tcolumn1 - имя столбца записи которое проверяется\n"+
                "\tvalue1 - значение которому должен соответствовать столбец column1 для обновляемой записи\n"+
                "\tcolumn2 - имя обновляемого столбца записи\n"+
                "\tvalue2 - значение обновляемого столбца записи\n"+
                "\tcolumnN - имя n-го обновляемого столбца записи\n"+
                "\tvalueN - значение n-го обновляемого столбца записи\n"+
                "--------------------------------------------------";

        view.write(helpText);
    }
}
