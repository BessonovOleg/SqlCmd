package ua.juja.sqlcmd.controller;


import ua.juja.sqlcmd.model.DatabaseManager;

public class CommandController {

    DatabaseManager databaseManager;

    public CommandController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public String parse(String message){
        String result = "текст неопознан, наберите help для получения списка команд";
        String command;
        String params = "";
        int indexSeparator = 0;

        if (message == null) {
            return result;
        } else if (message.equals("help")) {
            result = "";
        } else if (message.equals("exit")) {
            databaseManager.closeConnection();
            result = "";
        } else if (message.equals("tables")) {
            result = databaseManager.tables();
        } else {
            String[] arrayCommand = message.split("[|]");
            command = arrayCommand[0];
            indexSeparator = message.indexOf("|");

            if(indexSeparator > 0){
               params = message.substring(indexSeparator+1,message.length());
            }


            if (command.equals("connect")) {
                try {
                    databaseManager.connect(params);
                    return "подключение успешно установлено";
                }catch (Exception e){
                    return "Ошибка подключения по причине: " + e.getMessage() + " " + e.getCause().getMessage();
                }
            }

            if(arrayCommand.length>1){
                if(command.equals("clear")){
                    return databaseManager.clear(params);
                }

                if(command.equals("drop")){
                    return databaseManager.drop(params);
                }

                if(command.equals("create")){
                    return databaseManager.create(params);
                }

                if(command.equals("find")){
                    try {
                        return databaseManager.find(params);
                    }catch (Exception e){
                        return e.getMessage();
                    }
                }

                if(command.equals("insert")){
                    return databaseManager.insert(params);
                }

                if(command.equals("update")){
                    return databaseManager.update(params);
                }

                if(command.equals("delete")){
                    return databaseManager.delete(params);
                }
            }
        }

        return result;
    }




}
