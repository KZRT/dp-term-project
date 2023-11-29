package easyLearning.model.GUI;

import com.holub.database.Database;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame{
    private JTable table1;
    private JButton exportButton;
    private JPanel ResultPanel;

    public ResultFrame(){
        SwingUtilities.invokeLater(() -> {
            setContentPane(ResultPanel);

            setTitle("Result");
            setSize(3000, 1000);
            setLocationRelativeTo(null);
            //setResizable(false);
            setBackground(new Color(83, 88, 76));

            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);

            pack();
        });
    }
}
