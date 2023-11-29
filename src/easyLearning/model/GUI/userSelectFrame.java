package easyLearning.model.GUI;

import com.holub.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private JComboBox comboBox1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox4;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private JCheckBox checkBox9;
    private JCheckBox checkBox10;
    private JCheckBox checkBox11;
    private JCheckBox checkBox12;
    private JCheckBox checkBox5;
    private JCheckBox checkBox3;

    private File file;

    private Database database;

    public userSelectFrame() {
        SwingUtilities.invokeLater(() -> {
                    setContentPane(MainPanel);

                    setTitle("미리보는 AI체험기");
                    setSize(1500, 1000);
                    setLocationRelativeTo(null);
                    //setResizable(false);
                    setBackground(new Color(83, 88, 76));

                    pack();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(true);
                    this.database = new Database();
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
    }

    private void loadCSVIntoTable(File csvFile) {
        try {
            // Read CSV file and create a DefaultTableModel
            DefaultTableModel model = createTableFromCSV(csvFile.getAbsolutePath());
            this.table1.setModel(model);
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            updateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
        }
    }

    private void updateTable() {
        this.table1.repaint();
        pack();
    }
}
