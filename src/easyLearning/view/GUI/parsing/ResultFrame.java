package easyLearning.view.GUI.parsing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

import static easyLearning.view.GUI.FileTypeFilter.createTableFromCSV;

public class ResultFrame extends JFrame{
    private JTable table1;
    private JPanel ResultPanel;
    private JLabel clusteringMethod;
    private JLabel score;
    private JLabel evaluationMethod;
    private JLabel clusterCount;


    public ResultFrame(){
        SwingUtilities.invokeLater(() -> {
            setContentPane(ResultPanel);

            setTitle("Result");
            setSize(3000, 1000);
            setLocationRelativeTo(null);
            //setResizable(false);
            setBackground(new Color(83, 88, 76));
            setVisible(true);
            pack();
        });

    }

    public void setClusteringMethod(String text){
        this.clusteringMethod.setText("Clustering Method: " + text);
        this.clusteringMethod.repaint();
        pack();
    }

    public void setScore(String text){
        this.score.setText("Score: " + text);
        this.score.repaint();
        pack();
    }

    public void setEvaluationMethod(String text){
        this.evaluationMethod.setText("Evaluation Method: " + text);
        this.evaluationMethod.repaint();
        pack();
    }

    public void setClusterCount(String text){
        this.clusterCount.setText("Cluster Count: " + text);
        this.clusterCount.repaint();
        pack();
    }

    private void loadCSVIntoTable(File csvFile) {
        try {
            // Read CSV file and create a DefaultTableModel
            DefaultTableModel model = createTableFromCSV(csvFile.getAbsolutePath());
            this.table1.setModel(model);
            makeTableUneditable();
            updateTable();
            pack();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
        }
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

    private void updateTable() {
        this.table1.repaint();
        pack();
    }


}
