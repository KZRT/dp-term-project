package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatasetExporter implements Table.Exporter {

    private final Writer out;
    private int width;

    private Iterator columnNames;

    boolean columnLine = true;

    public DatasetExporter(Writer out) {
        this.out = out;
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        this.columnNames = columnNames;
        storeRow(columnNames); // comma separated list of columns ids
    }

    public void storeRow(Iterator data) throws IOException {
        int i = width;
        List dataList = new ArrayList<>();
        while (data.hasNext()) {
            Object datum = data.next();

            if (datum != null && columnLine != true) {
                dataList.add(datum.toString());
                //out.write(datum.toString());
            }

            if (--i > 0 && columnLine != true) {
                dataList.add(",\t");
                //out.write(",\t");
            }
        }
        if(columnLine != true && dataList.size() >= width * 2 - 1) {
            for(int j = 0; j < dataList.size(); j++) {
                out.write(dataList.get(j).toString());
            }
        }
        if(columnLine == true) {
            columnLine = false;
        } else {
            out.write("\n");
        }

    }

    public void startTable() throws IOException {/*nothing to do*/}

    public void endTable() throws IOException {/*nothing to do*/}
}
