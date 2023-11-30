package easyLearning.Application;

import easyLearning.controller.UserSelectController;
import easyLearning.model.ClusteringFacade;
import easyLearning.view.GUI.UserSelectFrame;

public class Application {
    public static void main(String[] args) {
        ClusteringFacade model = new ClusteringFacade();
        UserSelectFrame userSelectFrame = new UserSelectFrame();
        UserSelectController userSelectController = UserSelectController.getInstance(model, userSelectFrame);
    }
}
