package easyLearning.model.GUI;

import com.holub.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static easyLearning.model.GUI.FileTypeFilter.createTableFromCSV;


public class userSelectFrame extends JFrame{

    private JPanel MainPanel;
    private JButton importButton;
    private JTextArea pleaseInsertFileFormatTextArea;
    private JTable table1;
    private JButton dropColumnButton;
    private JButton dropNANButton;
    private JButton submitButton;
    private JScrollPane scrollPane1;
    private JComboBox DMComboBox;

    private File file;

    private Database database;

    public userSelectFrame() {
        SwingUtilities.invokeLater(() -> {
                    setContentPane(MainPanel);

                    setTitle("머신러닝 전, 미리 빠르게 확인해보자!");
                    setSize(3000, 1000);
                    setLocationRelativeTo(null);
                    //setResizable(false);
                    setBackground(new Color(83, 88, 76));

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(true);
                    this.database = new Database();
                    populateDropdown();
                    pack();
                });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File("c:\\"));
                fs.setDialogTitle("Open a File");
                fs.setFileFilter(new FileTypeFilter(".csv", "CSV File"));
                int result = fs.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fs.getSelectedFile();
                    try {
                        pleaseInsertFileFormatTextArea.setText(file.getAbsolutePath());
                        loadCSVIntoTable(file);

                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }

            }
        });
        dropColumnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    database.dropNaN(file);
                    updateTable();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResultFrame();
            }
        });
    }

    private void loadCSVIntoTable(File csvFile) {
        try {
            // Read CSV file and create a DefaultTableModel
            DefaultTableModel model = createTableFromCSV(csvFile.getAbsolutePath());
            this.table1.setModel(model);
            makeTableUneditable();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            updateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
        }
    }

    private void makeTableUneditable(){
        // 편집 비활성화
        this.table1.setDefaultEditor(Object.class, null);

        // 셀 선택 비활성화
        this.table1.setCellSelectionEnabled(false);

        // 열 이동 비활성화
        this.table1.getTableHeader().setReorderingAllowed(false);
    }



    private void updateTable() {
        this.table1.repaint();
        pack();
    }

    private void populateDropdown() {
        // 텍스트 파일을 읽어와서 드롭다운 버튼에 추가
        String filePath = "src/easyLearning/model/parsing/DistanceMeasurement.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DMComboBox.addItem(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
