package easyLearning.controller;

import easyLearning.model.GUI.FileTypeFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GUI {
    private JPanel panel1;
    private JButton importButton;
    private JTextArea pleaseInsertFileFormatTextArea;
    private JTable table1;
    private JButton dropNANButton;
    private JButton dropColumnButton;
    private JButton submitButton;
    private JComboBox comboBox1;
    private JCheckBox checkBox1;

    public GUI() {
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File("c:\\"));
                fs.setDialogTitle("Open a File");
                fs.setFileFilter(new FileTypeFilter(".txt", "Text File"));
                fs.setFileFilter(new FileTypeFilter(".dat", "Data File"));
                fs.setFileFilter(new FileTypeFilter(".hwp", "한글 파일"));
                int result = fs.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File file = fs.getSelectedFile();
                    try
                    {
                        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                        String line = "";
                        String s = "";
                        while((line = br.readLine()) != null)
                        {
                            s += line + "\n";
                        }
                        textArea1.setText(s);
                        if (br != null)
                            br.close();
                    }
                    catch (Exception exception)
                    {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
            }
        });
    }
}
