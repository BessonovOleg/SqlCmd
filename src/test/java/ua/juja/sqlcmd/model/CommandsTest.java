package ua.juja.sqlcmd.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.juja.sqlcmd.commands.Connect;
import ua.juja.sqlcmd.views.View;

public class CommandsTest {

    private View monkeyView;
    private DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception{

        monkeyView = new View() {
            String result;
            @Override
            public String getInput() {
                return result;
            }

            @Override
            public void write(String text) {
                result = text;
            }
        };

        databaseManager = new PostgresDatabaseManager("127.0.0.1:5432");
    }

    @Test
    public void connectTest() throws Exception{
        Connect connectCommand = new Connect(databaseManager,monkeyView);
        connectCommand.execute("connect|contactdb|postgres|postgres");
        Assert.assertEquals("Подключение успешно установлено!",monkeyView.getInput());
    }


}
