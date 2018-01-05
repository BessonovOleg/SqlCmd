package ua.juja.sqlcmd.controller;

import ua.juja.sqlcmd.commands.*;
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
        initCommands();
    }

    private void initCommands(){
/*
        *сonnect
        *tables
        *clear
        *drop
        create
        *find
        insert
        update
        delete
        *help
        *exit
        *
  */

        commands.add(new Connect(databaseManager,view));
        commands.add(new Tables(databaseManager,view));
        commands.add(new Clear(databaseManager,view));
        commands.add(new Drop(databaseManager,view));
        commands.add(new Find(databaseManager,view));
        commands.add(new Help(commands,view));
        commands.add(new Exit(databaseManager,view));
    }

    public void run(){
        String commandText;
        String defaultText = "текст неопознан, наберите help для получения списка команд";
        boolean isCommandFind;

        while (true) {
            isCommandFind = false;
            commandText = view.getInput();

            for (Command cmd : commands) {
                if (cmd.canExecute(commandText)) {
                    cmd.execute(commandText);
                    isCommandFind = true;
                    break;
                }
            }

            if(!isCommandFind){
                view.write(defaultText);
            }
        }
    }
}
