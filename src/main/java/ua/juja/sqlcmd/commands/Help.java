package ua.juja.sqlcmd.commands;

import java.util.ArrayList;

public class Help implements Command{

    ArrayList<Command> listCommands;

    public Help(ArrayList<Command> listCommands) {
        this.listCommands = listCommands;
    }

    @Override
    public boolean canExecute(String command) {
        return command.startsWith("Help");
    }

    @Override
    public void execute(String command) {
        for (Command cmd:listCommands){
            cmd.printHelp();
        }
    }

    @Override
    public void printHelp() {

    }
}
