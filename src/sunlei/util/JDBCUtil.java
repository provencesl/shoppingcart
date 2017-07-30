package sunlei.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBCUtil {
	static String driver="com.mysql.jdbc.Driver";
	static String url="jdbc:mysql://localhost:3306/shoppingcat?useUnicode=true&characterEncoding=utf8";
	static String user="root";
	static String password="656780sl";
	
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		Connection con=null;
		try {
			con=DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void close(Connection con,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
