package aTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//import java.util.Vector;

import com.aliasi.cluster.*;

import com.aliasi.matrix.SparseFloatVector;
import com.aliasi.matrix.Vector;
import com.aliasi.spell.TfIdfDistance;
import com.aliasi.symbol.MapSymbolTable;
import com.aliasi.symbol.SymbolTable;
import com.aliasi.tokenizer.CharacterTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenFeatureExtractor;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Counter;

import java.io.*;

public class TestMyFeatureExtractor {
	public static Vector[] featureVectors(String[] texts,SymbolTable symbolTable) {
		Vector[] vectors = new Vector[texts.length];
		TokenizerFactory tokenizerFactory = new StandardBgramTokenizerFactory();
		TfIdfDistance tfIdfDistance=new TfIdfDistance(tokenizerFactory);
		for(String str:texts)
		{
			tfIdfDistance.handle(str);
		}
		MyFeatureExtractor featureExtractor = new MyFeatureExtractor(tokenizerFactory,tfIdfDistance);
		for (int i = 0; i < texts.length; ++i) {
			Map<String,Double> featureMap = featureExtractor.features(texts[i]);
			vectors[i] = toVectorAddSymbols(featureMap, symbolTable,Integer.MAX_VALUE);
		}
		return vectors;
	}

	public static SparseFloatVector toVectorAddSymbols(Map<String,Double> featureVector, SymbolTable table,int numDimensions) {
		int size = (featureVector.size() * 3) / 2;
		Map<Integer,Number> vectorMap = new HashMap<Integer,Number>(size);
		for (Map.Entry<String,Double> entry : featureVector.entrySet()) {
			String feature = entry.getKey();
			Number val = entry.getValue();
			int id = table.getOrAddSymbol(feature);
			vectorMap.put(new Integer(id), val);
		}
		return new SparseFloatVector(vectorMap, numDimensions);
	}
	
	public static void testCluster()throws Exception{
//		SymbolTable symbolTable = new MapSymbolTable();
		java.util.Vector<String> weibos=new java.util.Vector<String>();
		weibos=GetWeiboFromDatabase.getWeiboTextWithoutURL(1000,"SinaWeibo");
//		weibos=GetWeiboFromDatabase.getAllWeiboWithoutURL();
		TokenizerFactory tokenizerFactory = new StandardBgramTokenizerFactory();
		TokenizerFactory idFactory=CharacterTokenizerFactory.INSTANCE;
		TfIdfDistance tfIdfDistance=new TfIdfDistance(tokenizerFactory);
		HashSet<String> strSet=new HashSet<String>();
		for(String str:weibos)
		{
			tfIdfDistance.handle(str);
			strSet.add(str);
		}
		System.out.println("tfIdf is done");
		MyFeatureExtractor featureExtractor = new MyFeatureExtractor(tokenizerFactory,tfIdfDistance);
		TokenFeatureExtractor tfe=new TokenFeatureExtractor(idFactory);
//		KMeansClusterer<String> kMeans=new KMeansClusterer(featureExtractor,6,0,true,0.0);
		KMeansClusterer<String> kMeans=new KMeansClusterer(tfe,5,0,true,0.0);
		System.out.println("kMeans clusterer is built");
		Set<Set<String>> strSets= kMeans.cluster(strSet);
		
		System.out.println("clustered "+strSets.size());
		FileWriter fw=new FileWriter(new File("output.txt"));
		BufferedWriter bw=new BufferedWriter(fw);
		int counter=0;
		for(Set<String> strSetElement:strSets){
			System.out.println("cluster"+(counter++));
			bw.write("cluster"+(counter)+"\n");
			bw.newLine();
			System.out.println("--------> size:"+strSetElement.size());
			bw.write("-------->"+"\n");
//			bw.write();
			bw.newLine();
			for(String str:strSetElement)
			{
//				System.out.println("                "+str);
				bw.write("                "+str+"\n");
				bw.newLine();
			}
		}
	}

	public static void main(String[] args)throws Exception {
//		args = new String[]{"this is a book a", "go to school"," this is school"};
/*		String str1="洛杉矶湖人队客场98-80击败新奥尔良黄蜂，4-2淘汰对手成为西部第二支晋级的球队。科比-布莱恩特24分3抢断，克里斯-保罗贡献10分8篮板11助攻。";
		String str2="霍华德25分15篮板，全队仅三人得分上双，魔术客场81-84不敌老鹰，总比分2-4遭老鹰淘汰。乔-约翰逊得到全队最高的23分10篮板，全队5人得分上双，带领老鹰以东部第五的身份淘汰第四的老鹰晋级东部半决赛。";
		String str3="当你说民主法治重要，他们就说：美国民主法治，但不是也有不公、歧视、不正义？--包括许多留美学生也这样，尤其一些海归。天堂国或许只是人死后才能看到，现实世界永远不会有，人的社会只有“更好”而没有“绝对好”。民主法治提供的只是制度平台，正义是每个人主动捍卫自己权利的结果，不是被动等到的";
		
		args=new String[]{str1,str2,str3};
		SymbolTable symbolTable = new MapSymbolTable();
		Vector[] vectors = featureVectors(args, symbolTable);
		System.out.println("VECTORS");
		for (int i = 0; i < vectors.length; ++i)
			System.out.println(i + ") " + vectors[i]);
		
		System.out.println(" SYMBOL TABLE");
		System.out.println(symbolTable);*/
		testCluster();
	}
}
