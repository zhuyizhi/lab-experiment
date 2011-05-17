package aTest;
import java.sql.*;
public class DatabaseOperation {
	static String userName="sa";
	static String password="69015870";
	static String conURLBase="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=";
	
	/**
	 * 
	 * @return return a connection to SinaWeibo if no Exception occurs, 
	 * otherwise return null
	 */
	public static Connection getSinaWeiboConnection(){
		java.sql.Connection  con = null;
	    String conURL="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=SinaWeiBo;";
		try{
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		    con=java.sql.DriverManager.getConnection(conURL,userName,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 
	 * @param databaseName name of the database
	 * @return connection to the database
	 */
	public static Connection getWeiboConnection(String databaseName){
		java.sql.Connection con=null;
		String conURL=conURLBase+databaseName;
		try{
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		    con=java.sql.DriverManager.getConnection(conURL,userName,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 
	 * @return return a connection to wordMap if no Exception occurs,
	 * otherwise return null 
	 */
	public static Connection getWordMapConnection(){
		Connection con=null;
		String conURL=conURLBase+"wordMap;";
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=java.sql.DriverManager.getConnection(conURL,userName,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
}
