package easyLearning.controller;

import easyLearning.model.ClusteringFacade;
import easyLearning.view.GUI.UserSelectFrame;
import com.holub.database.*;

import javax.swing.*;
import java.io.*;

public class UserSelectController implements Controller {
    private static UserSelectController userSelectController;
    private final ClusteringFacade model;
    private final UserSelectFrame view;
    private Database database;
    private File file;
    private UserSelectController(ClusteringFacade model, UserSelectFrame view) {
        this.model = model;
        this.view = view;
        database = new Database();
    }

    public static UserSelectController getInstance(ClusteringFacade model, UserSelectFrame view) {
        if (userSelectController == null) {
            userSelectController = new UserSelectController(model, view);
        }
        return userSelectController;
    }

    public JTable importCSVToTable(File file) throws IOException {
        this.file = file;
        Table table = TableFactory.create(new CSVImporter(new FileReader(file)));
        JTableExporter jTableExporter = new JTableExporter();
        table.export(jTableExporter);
        return jTableExporter.getJTable();
    }

    public JTable dropColumn(String columnName) throws IOException {
        database.dropColumn(file, columnName);
        return importCSVToTable(new File( "ColumnDropped_" + file.getName()));
    }
}
