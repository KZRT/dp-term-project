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

public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private String tableName;

    public HTMLExporter(Writer out) throws IOException {
        this.out = out;
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    }

    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = (tableName == null) ? "<anonymous>" : tableName;
        // Writing table name and start table
        out.write("\t\t<h1>" + this.tableName + "</h1>\n");
        out.write("\t\t<table>\n");
        // Starting table header
        out.write("\t\t\t<tr>\n");
        // For each column name, write as header
        while (columnNames.hasNext()) {
            Object nextData = columnNames.next();
            if(nextData == null) continue;
            out.write("\t\t\t\t<th>" + nextData.toString() + "</th>\n");
        }
        // Closing table header
        out.write("\t\t\t</tr>\n");
    }

    public void storeRow(Iterator data) throws IOException {
        int i = 0;
        // Starting table row
        out.write("\t\t\t<tr>\n");
        // For each column value, write as data
        while (data.hasNext()) {
            Object datum = data.next();
            if (datum != null) {
                out.write("\t\t\t\t<td>");
                out.write(datum.toString());
                out.write("</td>\n");
            } else {
                // Writing empty data
                out.write("\t\t\t\t<td></td>\n");
            }
        }
        // Closing table row
        out.write("\t\t\t</tr>\n");
    }

    public void startTable() throws IOException {
        // CSS for styling the page
        out.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                " \n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<title>Database Table HTML Exporter</title>\n" +
                "\t<style>\n" +
                "\t\ttable {\n" +
                "\t\t\tmargin: 0 auto;\n" +
                "\t\t\tfont-size: large;\n" +
                "\t\t\tborder: 1px solid black;\n" +
                "\t\t}\n" +
                " \n" +
                "\t\th1 {\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tcolor: #006600;\n" +
                "\t\t\tfont-size: xx-large;\n" +
                "\t\t\tfont-family: 'Gill Sans', 'Gill Sans MT',\n" +
                "\t\t\t' Calibri', 'Trebuchet MS', 'sans-serif';\n" +
                "\t\t}\n" +
                " \n" +
                "\t\ttd {\n" +
                "\t\t\tbackground-color: #E4F5D4;\n" +
                "\t\t\tborder: 1px solid black;\n" +
                "\t\t}\n" +
                " \n" +
                "\t\tth,\n" +
                "\t\ttd {\n" +
                "\t\t\tfont-weight: bold;\n" +
                "\t\t\tborder: 1px solid black;\n" +
                "\t\t\tpadding: 10px;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                " \n" +
                "\t\ttd {\n" +
                "\t\t\tfont-weight: lighter;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n");

        // Starting body of the page
        out.write("<body>\n" +
                "\t<section>\n");
    }

    public void endTable() throws IOException {
        // Closing table, section and body
        out.write("\t\t</table>\n" +
                "\t</section>\n" +
                "</body>\n" +
                "</html>");
    }
}
