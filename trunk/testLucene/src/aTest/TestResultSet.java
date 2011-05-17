package aTest;
import java.sql.*;
import java.util.*;
import java.lang.Runtime;
class weiboInstance{
	long textID;
	long userID;
	String created_at;
	String text;
	weiboInstance(long textid,long userid,String createdAt,String text){
		this.textID=textid;
		this.userID=userid;
		this.created_at=createdAt;
		this.text=text;
	}
}
public class TestResultSet {
	static GetWeiboFromDatabase wb=new GetWeiboFromDatabase();
	static DatabaseOperation dbo=new DatabaseOperation();

	public static void test(){
		String sqlQuery="select top 13989382 [userID],[textID],[created_at],[text] from text2";
		String sqlQuery2="select count(*) from text2";
		String sqlInsert="Insert into text(userID,textID,created_at,text) values(?,?,?,?)";
		Set<weiboInstance> weiboSet=new HashSet<weiboInstance>();
		Set<Long> textIDSet=new HashSet<Long>();
		try{
			Connection con=dbo.getWeiboConnection("SinaWeibo2");
			Statement st=con.createStatement();
			long time1=System.currentTimeMillis();
			ResultSet rs=st.executeQuery(sqlQuery);
//			ResultSet rs=st.executeQuery(sqlQuery2);
			long time2=System.currentTimeMillis();
			System.out.println("query end:"+(time2-time1));
			int counter=0;
			while(rs.next())
			{
				counter++;
				long tempTextID=rs.getLong("textID");
				if(!textIDSet.contains(tempTextID))
				{
					textIDSet.add(tempTextID);
					long tempUserID=rs.getLong("userID");
					String tempTime=rs.getString("created_at");
					String tempText=rs.getString("text");
					weiboInstance tempWeibo=new weiboInstance(tempTextID,tempUserID,tempTime,tempText);
					weiboSet.add(tempWeibo);
//					System.out.println("total number"+rs.getInt(1));
					if((counter%1000)==0)
					{
						Runtime runtime=Runtime.getRuntime();
						System.out.println(counter+" weibo has been read."+" memory left:"+runtime.freeMemory()+"/"+runtime.maxMemory());
					}
				}	
			}
			long time3=System.currentTimeMillis();
			System.out.println("query time:"+(time2-time1)+" operate time:"+(time3-time2));
			
			rs.close();
			st.close();
			con.close();
			
//			Connection conDest=dbo.getWeiboConnection("DistinctiveSinaWeibo1");
//			PreparedStatement stDest=conDest.prepareStatement(sqlInsert);
			
			for(weiboInstance wbInstance:weiboSet){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[]args){
		test();
	}
}
