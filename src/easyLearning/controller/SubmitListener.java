package easyLearning.controller;

import easyLearning.view.GUI.ResultFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SubmitListener implements ActionListener {
    private Controller controller;

    public SubmitListener() {
        controller = UserSelectController.getInstance(null, null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controller.startClustering();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
