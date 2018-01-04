package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;

public class Connect implements Command{
    private DatabaseManager databaseManager;
    private View view;
    private String helpText = "\nConnect - подключение к базе данных \n \tConnect|DatabaseName|UserName|Password\n";

    public Connect(DatabaseManager databaseManager, View view){
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.startsWith("Connect");
    }

    @Override
    public void execute(String command) {
        String dbName   = "";
        String userName = "";
        String password = "";

        try {
            String[] arrayCommand = command.split("[|]");
            dbName   = arrayCommand[1];
            userName = arrayCommand[2];
            password = arrayCommand[3];
        }catch (Exception ex){
            view.write("Ошибка формата команды");
        }

        try {
            databaseManager.connect(dbName, userName, password);
            view.write("Подключение успешно установлено!");
        }catch (Exception e){
            view.write("Ошибка подключения:" + e.getMessage());
        }
    }

    @Override
    public void printHelp() {
        view.write(helpText);
    }
}
