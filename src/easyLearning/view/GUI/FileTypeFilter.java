package easyLearning.view.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.table.DefaultTableModel;

public class FileTypeFilter extends FileFilter{
    private final String extension;
    private final String description;

    public FileTypeFilter(String extension, String description)
    {
        this.extension = extension;
        this.description = description;
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
        {
            return true;
        }
        return file.getName().endsWith(extension);
    }
    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }

    public static DefaultTableModel createTableFromCSV(String filePath) {
        DefaultTableModel model = new DefaultTableModel();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the header
            String[] headers = br.readLine().split(",");
            model.setColumnIdentifiers(headers);

            // Read the data
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
        }

        return model;
    }


}