package easyLearning.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluationListener implements ActionListener {
    private Controller controller;

    public EvaluationListener() {
        controller = UserSelectController.getInstance(null, null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();

        if (source.isSelected()) {
            // 체크박스가 선택되었을 때
            controller.addEvaluation(source.getText());
        } else {
            // 체크박스가 해제되었을 때
            controller.removeEvaluation(source.getText());
        }
    }
}
