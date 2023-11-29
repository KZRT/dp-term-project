package easyLearning.model.GUI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DMPanel extends JPanel {
    private JComboBox dropdown;

    public DMPanel(){
        // 드롭다운 버튼 초기화
        dropdown = new JComboBox<>();
        populateDropdown("src/easyLearning/model/parsing/DistanceMeasurement.txt"); // 텍스트 파일 경로를 설정하세요
    }

    private void populateDropdown(String filePath) {
        // 텍스트 파일을 읽어와서 드롭다운 버튼에 추가
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dropdown.addItem(line);
            }
            updateUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
