package easyLearning.controller;

import easyLearning.view.GUI.FileTypeFilter;
import easyLearning.view.GUI.UserSelectFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DropColumnListener implements ActionListener {
    private final UserSelectFrame userSelectFrame;
    private Controller controller;
    private final JTable table;

    public DropColumnListener(UserSelectFrame userSelectFrame, JTable table) {
        this.userSelectFrame = userSelectFrame;
        this.controller = UserSelectController.getInstance(null, userSelectFrame);
        this.table = table;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            int selectedColumnNum = table.getSelectedColumn();
            String selectedColumnName = table.getColumnName(selectedColumnNum);
            JTable droppedColumnTable = controller.dropColumn(selectedColumnName);
            userSelectFrame.setTable(droppedColumnTable);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "잘못된 접근입니다");
        }
    }
}
