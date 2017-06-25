/**
 * Created by Administrator on 2017/6/23.
 */
import java.sql.*;
public class DataBase {
    private String dbUrl ="jdbc:mysql://127.0.0.1:3306/fujia";//数据库
    private String dbUser="root";                              //用户名
    private String dbPwd="";                            //密码


    public DataBase() {
        try {
            //加载数据库驱动程序9
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    //获取数据库连接
    public Connection getCon(){
        Connection con = null;
        try {
            //建立数据库连接
            con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            System.out.println("success");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return con;
    }
    //关闭数据库连接
    public void closeConnection(Connection con){
        try{
            if(con!=null) con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String aa[]){
        DataBase db=new DataBase();
        Connection connection=db.getCon();

    }
}


