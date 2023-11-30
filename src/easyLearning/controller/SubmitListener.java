package easyLearning.controller;

import easyLearning.view.GUI.ResultFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SubmitListener implements ActionListener {
    private Controller controller;
    private JTable table;

    public SubmitListener(JTable table) {
        controller = UserSelectController.getInstance(null, null);
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int selectedColumnNum = table.getSelectedColumn();
            String selectedColumnName = table.getColumnName(selectedColumnNum);
            controller.startClustering(selectedColumnName);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
