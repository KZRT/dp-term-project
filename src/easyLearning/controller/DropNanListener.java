package easyLearning.controller;

import easyLearning.view.GUI.UserSelectFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropNanListener implements ActionListener {
    private final UserSelectFrame userSelectFrame;
    private Controller controller;

    public DropNanListener(UserSelectFrame userSelectFrame) {
        this.userSelectFrame = userSelectFrame;
        this.controller = UserSelectController.getInstance(null, userSelectFrame);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            JTable droppedColumnTable = controller.dropNan();
            userSelectFrame.setTable(droppedColumnTable);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            JOptionPane.showMessageDialog(null, "잘못된 접근입니다");
        }
    }
}
