package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Clear implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Clear(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "clear";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String tableName = "";

        try {
            String[] arrayCommand = CommandParser.getArray(command);
            tableName = arrayCommand[1];
        }catch (Exception ex){
            view.write("Ошибка формата команды");
        }

        view.write(databaseManager.clear(tableName));
    }

    @Override
    public void printHelp() {
        String helpText = "Clear\n"+
                "Команда очищает содержимое указанной (всей) таблицы\n"+
                "Формат: Clear | tableName\n"+
                "где tableName - имя очищаемой таблицы\n"+
                "--------------------------------------------------";
        view.write(helpText);
    }
}
