package easyLearning.view.GUI;

import com.holub.database.Database;
import easyLearning.controller.DropColumnListener;
import easyLearning.controller.ImportListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;

import static easyLearning.view.GUI.FileTypeFilter.createTableFromCSV;


public class UserSelectFrame extends JFrame{

    private JPanel MainPanel;
    private JButton importButton;
    private JTextArea pleaseInsertFileFormatTextArea;
    private JTable table1;
    private JButton dropNANButton;
    private JButton dropColumnButton;
    private JButton submitButton;
    private JScrollPane scrollPane1;
    private JComboBox DMComboBox;

    private File file;

    private Database database;

    public UserSelectFrame() {
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

        importButton.addActionListener(new ImportListener(this));
        dropColumnButton.addActionListener(new DropColumnListener(this, table1));
        dropNANButton.addActionListener(e -> {
            try {
                database.dropNaN(file);
                String tableName = file.getName();
                String filePath = Paths.get(file.getAbsolutePath()).getParent().toString();

                loadCSVIntoTable(new File( "NANDropped_" + file.getName()));
                updateTable();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "잘못된 접근입니다");
            }
        });
        submitButton.addActionListener(e -> {
            try {
                new ResultFrame(file);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "잘못된 접근입니다");
            }

        });
    }

    public void setPleaseInsertFileFormatTextArea(String text){
        this.pleaseInsertFileFormatTextArea.setText(text);
    }


    public void setTable(JTable table){
        this.table1.setModel(table.getModel());
        makeTableUneditable();
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        updateTable();
    }

    private void loadCSVIntoTable(File csvFile) {
        try {
            // Read CSV file and create a DefaultTableModel
            DefaultTableModel model = createTableFromCSV(String.valueOf(csvFile));
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

        // Enable cell selection (default is row selection)
        this.table1.setCellSelectionEnabled(true);

        // Disable row selection
        this.table1.setRowSelectionAllowed(false);

        // 열 이동 비활성화
        this.table1.getTableHeader().setReorderingAllowed(false);
    }

    private void setFile(File file){
        this.file = file;
    }



    public void updateTable() {
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

    public void loadFile(String sourceFilePath) throws IOException {

        // 현재 작업 중인 프로젝트 폴더의 경로
        String projectFolderPath = System.getProperty("user.dir");

        // 복사 대상 파일의 이름
        String fileName = new File(sourceFilePath).getName();

        // 복사 대상 파일의 새로운 경로
        String destinationFilePath = projectFolderPath + File.separator + fileName;

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(sourceFilePath);
            fos = new FileOutputStream(destinationFilePath);
            System.out.println(sourceFilePath);
            System.out.println(destinationFilePath);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            System.out.println("파일이 성공적으로 복사되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.err.println("파일 복사 중 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }
}
