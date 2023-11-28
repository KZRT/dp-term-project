package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class KaggleCSVExporter implements Table.Exporter{
    private final Writer out;
    private int width;

    public KaggleCSVExporter(Writer out) {
        this.out = out;
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        storeRow(columnNames); // comma separated list of columns ids
    }

    public void storeRow(Iterator data) throws IOException {
        int i = width;
        while (data.hasNext()) {
            Object datum = data.next();

            if (datum != null) out.write(datum.toString());

            if (--i > 0) out.write(",\t");
        }
        out.write("\n");
    }

    public void startTable() throws IOException {/*nothing to do*/}

    public void endTable() throws IOException {/*nothing to do*/}
}
