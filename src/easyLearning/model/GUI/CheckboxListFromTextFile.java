package easyLearning.model.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckboxListFromTextFile extends JFrame {

    public CheckboxListFromTextFile() {
        super("Checkbox List from Text File");

        // 파일 경로를 수정하세요.
        String filePath = "src/easyLearning/model/parsing/Method.txt";

        List<JCheckBox> checkBoxList = createCheckboxList(filePath);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(checkBoxList.size(), 1));

        for (JCheckBox checkBox : checkBoxList) {
            panel.add(checkBox);
        }

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<JCheckBox> createCheckboxList(String filePath) {
        List<JCheckBox> checkBoxList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JCheckBox checkBox = new JCheckBox(line);
                checkBoxList.add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return checkBoxList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckboxListFromTextFile());
    }
}
