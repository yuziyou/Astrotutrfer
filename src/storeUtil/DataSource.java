package storeUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author zhang_xz
 * 创建数据库连接
 */
public class DataSource {

	
	
	
	private static String DRIVER = "com.mysql.jdbc.Driver";
    private String URL = "jdbc:mysql://127.0.0.1:3306/mysql";
    private String USER = "root";
    private String PASSWORD = "xjtuse";
    private Connection conn;
    Statement stmt=null;
	ResultSet rs=null;
    /**
     * 加载jar
     */
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 创建连接
     * @return
     */
    public Connection getConnection() {
        conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("success connect to db !");
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
   public void update(String contentUrl)
	{
		try
		{
			String isCrawled="t";
			String strSql="update block_hslt_shanxi set isCrawled='"+isCrawled+"' where contentUrl="+"'"+contentUrl+"'";
			System.out.println(strSql);
			Statement stmt2=conn.createStatement();
			stmt2.executeUpdate(strSql);	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

    /**
     * 关闭连接
     */
    public void close() {
        try {
            conn.close();
            conn=null;
//            System.out.println("success close to db !");
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 设置用户名
     * @param USER
     */
    public void setUSER(String USER) {
        this.USER = USER;
    }

    /**
     * 设置密码
     * @param PASSWORD
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    
}
