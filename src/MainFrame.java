/**
 * Created by Administrator on 2017/6/23.
 */

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField id_textField;
    private JTextField name_textField;
    private JTextField school_textField;
    private JButton add_Button,showAll_button,clear_Button;
    private DefaultTableModel tableModel;
    private DataBase db;
    private Connection con;
    private PreparedStatement ps;
    private static String stuInf[]={"学号","姓名","年龄 ","性别","学校"};
    private JTextField age_textField;
    private JComboBox comboBox;
    private JPopupMenu popMenu;
    private JMenuItem deleteMenuItem;
    private JMenuItem modifyJMenuItem;
    ModifyBook modifyBook;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    /**
     * Create the frame.
     */
    /**
     *
     */
    public MainFrame() {
        db=new DataBase();
        con=db.getCon();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 688, 417);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        popMenu=new JPopupMenu();
        deleteMenuItem=new JMenuItem("删除该条记录");
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row=table.getSelectedRow();
                String stuId=table.getValueAt(row, 0)+"";
                try {
                    ps=con.prepareStatement("delete from student where stuId=?");
                    ps.setString(1, stuId);
                    con.setAutoCommit(false);
                    ps.execute();
                    con.setAutoCommit(true);
                    tableModel.removeRow(row);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "删除时出现了错误!");
                    try {
                        con.rollback();
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "删除时出现了错误!");
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }



            }
        });
        popMenu.add(deleteMenuItem);


        modifyJMenuItem=new JMenuItem("修改该条记录");
        modifyJMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row=table.getSelectedRow();
                String stuId=table.getValueAt(row, 0)+"";
                String stuName=table.getValueAt(row, 1)+"";
                String stuAge=table.getValueAt(row, 2)+"";
                String stuSex=table.getValueAt(row, 3)+"";
                String stuSchool=table.getValueAt(row, 4)+"";
                Student student=new Student(stuId,stuName,Integer.parseInt(stuAge),stuSex,stuSchool);
                modifyBook=new ModifyBook(student);
                modifyBook.setVisible(true);
            }
        });
        popMenu.add(modifyJMenuItem);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GREEN));
        panel.setBounds(20, 22, 642, 54);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel id_label = new JLabel("\u5B66\u53F7:");
        id_label.setBounds(10, 29, 32, 15);
        panel.add(id_label);

        id_textField = new JTextField();
        id_textField.setText("\u8F93\u5165\u5B66\u53F7");
        id_textField.setBounds(52, 26, 54, 21);
        panel.add(id_textField);
        id_textField.setColumns(10);

        JLabel name_lable = new JLabel("\u59D3\u540D:");
        name_lable.setBounds(112, 29, 32, 15);
        panel.add(name_lable);

        name_textField = new JTextField();
        name_textField.setText("\u8F93\u5165\u59D3\u540D");
        name_textField.setColumns(10);
        name_textField.setBounds(142, 26, 66, 21);
        panel.add(name_textField);

        JLabel label = new JLabel("\u6027\u522B:");
        label.setBounds(218, 29, 32, 15);
        panel.add(label);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7537", "\u5973"}));
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(250, 26, 43, 21);
        panel.add(comboBox);

        JLabel school_label = new JLabel("\u5B66\u6821:");
        school_label.setBounds(425, 29, 32, 15);
        panel.add(school_label);

        school_textField = new JTextField();
        school_textField.setText("\u8F93\u5165\u6240\u5728\u5B66\u6821");
        school_textField.setColumns(10);
        school_textField.setBounds(467, 26, 86, 21);
        panel.add(school_textField);

        add_Button = new JButton("\u6DFB\u52A0");
        add_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkDatas()){
                    String stuId=id_textField.getText();
                    String stuName=name_textField.getText();
                    String stuAge=age_textField.getText();
                    String stuSex=comboBox.getSelectedItem().toString();
                    String stuSchool=school_textField.getText();
                    try {

                        ps=con.prepareStatement("select *  from student where stuId=?");
                        ps.setString(1, stuId);
                        ResultSet rs=ps.executeQuery();
                        if(rs.next()){//说明这个Id已经被人加使用了
                            JOptionPane.showMessageDialog(null, "这个ID已经被别人使用，请您换一个ID");
                            rs.close();
                        }
                        else{
                            con.setAutoCommit(false);

                            ps=con.prepareStatement("insert into student values(?,?,?,?,?)");
                            ps.setString(1, stuId);
                            ps.setString(2, stuName);
                            ps.setString(3, stuAge);
                            ps.setString(4, stuSex);
                            ps.setString(5, stuSchool);
                            ps.execute();
                            con.commit();
                            JOptionPane.showMessageDialog(null, "添加成功");

                        }
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "执行添加出现错误！");
                        e1.printStackTrace();
                    }


                }}
        });
        add_Button.setToolTipText("\u6DFB\u52A0\u8BB0\u5F55");
        add_Button.setBounds(563, 25, 69, 23);
        panel.add(add_Button);

        JLabel label_1 = new JLabel("\u5E74\u9F84:");
        label_1.setBounds(303, 29, 32, 15);
        panel.add(label_1);

        age_textField = new JTextField();
        age_textField.setText("\u8F93\u5165\u5E74\u9F84");
        age_textField.setColumns(10);
        age_textField.setBounds(345, 26, 66, 21);
        panel.add(age_textField);

        table = new JTable();
        tableModel =new DefaultTableModel();
        table = new JTable();
        table.setModel(tableModel);
        table.setEnabled(false);
        tableModel.setColumnIdentifiers(stuInf);

        table.addMouseListener(new MouseAdapter() {//添加单击鼠标弹出操作选项
            public void mouseReleased(MouseEvent e){

                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        table.addMouseMotionListener(new MouseMotionListener() {
            //当鼠标在每条记录上游走的时候，记录会被选中
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p=e.getPoint();
                int x=table.rowAtPoint(p);
                table.getSelectionModel().setSelectionInterval(x, x);
                popMenu.setVisible(false);

            }

            @Override
            public void mouseDragged(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 86, 565, 225);
        scrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u663E\u793A", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 255, 0)));
        contentPane.add(scrollPane);
        showAll_button = new JButton("\u663E\u793A\u6240\u6709\u4FE1\u606F");
        showAll_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleardatas();
                try {
                    ps=con.prepareStatement("select * from student");
                    ResultSet rs=ps.executeQuery();
                    boolean flag=false;
                    while(rs.next()){
                        flag=true;
                        String data[]=new String[5];
                        data[0]=rs.getString(1);
                        data[1]=rs.getString(2);
                        data[2]=rs.getString(3);
                        data[3]=rs.getString(4);
                        data[4]=rs.getString(5);
                        tableModel.addRow(data);
                    }
                    if(!flag){
                        JOptionPane.showMessageDialog(null, "查询结果为空！");
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        showAll_button.setToolTipText("\u5355\u51FB\u53EF\u4EE5\u663E\u793A\u6240\u6709\u5B66\u751F\u7684\u4FE1\u606F");
        showAll_button.setBounds(10, 333, 137, 23);
        contentPane.add(showAll_button);

        clear_Button = new JButton("\u6E05\u7A7A\u8868\u683C\u6570\u636E");
        clear_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleardatas();


            }
        });
        clear_Button.setBounds(172, 333, 113, 23);
        contentPane.add(clear_Button);
    }
    private void cleardatas(){//清除表格中所有的数据
        int rows = tableModel.getRowCount();
        if (rows != 0)
            for (int i = 0; i < rows; i++)
                tableModel.removeRow(0);
    }
    private boolean checkDatas(){
        //该方法用来检查进行数据添加的时候是否各个输入都是合法的
        boolean result=true;
        String stuId=id_textField.getText();
        String stuName=name_textField.getText();
        String stuAge=age_textField.getText();
        String stuSchool=school_textField.getText();
        if(stuId==null||stuId.equals("")){
            JOptionPane.showMessageDialog(null, "学号不能为空！");
            id_textField.requestFocus();
            result=false;
        }
        else if(stuName==null||stuName.equals("")){
            JOptionPane.showMessageDialog(null, "姓名不能为空！");
            name_textField.requestFocus();
            result=false;
        }
        else if(stuAge==null||stuAge.equals("")){
            JOptionPane.showMessageDialog(null, "年龄不能为空！");
            age_textField.requestFocus();
            result=false;
        }

        else if(stuSchool==null||stuSchool.equals("")){
            JOptionPane.showMessageDialog(null, "学校不能为空！");
            school_textField.requestFocus();
            result=false;
        }
        else {
            try {
                int age=Integer.parseInt(stuAge);
                if(age<=0||age>=100){
                    JOptionPane.showMessageDialog(null, "输入的年龄必须介于0~100之间！");
                    result=false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "年龄格式出现错误！");
                result=false;
            }
        }
        return result;
    }
}
