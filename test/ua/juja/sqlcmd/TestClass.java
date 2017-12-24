package ua.juja.sqlcmd;


import org.junit.Assert;
import org.junit.Test;
import ua.juja.sqlcmd.dababase.DbManager;


public class TestClass {

    @Test
    public void dbManagerTest(){
        DbManager dbManager = new DbManager();
        Assert.assertEquals(dbManager.connect("contactdb|postgres|postgres"),"подключение успешно установлено!");
    }

    @Test
    public void dbManager_find(){
        DbManager dbManager = new DbManager();
        dbManager.connect("contactdb|postgres|postgres");
        Assert.assertEquals(dbManager.find("test"),"col1|col2");
    }
}
