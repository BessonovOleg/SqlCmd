package ua.juja.sqlcmd;


import org.junit.Test;
import ua.juja.sqlcmd.dababase.DbManager;
import ua.juja.sqlcmd.main.Chat;


public class TestClass {

    /*
    @Test
    public void dbManagerTest(){
        DbManager dbManager = new DbManager();
        Assert.assertEquals(dbManager.connect("test|postgres|postgres"),"подключение успешно установлено!");
    }
    */

    @Test
    public void dbManager_find(){
        DbManager dbManager = new DbManager();
        dbManager.connect("test|postgres|postgres");
        //Assert.assertEquals(dbManager.find("test"),"col1|col2");

        System.out.println(dbManager.find("test"));
    }

    @Test
    public void dbManager_insert_test(){
        DbManager dbManager = new DbManager();
        //dbManager.connect("test|postgres|postgres");
        Chat chat = new Chat(dbManager);
        System.out.println(chat.parse("connect|test|postgres|postgres"));
        System.out.println(chat.parse("insert|test|col1|Brbrbrbrbr|col2|Bubububububub|col3|wewerwersdfKoKoKo"));
        System.out.println(chat.parse("find|test"));
        System.out.println(chat.parse("exit"));
    }

    @Test
    public void dbManager_update_test(){
        DbManager dbManager = new DbManager();
        Chat chat = new Chat(dbManager);
        System.out.println(chat.parse("connect|test|postgres|postgres"));
        System.out.println(chat.parse("update|test|col1|BoBo|col2|pipi"));
        System.out.println(chat.parse("exit"));
    }


}
