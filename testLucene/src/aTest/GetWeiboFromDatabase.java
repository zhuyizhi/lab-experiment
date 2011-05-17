package aTest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.util.*;
public class GetWeiboFromDatabase {
	/**
	 * 
	 * @param num: the number of weibo you wish to return
	 */
	public static Vector<String> getWeiboText(int num,String databaseName){
		Vector<String> vecStr=new Vector<String>();
		try{
			Connection con=DatabaseOperation.getWeiboConnection(databaseName);
			Statement st=con.createStatement();
			String sql="select top "+num+" * from text";
			ResultSet rs=st.executeQuery(sql);
			while(rs.next())
			{
				vecStr.add(rs.getString("text"));
			}
			rs.close();
			st.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return vecStr;
		}
	}
	/**
	 * 
	 * @param num: the number of weibo you wish to return
	 * @return weiboTextWithoutURL
	 */
	public static Vector<String> getWeiboTextWithoutURL(int num,String databaseName){
		Vector<String> vecStr=new Vector<String>();
		try{
			Connection con=DatabaseOperation.getWeiboConnection(databaseName);
			Statement st=con.createStatement();
			String sql="select top "+num+" * from text";
			ResultSet rs=st.executeQuery(sql);
			while(rs.next())
			{
				String str=rs.getString("text");
				vecStr.add(WeiboV1.filterURL(str));
			}
			rs.close();
			st.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return vecStr;
		}
	}
	/**
	 * 
	 * @return get all the weibo from database
	 */
	public static Vector<String> getAllWeibo(String databaseName){
		Vector<String> vecStr=new Vector<String>();
		try{
			Connection con=DatabaseOperation.getWeiboConnection(databaseName);
			Statement st=con.createStatement();
			String sql="select * from text";
			long time1=System.currentTimeMillis();
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next())
			{
				vecStr.add(rs.getString("text"));
			}
			long time2=System.currentTimeMillis();
			System.out.println("query finished, cost:"+(time2-time1));
			rs.close();
			st.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return vecStr;
		}
	}
	/**
	 * 
	 * @return
	 */
	public static Vector<String> getAllWeiboWithoutURL(String databaseName){
		Vector<String> vecStr=new Vector<String>();
		try{
			Connection con=DatabaseOperation.getWeiboConnection(databaseName);
			Statement st=con.createStatement();
			String sql="select * from text";
			long time1=System.currentTimeMillis();
			ResultSet rs=st.executeQuery(sql);			
			while(rs.next())
			{
				String str=rs.getString("text");
				vecStr.add(WeiboV1.filterURL(str));
			}
			long time2=System.currentTimeMillis();
//			System.out.println("ִ�гɹ�,ʱ��Ϊ:"+(time2-time1));
			rs.close();
			st.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return vecStr;
		}
	}
	public static void main(String[]args)throws Exception{
//		Vector<String> vecStr=getWeiboText(1000);
//		int num=vecStr.size();
//		for(String str:vecStr)
//		{
//			System.out.println(str);
//		}
//		System.out.println(num);
		Vector<String> vecStr=getAllWeibo("SinaWeibo");
		int num=vecStr.size();
		System.out.println(num);
	}
}