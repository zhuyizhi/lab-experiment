package aTest;
import edu.sjtu.ltlab.word.split.*;
import java.io.*;
import java.util.*;
public class TestWordSplitter {
	static String doc="F:\\工具，程序，语言资源\\testCorp\\5.txt";
	public static void main(String[]args)throws Exception{
		ICTCSplit ictSplitter=new ICTCSplit();
		IRSplit irSplitter=new IRSplit();
		Scanner sc=new Scanner(new File(doc));
		StringBuffer sb=new StringBuffer();
		while(sc.hasNext())
		{
			sb.append(sc.nextLine());
		}
		String temps=sb.toString();
		String res=irSplitter.paragraphProcess(temps);
		System.out.println(res);
//		String[] strs=res.split("  ");
//		for(String s:strs)
//			System.out.println(s);
	}

}
