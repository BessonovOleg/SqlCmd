package ua.juja.sqlcmd.model;

import ua.juja.sqlcmd.views.View;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordsTableImpl implements RecordsTable{
    private ResultSet resultSet;

    public RecordsTableImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public void printTable(View view) {
        StringBuilder tbl = new StringBuilder();
        StringBuilder separate = new StringBuilder();
        int countColumns = 0;
        int[] sizeColumn = null;
        String[] columnHeaders = null;
        ArrayList<String> rows = new ArrayList<>();

        try {
            ResultSetMetaData md = resultSet.getMetaData();
            countColumns = md.getColumnCount();
            sizeColumn = new int[countColumns];
            columnHeaders = new String[countColumns];
            String tmpRow = "";

            for (int i = 1; i <= countColumns; i++) {
                columnHeaders[i-1] = md.getColumnName(i);
                sizeColumn[i-1] = md.getColumnName(i).length() + 2;
            }

            while (resultSet.next()) {
                tmpRow = "";
                for (int i = 1; i <= countColumns; i++) {
                    if (resultSet.getObject(i) != null) {
                        if (i > 1) {
                            tmpRow = tmpRow + "|" + resultSet.getString(i);
                        } else {
                            tmpRow = tmpRow + resultSet.getString(i);
                        }

                        if ((resultSet.getString(i).length() + 2) > sizeColumn[i - 1]) {
                            sizeColumn[i - 1] = resultSet.getString(i).length() + 2;
                        }
                    }else {
                        if(i > 1){
                            tmpRow = tmpRow + " | ";
                        }else {
                            tmpRow = " ";
                        }
                    }
                }
                rows.add(tmpRow);
            }
            resultSet.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        //формироуем заголовочнуя строку
        separate.append("+");
        for (int i = 0; i < sizeColumn.length; i++) {
            for (int j = 0; j < sizeColumn[i]; j++) {
                separate.append("-");
            }
            separate.append("+");
        }
        separate.append("\n");

        tbl.append(separate.toString());

        int index = 0;
        for(String header:columnHeaders){
            tbl.append("+ ");
            tbl.append(header);

            if((header.length()) < sizeColumn[index]){
                for(int j = 0; j < (sizeColumn[index] - header.length()-1);j++){
                    tbl.append(" ");
                }
            }
            index++;
        }

        tbl.append("+");
        tbl.append("\n");
        tbl.append(separate.toString());

        for(String data:rows){
            String[] cols = data.split("[|]");

            for(int colIndex = 0;colIndex < cols.length ;colIndex++){
                tbl.append("+ ");
                tbl.append(cols[colIndex]);

                if(cols[colIndex].length() < sizeColumn[colIndex]){
                    for(int j = 0; j < (sizeColumn[colIndex] - cols[colIndex].length()-1);j++){
                        tbl.append(" ");
                    }
                }
            }
            tbl.append("+\n");
        }
        tbl.append(separate.toString());
        view.write(tbl.toString());
    }
}
