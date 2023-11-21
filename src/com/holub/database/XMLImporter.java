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

import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

/***
 *	Pass this importer to a {@link Table} constructor (such
 *	as
 *    {link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 *	to initialize
 *	a <code>Table</code> from
 *	a comma-sparated-value repressentation. For example:
 *	<PRE>
 *	Reader in = new FileReader( "people.csv" );
 *	people = new ConcreteTable( new CSVImporter(in) );
 *	in.close();
 *	</PRE>
 *	The input file for a table called "name" with
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
 *
 * @see Table
 * @see Table.Importer
 * @see CSVExporter
 */

public class XMLImporter implements Table.Importer {
    private BufferedReader in;            // null once end-of-file reached
    private final ArrayList<String> columnNames;
    private String tableName;
    private final String regex = "<|>|/|\\s+";

    public XMLImporter(Reader in) {
        this.in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        columnNames = new ArrayList<>();
    }

    public void startTable() throws IOException {
        in.readLine(); // skip first line
        tableName = in.readLine().trim().replaceAll(regex, "");
        in.mark(2);
        String startRow = in.readLine().replaceAll(regex, "");
        String endRow = "</" + startRow + ">";
        String line = in.readLine().trim();
        while (!line.equals(endRow)) {
            if (startRow.equals(tableName)) {
                break;
            }
            String columnName = line.split(regex)[1];
            if (columnName == null) {
                continue;
            }
            else {
                columnNames.add(columnName);
            }
            line = in.readLine().trim();
        }
        in.reset();
    }

    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.size();
    }

    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(columnNames.toArray());  //{=CSVImporter.ArrayIteratorCall}
    }

    public Iterator loadRow() throws IOException {
        Iterator row = null;
        ArrayList<String> rowValues = new ArrayList<>();
        if (in != null) {
            String startRow = in.readLine().replaceAll(regex, "");
            if (startRow.equals(tableName)) {
                return null;
            }
            String endRow = "</" + startRow + ">";
            String line = in.readLine().trim();
            while (!line.equals(endRow)) {
                String value = line.split(regex)[2];
                if (value == null) {
                    continue;
                }
                else {
                    rowValues.add(value);
                }
                line = in.readLine().trim();
            }
            row = new ArrayIterator(rowValues.toArray());
        }
        return row;
    }

    public void endTable() throws IOException {
    }
}
