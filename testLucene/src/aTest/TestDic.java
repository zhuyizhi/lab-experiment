package aTest;
import java.util.Vector;

import com.aliasi.dict.*;
import com.aliasi.spell.TfIdfDistance;
import com.aliasi.tokenizer.CharacterTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import java.sql.*;
public class TestDic {
	static GetWeiboFromDatabase wb=new GetWeiboFromDatabase();
	static DatabaseOperation dbo=new DatabaseOperation();
	public static void testTfIdfDistance()throws Exception{
//		Vector<String> vecWeibo=wb.getAllWeibo();
		long time1=System.currentTimeMillis();
//		Vector<String> vecWeibo=wb.getWeiboTextWithoutURL(150000);
		Vector<String> vecWeibo=wb.getAllWeiboWithoutURL("SinaWeibo");
		long time2=System.currentTimeMillis();
		System.out.println("get weibo from database. time consumption:"+(time2-time1));
		System.out.println("get "+vecWeibo.size()+" weibo");
		TokenizerFactory chineseTokenizer=CharacterTokenizerFactory.INSTANCE;
		TokenizerFactory trueChineseTokenizer=new StandardBgramTokenizerFactory();
		TfIdfDistance tfIdf = new TfIdfDistance(trueChineseTokenizer);
		int counter=0;
		for(String str:vecWeibo)
		{
			tfIdf.handle(str);
			System.out.println("number of weibo that been handled: "+(++counter));
		}
		long time3=System.currentTimeMillis();
		System.out.println("tfIdf distance counted:"+(time3-time2));
		Connection con=dbo.getWordMapConnection();
//		String sql="insert into wordList(word,df) values(?,?)";
		PreparedStatement pst=con.prepareStatement("insert into wordListLarge(word,df) values(?,?)");
		int counter2=0,times=0;
		for(String str:tfIdf.termSet())
		{
			pst.setString(1, str);
			pst.setInt(2, tfIdf.docFrequency(str));
//			pst.addBatch();
			pst.execute();
			counter2++;
			if((counter2%100)==0)
				System.out.println("executed :"+counter2);
//			pst.executeUpdate();
//			counter2++;
//			if(counter2>5000)
//			{
//				counter2=0;
//				pst.executeBatch();
//				System.out.println("execute "+(++times));
//			}
		}
		pst.executeBatch();
		long time4=System.currentTimeMillis();
		System.out.println("insertion completed:"+(time4-time3));
	}
	public static void main(String[]args)throws Exception{
		testTfIdfDistance();
	}
}
