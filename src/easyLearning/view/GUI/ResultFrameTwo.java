package easyLearning.view.GUI;

import javax.swing.*;
import java.awt.*;

public class ResultFrameTwo extends JFrame {
    private JTable table1;
    private JLabel result;
    private JScrollPane scrollPane1;
    private JPanel contentPane;


    public ResultFrameTwo(String[] methodNames, String[] evaluationNames, double[][] scores) {
        SwingUtilities.invokeLater(() -> {
            setContentPane(contentPane);
            setTitle("Result");
            setSize(3000, 1000);
            setLocationRelativeTo(null);
            //setResizable(false);
            setBackground(new Color(83, 88, 76));

            JTable tempTable = new JTable(evaluationNames.length + 1, methodNames.length + 1);
            // set 0/0 cell to "Evaluation Method"
            tempTable.setValueAt("Evaluation/Method", 0, 0);
            for (int i = 0; i < scores.length; i++) {
                tempTable.setValueAt(methodNames[i], 0, i + 1);
            }
            for (int i = 0; i < scores[0].length; i++) {
                tempTable.setValueAt(evaluationNames[i], i + 1, 0);
            }

            for (int i = 0; i < scores.length; i++) {
                for (int j = 0; j < scores[0].length; j++) {
                    tempTable.setValueAt(scores[i][j], j + 1, i + 1);
                }
            }
            this.table1.setModel(tempTable.getModel());
            makeTableUneditable();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            this.table1.repaint();
            setVisible(true);
            pack();
        });
    }

    private void makeTableUneditable(){
        // 편집 비활성화
        this.table1.setDefaultEditor(Object.class, null);

        // Enable cell selection (default is row selection)
        this.table1.setCellSelectionEnabled(true);

        // Disable row selection
        this.table1.setRowSelectionAllowed(false);

        // 열 이동 비활성화
        this.table1.getTableHeader().setReorderingAllowed(false);
    }

    private void createUIComponents() {
        // size = scores.length + 1 * scores[0].length + 1
        // set column as method names
        // set row as evaluation names
        // set scores


    }
}
