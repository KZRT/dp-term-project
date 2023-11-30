package easyLearning.controller;

import easyLearning.view.GUI.UserSelectFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IterationListener implements ActionListener {
    private Controller controller;
    private JTextField iterationsTextField;

    public IterationListener(UserSelectFrame userSelectFrame, JTextField iterationsTextField) {
        controller = UserSelectController.getInstance(null, userSelectFrame);
        this.iterationsTextField = iterationsTextField;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int iterations = Integer.parseInt(iterationsTextField.getText());
        controller.setIterations(iterations);
    }
}
