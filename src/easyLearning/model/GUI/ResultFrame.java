package easyLearning.model.GUI;

import com.holub.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;

import static easyLearning.model.GUI.FileTypeFilter.createTableFromCSV;

public class ResultFrame extends JFrame{
    private JTable table1;
    private JButton exportButton;
    private JPanel ResultPanel;
    private javax.swing.JScrollPane JScrollPane;
    private JLabel FileName;

    private File resultFile;

    public ResultFrame(File file){
        SwingUtilities.invokeLater(() -> {
            setContentPane(ResultPanel);

            setTitle("Result");
            setSize(3000, 1000);
            setLocationRelativeTo(null);
            //setResizable(false);
            setBackground(new Color(83, 88, 76));
            this.resultFile = new File("Result_" + file.getName());

            loadCSVIntoTable(this.resultFile);

            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);

            pack();
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File("c:\\"));
                fs.setDialogTitle("Save a File");
                fs.setFileFilter(new FileTypeFilter("folder", "폴더이름"));
                int result = fs.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File file = fs.getSelectedFile();
                    try
                    {
                        //SaveFile(file.getAbsolutePath());
                        fileSave(file, file.getAbsolutePath(), file.getName());
                        //SaveFile(file.getAbsolutePath());
                    }
                    catch (Exception exception)
                    {
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
            makeTableUneditable();
            JScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            updateTable();
            pack();
            FileName.setText(this.resultFile.getName());
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



    //파일 복사 프로그램
    public void fileSave(File file,String path,String name)
    {
        try
        {
            File f= new File(path); // 디렉토리의 정보
            if(!f.exists()) // 폴더가 존재 하지 않는다면
            {
                f.mkdir(); // upload폴더 생성
            }

            //String filePathFolder = Paths.get(path).getParent().toString();



            //파일 복사
            String filePath = path+"\\"+resultFile.getName()+".csv";

            //파일 읽기
            FileInputStream fis = new FileInputStream(resultFile);
            //파일 쓰기
            FileOutputStream fos = new FileOutputStream(filePath);

            int i=0; // 파일 읽은 바이트 수를 체크하기 위한 변수
            byte[] buffer=new byte[1024];


            while((i=fis.read(buffer,0,1024))!=-1)//-1 = EOF(End of File)
            {
                fos.write(buffer, 0, i); // 읽은 개수만큼 출력
            }

            fis.close();
            fos.close();

        }catch(Exception ex){}

    }


    public void SaveFile(String targetPath) throws IOException {

        // 복사 대상 파일의 이름
        String fileName = resultFile.getName();

        String filePathFolder = Paths.get(targetPath).getParent().toString();

        // 복사 대상 파일의 새로운 경로
        String destinationFilePath = filePathFolder + File.separator + fileName;


        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(resultFile);
            fos = new FileOutputStream(destinationFilePath);

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
