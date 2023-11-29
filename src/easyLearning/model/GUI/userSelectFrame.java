package easyLearning.model.GUI;

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
    private JButton dropNANButton;
    private JButton dropColumnButton;
    private JButton submitButton;
    private JComboBox comboBox1;
    private JCheckBox checkBox1;
    private JScrollPane scrollPane1;

    private File file;

    public userSelectFrame() {
        SwingUtilities.invokeLater(() -> {
                    setContentPane(MainPanel);

                    setTitle("미리보는 AI체험기");
                    setSize(900, 1000);
                    setLocationRelativeTo(null);
                    //setResizable(false);
                    setBackground(new Color(83, 88, 76));

                    pack();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(true);
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
    }

    private void loadCSVIntoTable(File csvFile) {
        try {
            // Read CSV file and create a DefaultTableModel
            DefaultTableModel model = createTableFromCSV(csvFile.getAbsolutePath());
            this.table1.setModel(model);
            this.table1.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading CSV into table: " + e.getMessage());
        }
    }
}
