package aTest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class WeiboV1 {
	public static String sinaURL_1="http://sinaurl.cn/";
	public static String sinaURL_2="http://t.cn/";
	public static String reg="("+sinaURL_1+"|"+sinaURL_2+"){1}[\\w*]+";
	public static Pattern pattern=Pattern.compile("("+sinaURL_1+"|"+sinaURL_2+"){1}[\\w*]+");
//	public static Pattern pattern2 = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
	public static boolean hasURL(String str){
		if(str.contains(sinaURL_1)||str.contains(sinaURL_2))
			return true;
		else
			return false;
	}
	public static String getURL(String str){
		Matcher matcher=pattern.matcher(str);
		StringBuffer sb=new StringBuffer();
		int counter=0;
		while(matcher.find())
		{
			sb.append(matcher.group());
			sb.append(" ");
			counter++;
		}
		if(counter==0)
			return "no URL";
		return sb.toString();
	}
	public static String filterURL(String str){
		return str.replaceAll(reg, "");
	}
	public static void testFunction()throws Exception
	{
		Vector<String> vecStr=GetWeiboFromDatabase.getWeiboText(1000,"SinaWeibo");
		Vector<String> vecURL=new Vector<String>();
		int count=0;
		for(String str:vecStr)
			if(hasURL(str))
			{
				count++;
//				if(getURL(str).compareTo("no URL")==0)
				vecURL.add(getURL(str));
//					vecURL.add(str);
			}
		for(String str:vecURL)
			System.out.println(str);
		System.out.println(count+":"+vecURL.size());
	}
	public static void testFunction2()throws Exception{
		String s="������������UC�������΢�����Ƽ���http://sinaurl.cn/hMAnw";
		System.out.println(filterURL(s));
	}
	public static void main(String[] args)throws Exception{
		testFunction2();
	}
}
