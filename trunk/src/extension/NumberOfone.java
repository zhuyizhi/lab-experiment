package extension;
/**
 * ����1��ʮ��������N�������1��ʼ��N�м�����������1���ֵĸ�����
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
		//���ȴ�1��N����һ�εõ�һ���ַ���
		for(int i=1;i<=n;i++){
			sb.append(i);
		}
		//Ȼ��˳��ɨ������1�ĸ���
		for(int i=0;i<sb.length();i++)
			if(sb.charAt(i)=='1')
				count++;
		System.out.println("���С�1��"+count+"��");
		return count;
	}
	public static int getByEnumeration2(int n){
		int counter=0;
		StringBuilder sb=new StringBuilder();
		//ֱ�Ӵ�1��n����������getOnes�������㵱ǰ������1�ĸ���
		for(int i=1;i<=n;i++){
			counter+=getOnes(i);
		}
		System.out.println("ʹ��getByEnumeration2�������õ����С�1��"+counter+"��");
		return counter;
	}
	//ʹ�����ص�pdf�ĵ��еķ�����ͨ�������ܽ�õ�����
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
		System.out.println("ʹ��getByAnalyze�������õ����С�1��"+counter+"��");
		return counter;
	}
	public static void main(String[]args){
		int n=100000000;
		long timeStart=System.currentTimeMillis();
		getByEnumeration2(n);
		long timeEnd=System.currentTimeMillis();
		System.out.println("��ʱ��"+(timeEnd-timeStart));
		timeStart=System.currentTimeMillis();
		getByAnalyze(n);
		timeEnd=System.currentTimeMillis();
		System.out.println("��ʱ��"+(timeEnd-timeStart));
	}
}
