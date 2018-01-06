package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Insert implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Insert(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "insert";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String params = CommandParser.getParams(command);

        try {
            view.write(databaseManager.insert(params));
        }catch (Exception e){
            view.write("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void printHelp() {
        String helpText = "insert\n"+
                "Команда для вставки одной строки в заданную таблицу\n"+
                "Формат: insert | tableName | column1 | value1 | column2 | value2 | ... | columnN | valueN\n"+
                "\tгде: tableName - имя таблицы\n"+
                "\tcolumn1 - имя первого столбца записи\n"+
                "\tvalue1 - значение первого столбца записи\n"+
                "\tcolumn2 - имя второго столбца записи\n"+
                "\tvalue2 - значение второго столбца записи\n"+
                "\tcolumnN - имя n-го столбца записи\n"+
                "\tvalueN - значение n-го столбца записи\n"+
                "--------------------------------------------------";
        view.write(helpText);
    }
}
