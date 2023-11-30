package JUnit.controller;

import easyLearning.controller.Controller;
import easyLearning.controller.UserSelectController;
import easyLearning.model.ClusteringFacade;
import easyLearning.view.GUI.UserSelectFrame;
import org.junit.Test;

import javax.swing.*;
import java.io.File;

import static java.lang.Thread.sleep;

public class TestController {
    @Test
    public void testimportCSVToTable() {
        Controller controller = UserSelectController.getInstance(null, null);
        try {
            JTable table = controller.importCSVToTable(new File("water_potability.csv"));
            System.out.println(table);
            JFrame frame = new JFrame();
            frame.add(new JScrollPane(table));
            frame.pack();
            frame.setVisible(true);
            sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCheckboxListener() {
        UserSelectFrame userSelectFrame = new UserSelectFrame();
        ClusteringFacade model = new ClusteringFacade();
        UserSelectController userSelectController = UserSelectController.getInstance(model, userSelectFrame);

    }
}
