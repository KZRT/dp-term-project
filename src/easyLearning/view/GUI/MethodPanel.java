package easyLearning.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MethodPanel extends JPanel {

    List<JCheckBox> checkBoxList;
    Color color1;


    public MethodPanel(){

        // 파일 경로를 수정하세요.
        String filePath = "src/easyLearning/model/parsing/Method.txt";

        List<JCheckBox> checkBoxList = createCheckboxList(filePath);

        setLayout(new GridLayout(4, 2));

        for (JCheckBox checkBox : checkBoxList) {
            add(checkBox);
        }
    }

    private List<JCheckBox> createCheckboxList(String filePath) {
        List<JCheckBox> checkBoxList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JCheckBox checkBox = new JCheckBox(line);
                checkBox.addActionListener(new MethodListener());
                checkBoxList.add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return checkBoxList;
    }


}

class MethodListener implements ActionListener {
    private static List<String> selectedItems = new ArrayList<>();
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();

        if (source.isSelected()) {
            // 체크박스가 선택되었을 때
            selectedItems.add(source.getText());
        } else {
            // 체크박스가 해제되었을 때
            selectedItems.remove(source.getText());
        }

        // 선택된 항목들 출력
        System.out.println("Selected Items: " + selectedItems);
    }
};


