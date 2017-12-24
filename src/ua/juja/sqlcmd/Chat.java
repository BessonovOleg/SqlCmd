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
                return dao.connect(params);
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
                    return dao.find(params);
                }
            }

        }




        return result;
    }




}
