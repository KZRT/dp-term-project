package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class KaggleCSVImporter implements Table.Importer {

    private BufferedReader in;            // null once end-of-file reached
    private String[] columnNames;
    private String tableName;

    public KaggleCSVImporter(Reader in) {
        this.in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
    }

    public void startTable() throws IOException {
        columnNames = in.readLine().split("\\s*,\\s*");
    }

    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.length;
    }

    public Iterator loadColumnNames() throws IOException {
        System.out.println("CSVImporter.loadColumnNames " + columnNames);
        for (int i = 0; i < columnNames.length; i++) {
            System.out.print(columnNames[i] + " ");
        }

        return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
    }

    public Iterator loadRow() throws IOException {
        Iterator row = null;
        if (in != null) {
            String line = in.readLine();
            if (line == null) in = null;
            else row = new ArrayIterator(line.split("\\s*,\\s*"));
        }
        return row;
    }

    public void endTable() throws IOException {
    }
}
