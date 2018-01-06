package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.views.View;

import java.util.ArrayList;

public class Help implements Command{

    ArrayList<Command> listCommands;
    private View view;

    private String helpText = "help\n"+
             "Команда выводит в консоль список всех доступных команд\n"+
             "Формат: help (без параметров)\n"+
             "--------------------------------------------------";
    public final String COMMAND_TEXT = "help";

    public Help(ArrayList<Command> listCommands,View view) {
        this.listCommands = listCommands;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        for (Command cmd:listCommands){
            cmd.printHelp();
        }
    }

    @Override
    public void printHelp() {
        view.write(helpText);
    }
}
