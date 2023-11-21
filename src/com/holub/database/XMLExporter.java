/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

/***
 *	Pass this exporter to a {@link Table#export} implementation to
 *	create a comma-sparated-value version of a {@link Table}.
 *	For example:
 *	<PRE>
 *	Table people  = TableFactory.create( ... );
 *	//...
 *	Writer out = new FileWriter( "people.csv" );
 *	people.export( new CSVExporter(out) );
 *	out.close();
 *	</PRE>
 *	The output file for a table called "name" with
 *	columns "first," "last," and "addrId" would look
 *	like this:
 *	<PRE>
 *	name
 *	first,	last,	addrId
 *	Fred,	Flintstone,	1
 *	Wilma,	Flintstone,	1
 *	Allen,	Holub,	0
 *	</PRE>
 *	The first line is the table name, the second line
 *	identifies the columns, and the subsequent lines define
 *	the rows.
 *
 * @include /etc/license.txt
 * @see Table
 * @see Table.Exporter
 * @see CSVImporter
 */

public class XMLExporter implements Table.Exporter {
    private final Writer out;
    private int width;
    private final ArrayList<String> columnNames;
    private String tableName;
    private int height;

    public XMLExporter(Writer out) throws IOException {
        this.out = out;
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        columnNames = new ArrayList<>();
        this.height = 1;
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.width = width;
        out.write(tableName == null ? "<anonymous>" : "<" + tableName + ">"); // start table
        out.write("\n");
        this.tableName = (tableName == null) ? "<anonymous>" : tableName;
        // Create an array of column names
        while (columnNames.hasNext()) {
            Object nextData = columnNames.next();
            if(nextData == null) continue;
            String columnName = nextData.toString();
            this.columnNames.add(columnName);
        }
    }

    public void storeRow(Iterator data) throws IOException {
        int i = 0;
        String nextColumnName;
        out.write("\t<" + tableName + height + ">\n");
        while (data.hasNext()) {
            nextColumnName = columnNames.get(i++);
            Object datum = data.next();
            if (datum != null) {
                out.write("\t\t<" + nextColumnName + ">");
                out.write(datum.toString());
                out.write("</" + nextColumnName + ">\n");
            } else {
                out.write("\t\t<" + nextColumnName + "/>\n");
                out.write("NULL");
                out.write("</" + nextColumnName + ">\n");
            }
        }
        out.write("\t</" + tableName +  height + ">\n");
        height++;
    }

    public void startTable() throws IOException {
        out.write("");
    }

    public void endTable() throws IOException {
        out.write("</" + tableName + ">");
    }
}
