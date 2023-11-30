package easyLearning.Application;

import easyLearning.controller.UserSelectController;
import easyLearning.model.ClusteringFacade;
import easyLearning.view.GUI.UserSelectFrame;

public class Application {
    public static void main(String[] args) {

        UserSelectFrame userSelectFrame = new UserSelectFrame();
        ClusteringFacade model = new ClusteringFacade();
        UserSelectController userSelectController = UserSelectController.getInstance(model, userSelectFrame);
    }
}
