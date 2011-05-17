package aTest;
import java.sql.*;
import java.util.*;

public class GetExtractedWeibo {
	static GetWeiboFromDatabase wb=new GetWeiboFromDatabase();
	static DatabaseOperation dbo=new DatabaseOperation();
	public static void extractTo(String sourceDatabase,String destDatabase){
		Set<Long> textIDSet=new HashSet<Long>();
		String sqlQuery="select top 13989382 [userID],[textID],[created_at],[text] from text2";
		String sqlInsert="Insert into text(userID,textID,created_at,text) values(?,?,?,?)";
		try{
			Connection conSource=dbo.getWeiboConnection(sourceDatabase);
			Connection conDest=dbo.getWeiboConnection("DistinctiveSinaWeibo1");
			Statement stSource=conSource.createStatement();
			PreparedStatement stDest=conDest.prepareStatement(sqlInsert);
			long time1=System.currentTimeMillis();
			ResultSet rsSource=stSource.executeQuery(sqlQuery);
			long time2=System.currentTimeMillis();
			System.out.println("query time:"+(time2-time1));
			int counter=0;
			while(rsSource.next()){
				long tempTextID=rsSource.getLong("textID");
				if(!textIDSet.contains(tempTextID)){//if there is no duplicate
					textIDSet.add(tempTextID);
					/*long tempUserID=rsSource.getLong("userID");
					stDest.setLong(1, tempUserID);
					stDest.setLong(2, tempTextID);
					String tempTime=rsSource.getString("created_at");
					stDest.setString(3, tempTime);
					String tempText=rsSource.getString("text");
					stDest.setString(4,tempText);*/
					counter++;
					stDest.setLong(1, rsSource.getLong("userID"));
					stDest.setLong(2, tempTextID);
					stDest.setString(3, rsSource.getString("created_at"));
					stDest.setString(4,rsSource.getString("text"));
//					if((counter%1000)==0)
//						System.out.println(counter+" weibo has been inserted");
					stDest.addBatch();
					if((counter%1000)==0)
					{
						stDest.executeBatch();
						stDest.clearBatch();
//						counter=0;
						Runtime runtime=Runtime.getRuntime();
						System.out.println(counter+" weibo has been inserted."+" memory left:"+runtime.freeMemory()+"/"+runtime.maxMemory());
					}
//					stDest.execute();
					
				}
			}
			stDest.executeBatch();
			long time3=System.currentTimeMillis();
			System.out.println("insert time:"+(time3-time2));
			stSource.close();
			stDest.close();
			conSource.close();
			conDest.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args){
		extractTo("SinaWeibo2","DistinctiveSinaWeibo1");
	}
	
}
