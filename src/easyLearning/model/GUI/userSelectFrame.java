package easyLearning.model.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

    public userSelectFrame() {
        setContentPane(MainPanel);

        setTitle("미리보는 AI체험기");
        setSize(900, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(83,88,76));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        importButton.addActionListener(new Listener());

    }

    class Listener implements ActionListener {
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
                    pleaseInsertFileFormatTextArea.setText(s);
                    if (br != null)
                        br.close();
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        }
    }
}
