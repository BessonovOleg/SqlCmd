package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.model.RecordsTable;
import ua.juja.sqlcmd.model.RecordsTableImpl;
import ua.juja.sqlcmd.views.View;

public class Find implements Command{

    private DatabaseManager databaseManager;
    private View view;
    private final String COMMAND_TEXT = "find";

    private String helpText = "find\n"+
            "Команда для получения содержимого указанной таблицы\n"+
            "Формат: find | tableName\n"+
            "\tгде tableName - имя таблицы\n"+
            "--------------------------------------------------";

    public Find(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.toLowerCase().startsWith(COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String tableName;
        try {
            String[] arrayCommand = command.split("[|]");
            tableName = arrayCommand[1];
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
        view.write(helpText);
    }
}
