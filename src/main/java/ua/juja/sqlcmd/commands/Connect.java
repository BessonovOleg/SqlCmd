package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;

public class Connect implements Command{
    private DatabaseManager databaseManager;
    private View view;
    public final String COMMAND_TEXT = "connect";
    private String helpText =  "сonnect\n" +
            "Команда для подключения к соответствующей БД\n" +
            "Формат команды: connect | database | username | password\n" +
            "\t где: database - имя БД\n" +
            "\t username -  имя пользователя БД\n" +
            "\t password - пароль пользователя БД\n" +
            "--------------------------------------------------";

    public Connect(DatabaseManager databaseManager, View view){
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.toLowerCase().startsWith(COMMAND_TEXT);
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
            return;
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
