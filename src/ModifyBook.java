/**
 * Created by Administrator on 2017/6/23.
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.mysql.jdbc.PreparedStatement;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;


public class ModifyBook extends JFrame {

    private JPanel contentPane;
    private JTextField id_textField;
    private JTextField name_textField;
    private JTextField age_textField;
    private JTextField school_textField;
    private Student stu;
    private DataBase dBase;
    private Connection connection;
    private java.sql.PreparedStatement ps;
    private JComboBox comboBox;
    /**
     * Create the frame.
     */
    public ModifyBook(final Student stu) {
        dBase=new DataBase();
        connection=dBase.getCon();
        this.stu=stu;
        setResizable(false);
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 185, 275);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label_2 = new JLabel("\u4FE1\u606F\u4FEE\u6539");
        //label_2.setFont(new Font("楷体", Font.PLAIN, 23));
        label_2.setBounds(35, 10, 92, 32);
        contentPane.add(label_2);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u4FE1\u606F\u4FEE\u6539", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GREEN));
        panel.setBounds(10, 52, 159, 186);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("\u5B66\u53F7\uFF1A");
        label.setBounds(10, 21, 52, 15);
        panel.add(label);

        JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
        label_1.setBounds(10, 50, 52, 15);
        panel.add(label_1);

        JLabel label_3 = new JLabel("\u6027\u522B\uFF1A");
        label_3.setBounds(10, 75, 52, 15);
        panel.add(label_3);

        JLabel label_4 = new JLabel("\u5E74\u9F84\uFF1A");
        label_4.setBounds(10, 100, 52, 15);
        panel.add(label_4);

        JLabel label_5 = new JLabel("\u5B66\u9662\uFF1A");
        label_5.setBounds(10, 125, 52, 15);
        panel.add(label_5);

        id_textField = new JTextField(stu.getStuID());
        id_textField.setBounds(72, 18, 66, 21);
        id_textField.setEditable(false);
        panel.add(id_textField);
        id_textField.setColumns(10);

        name_textField = new JTextField(stu.getStuName());
        name_textField.setColumns(10);
        name_textField.setBounds(72, 47, 66, 21);
        panel.add(name_textField);

        age_textField = new JTextField(stu.getStuAge()+"");
        age_textField.setColumns(10);
        age_textField.setBounds(72, 97, 66, 21);
        panel.add(age_textField);

        school_textField = new JTextField(stu.getStuSchool());
        school_textField.setColumns(10);
        school_textField.setBounds(72, 122, 66, 21);
        panel.add(school_textField);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7537", "\u5973"}));
        if(stu.getStuSex().equals("女")){
            comboBox.setSelectedIndex(1);
        }
        comboBox.setBounds(72, 72, 66, 21);
        panel.add(comboBox);

        JButton button = new JButton("\u786E\u5B9A");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkdatas()){
                    try {
                        System.out.println(stu.getStuID()+stu.getStuName()+stu.getStuSex()+stu.getStuSchool());
                        ps=connection.prepareStatement("update student set stuName=? ,stuAge=?, stuSex=? , stuSchool=? where stuId=?");
                        ps.setString(1, name_textField.getText());
                        ps.setString(2, age_textField.getText());
                        ps.setString(3, comboBox.getSelectedItem().toString());
                        ps.setString(4, school_textField.getText());
                        ps.setString(5, id_textField.getText());
                        connection.setAutoCommit(false);
                        ps.executeUpdate();
                        connection.commit();
                        JOptionPane.showMessageDialog(null, "信息更新成功！");
                        setVisible(false);
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "信息修改时出现错误！");
                        try {
                            connection.rollback();
                        } catch (SQLException e2) {
                            JOptionPane.showMessageDialog(null, "信息修改时出现错误！");
                            e2.printStackTrace();
                        }
                        e1.printStackTrace();
                    }

                }}
        });
        button.setBounds(55, 153, 67, 23);
        panel.add(button);
    }
    private boolean checkdatas(){
        //该方法用来检查进行数据添加的时候是否各个输入都是合法的
        boolean result=true;
        //	String stuId=id_textField.getText();
        String stuName=name_textField.getText();
        String stuAge=age_textField.getText();
        String stuSchool=school_textField.getText();
        if(stuName==null||stuName.equals("")){
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
