package ua.juja.sqlcmd;


import ua.juja.sqlcmd.dababase.Dao;

public class Chat {

    Dao dao;

    public Chat(Dao dao){
        this.dao = dao;
    }

    public String getHelloMsg(){
        return TextMessages.helloText;
    }

    public String getGoodbyMsg(){
        return TextMessages.goodbyText;
    }


    public String parse(String message){
        String result = "текст неопознан, наберите help для получения списка команд";
        String command;
        String params = "";
        int indexSeparator = 0;

        if (message == null) {
            return result;
        } else if (message.equals("help")) {
            result = TextMessages.helpText;
        } else if (message.equals("exit")) {
            dao.closeConnection();
            result = TextMessages.goodbyText;
        } else if (message.equals("tables")) {
            result = dao.tables();
        } else {
            String[] arrayCommand = message.split("[|]");
            command = arrayCommand[0];
            indexSeparator = message.indexOf("|");

            if(indexSeparator > 0){
               params = message.substring(indexSeparator+1,message.length());
            }

            if (command.equals("connect")) {
                try {
                    dao.connect(params);
                    return "подключение успешно установлено";
                }catch (Exception e){
                    return "Ошибка подключения по причине: " + e.getMessage() + " " + e.getCause().getMessage();
                }
            }

            if(arrayCommand.length>1){
                if(command.equals("clear")){
                    return dao.clear(params);
                }

                if(command.equals("drop")){
                    return dao.drop(params);
                }

                if(command.equals("create")){
                    return dao.create(params);
                }

                if(command.equals("find")){
                    try {
                        return dao.find(params);
                    }catch (Exception e){
                        return e.getMessage();
                    }
                }

                if(command.equals("insert")){
                    return dao.insert(params);
                }

                if(command.equals("update")){
                    return dao.update(params);
                }

                if(command.equals("delete")){
                    return dao.delete(params);
                }
            }
        }



        return result;
    }




}
