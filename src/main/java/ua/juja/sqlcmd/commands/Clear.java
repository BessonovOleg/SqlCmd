package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;

public class Clear implements Command{
    private DatabaseManager databaseManager;
    private View view;
    public final String COMMAND_TEXT = "clear";
    private String helpText = "Clear\n"+
             "Команда очищает содержимое указанной (всей) таблицы\n"+
             "Формат: Clear | tableName\n"+
             "где tableName - имя очищаемой таблицы\n"+
             "--------------------------------------------------";

    public Clear(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.toLowerCase().startsWith(COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String tableName = "";

        try {
            String[] arrayCommand = command.split("[|]");
            tableName = arrayCommand[1];
        }catch (Exception ex){
            view.write("Ошибка формата команды");
        }

        view.write(databaseManager.clear(tableName));
    }

    @Override
    public void printHelp() {
        view.write(helpText);
    }
}
