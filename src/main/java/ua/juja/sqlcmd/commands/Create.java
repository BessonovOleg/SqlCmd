package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Create implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Create(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "create";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String params = CommandParser.getParams(command);

        try {
            view.write(databaseManager.create(params));
        }catch (Exception e){
            view.write("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void printHelp() {
        String helpText = "create\n"+
                "Команда создает новую таблицу с заданными полями\n"+
                "Формат: create | tableName | column1 | column2 | ... | columnN\n"+
                "\tгде: tableName - имя таблицы\n"+
                "\tcolumn1 - имя первого столбца записи\n"+
                "\tcolumn2 - имя второго столбца записи\n"+
                "\tcolumnN - имя n-го столбца записи\n"+
                "--------------------------------------------------";
        view.write(helpText);
    }
}


