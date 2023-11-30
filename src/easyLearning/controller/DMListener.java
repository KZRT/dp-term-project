package easyLearning.controller;

import easyLearning.view.GUI.UserSelectFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DMListener implements ActionListener {
    private final UserSelectFrame userSelectFrame;
    private Controller controller;
    private String selectedDMValue = "EuclideanDistance";
    private JComboBox dmComboBox;

    public DMListener(UserSelectFrame userSelectFrame, JComboBox dmComboBox) {
        this.userSelectFrame = userSelectFrame;
        this.controller = UserSelectController.getInstance(null, userSelectFrame);
        this.dmComboBox = dmComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // 선택된 항목 가져오기
        String selectedDMValue = (String) dmComboBox.getSelectedItem();
        // 레이블에 선택된 항목 표시
        controller.setDistanceMeasure(selectedDMValue);
    }
}
