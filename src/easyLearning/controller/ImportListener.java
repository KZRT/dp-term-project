package easyLearning.controller;

import easyLearning.view.GUI.FileTypeFilter;
import easyLearning.view.GUI.UserSelectFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImportListener implements ActionListener {
    private final UserSelectFrame userSelectFrame;
    private Controller controller;

    public ImportListener(UserSelectFrame userSelectFrame) {
        this.userSelectFrame = userSelectFrame;
        this.controller = UserSelectController.getInstance(null, userSelectFrame);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fs = new JFileChooser(new File("c:\\"));
        fs.setDialogTitle("Open a File");
        fs.setFileFilter(new FileTypeFilter(".csv", "CSV File"));
        int result = fs.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fs.getSelectedFile();
            try {
                userSelectFrame.setPleaseInsertFileFormatTextArea(file.getAbsolutePath());
                userSelectFrame.setTable(controller.importCSVToTable(file));
                userSelectFrame.updateTable();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }
}
