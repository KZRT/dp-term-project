package easyLearning.controller;

import easyLearning.view.GUI.FileTypeFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ResultListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fs = new JFileChooser(new File("c:\\"));
        fs.setDialogTitle("Save a File");
        fs.setFileFilter(new FileTypeFilter("folder", "폴더이름"));
        int result = fs.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fs.getSelectedFile();
            try {
                //SaveFile(file.getAbsolutePath());
//                fileSave(file, file.getAbsolutePath(), file.getName());
                //SaveFile(file.getAbsolutePath());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }
}
