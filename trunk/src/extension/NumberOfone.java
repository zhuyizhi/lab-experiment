package extension;
/**
 * 给定1个十进制整数N，计算从1开始到N中间所有数字中1出现的个数。
 * @author ucai
 *
 */

public class NumberOfone {
	public static int getOnes(int number){
		String str=new Integer(number).toString();
		int counter=0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='1')
				counter++;
		}
		return counter;
	}
	
	public static int getByEnumeration(int n){
		int count=0;
		StringBuilder sb=new StringBuilder();
		//首先从1到N迭代一次得到一个字符串
		for(int i=1;i<=n;i++){
			sb.append(i);
		}
		//然后顺序扫描其中1的个数
		for(int i=0;i<sb.length();i++)
			if(sb.charAt(i)=='1')
				count++;
		System.out.println("共有‘1’"+count+"个");
		return count;
	}
	public static int getByEnumeration2(int n){
		int counter=0;
		StringBuilder sb=new StringBuilder();
		//直接从1到n遍历，调用getOnes函数计算当前数字中1的个数
		for(int i=1;i<=n;i++){
			counter+=getOnes(i);
		}
		System.out.println("使用getByEnumeration2方法，得到共有‘1’"+counter+"个");
		return counter;
	}
	//使用下载的pdf文档中的方法，通过归纳总结得到规律
	public static int getByAnalyze(int n){
		int counter=0;
		int factor=1;
		int lowerNum=0;
		int higherNum=0;
		int currentNum=0;
		while(n/factor!=0){
			lowerNum=n-(n/factor)*factor;
			currentNum=(n/factor)%10;
			higherNum=n/(factor*10);
			switch(currentNum){
			case 0:
				counter+=higherNum*factor;
				break;
			case 1:
				counter+=higherNum*factor+lowerNum+1;
				break;
			default:
				counter+=(higherNum+1)*factor;
				break;
			}
			factor*=10;
		}
		System.out.println("使用getByAnalyze方法，得到共有‘1’"+counter+"个");
		return counter;
	}
	public static void main(String[]args){
		int n=100000000;
		long timeStart=System.currentTimeMillis();
		getByEnumeration2(n);
		long timeEnd=System.currentTimeMillis();
		System.out.println("耗时："+(timeEnd-timeStart));
		timeStart=System.currentTimeMillis();
		getByAnalyze(n);
		timeEnd=System.currentTimeMillis();
		System.out.println("耗时："+(timeEnd-timeStart));
	}
}
