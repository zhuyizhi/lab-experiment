package testLingpipe;
//http://tech.dir.groups.yahoo.com/group/LingPipe/message/588
import com.aliasi.spell.TfIdfDistance;
import com.aliasi.tokenizer.CharacterTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.tokenizer.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;
public class TfIdfDistanceDemo {
	static String[] docs={"F:\\testCorp\\1.txt","F:\\testCorp\\2.txt",
		"F:\\testCorp\\4.txt"};
	public static void main(String[] args)throws Exception {
//		TokenizerFactory tokenizerFactory =new IndoEuropeanTokenizerFactory();
		TokenizerFactory chineseTokenizer=CharacterTokenizerFactory.INSTANCE;
		TokenizerFactory chinese2=new StandardBgramTokenizerFactory();
		
		TfIdfDistance tfIdf = new TfIdfDistance(chinese2);
//		TfIdfDistance tfIdf = new TfIdfDistance(chineseTokenizer);
//		String[] documents=new String[3];
		Vector<String> documents=new Vector<String>();
		for (String s : docs)
		{
			Scanner sc=new Scanner(new File(s));
			StringBuffer sb=new StringBuffer();
			while(sc.hasNext())
			{
				sb=sb.append(sc.nextLine());
			}
			String tempDoc=sb.toString();
			documents.add(tempDoc);
			tfIdf.handle(tempDoc);
		}
		tfIdf.handle("这是手动输入的一段文字，为了检验正确性");
		tfIdf.handle("这是另外一段文字，为了检验编码");
			
		System.out.printf("\n %18s %8s %8s\n","Term", "Doc Freq", "IDF");
		for (String term : tfIdf.termSet())
			System.out.printf(" %18s %8d %8.2f\n",term,tfIdf.docFrequency(term),tfIdf.idf(term));
		for (String s1 : documents) {
			for (String s2 : documents) {
				System.out.println("\nString1=" + s1);
				System.out.println("String2=" + s2);
				System.out.printf("distance=%4.2f proximity=%4.2f\n",tfIdf.distance(s1,s2),tfIdf.proximity(s1,s2));
			}
		}
	}
}

/*
  如果要使插件开发应用能有更好的国际化支持，能够最大程度的支持中文输出，则最好使 Java文件使用UTF-8编码。然而，Eclipse工作空间(workspace)的缺省字符编码是操作系统缺省的编码，简体中文操作系统 (Windows XP、Windows 2000简体中文)的缺省编码是GB18030，在此工作空间中建立的工程编码是GB18030，工程中建立的java文件也是GB18030。如果要使新建立工程、java文件直接使UTF-8则需要做以下工作： 
1、windows->Preferences...打开"首选项"对话框，左侧导航树，导航到general->Workspace，右侧Text file encoding，选择Other，改变为UTF-8，以后新建立工程其属性对话框中的Text file encoding即为UTF-8。
 */
