package ua.juja.sqlcmd.controller;

import ua.juja.sqlcmd.commands.Command;
import ua.juja.sqlcmd.commands.Connect;
import ua.juja.sqlcmd.commands.Help;
import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;
import java.util.ArrayList;

public class CommandController {

    private DatabaseManager databaseManager;
    private View view;
    private ArrayList<Command> commands = new ArrayList<>();

    public CommandController(DatabaseManager databaseManager,View view){
        this.databaseManager = databaseManager;
        this.view = view;

        commands.add(new Connect(this.databaseManager,this.view));
        commands.add(new Help(commands));
    }


    public void run(){

        String commandText;
        commandText = view.getInput();

        for(Command cmd:commands){
            if(cmd.canExecute(commandText)){
                cmd.execute(commandText);
                break;
            }
        }



        String result = "текст неопознан, наберите help для получения списка команд";
    }

    public void parse(String message){
        String command;
        String params = "";
        int indexSeparator = 0;

        if (message == null) {
            //return result;
        } else if (message.equals("help")) {
           // result = "";
        } else if (message.equals("exit")) {
            databaseManager.closeConnection();
           // result = "";
        } else if (message.equals("tables")) {
           /// result = databaseManager.tables();
        } else {
            String[] arrayCommand = message.split("[|]");
            command = arrayCommand[0];
            indexSeparator = message.indexOf("|");

            if(indexSeparator > 0){
               params = message.substring(indexSeparator+1,message.length());
            }


            if(arrayCommand.length>1){
                if(command.equals("clear")){
     //               return databaseManager.clear(params);
                }

                if(command.equals("drop")){
       //             return databaseManager.drop(params);
                }

                if(command.equals("create")){
         //           return databaseManager.create(params);
                }

                if(command.equals("find")){
                    try {
           //             return databaseManager.find(params);
                    }catch (Exception e){
             //           return e.getMessage();
                    }
                }

                if(command.equals("insert")){
            //        return databaseManager.insert(params);
                }

                if(command.equals("update")){
              //      return databaseManager.update(params);
                }

                if(command.equals("delete")){
                //    return databaseManager.delete(params);
                }
            }
        }


    }




}
