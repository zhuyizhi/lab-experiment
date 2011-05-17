package aTest;
//http://blog.sina.com.cn/s/blog_5623b16b0100gyl4.html
import com.aliasi.matrix.SparseFloatVector;
import com.aliasi.matrix.Vector;
import com.aliasi.symbol.MapSymbolTable;
import com.aliasi.symbol.SymbolTable;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.tokenizer.TokenFeatureExtractor;
import java.util.HashMap;
import com.aliasi.util.Counter;
import java.util.Map;

public class ExtractFeatures {
	public static Vector[] featureVectors(String[] texts,SymbolTable symbolTable) {
		Vector[] vectors = new Vector[texts.length];
		TokenizerFactory tokenizerFactory = new IndoEuropeanTokenizerFactory();
		TokenFeatureExtractor featureExtractor = new TokenFeatureExtractor(tokenizerFactory);
		for (int i = 0; i < texts.length; ++i) {
			Map<String,Counter> featureMap = featureExtractor.features(texts[i]);
			vectors[i] = toVectorAddSymbols(featureMap, symbolTable,Integer.MAX_VALUE);
		}
		return vectors;
	}

	public static SparseFloatVector toVectorAddSymbols(Map<String,Counter> featureVector, SymbolTable table,int numDimensions) {
		int size = (featureVector.size() * 3) / 2;
		Map<Integer,Number> vectorMap = new HashMap(size);
		for (Map.Entry<String,Counter> entry : featureVector.entrySet()) {
			String feature = entry.getKey();
			Number val = entry.getValue();
			int id = table.getOrAddSymbol(feature);
			vectorMap.put(new Integer(id), val);
		}
		return new SparseFloatVector(vectorMap, numDimensions);
	}

	public static void main(String[] args) {
		args = new String[]{"this is a book a", "go to school"," this is school"};
		SymbolTable symbolTable = new MapSymbolTable();
		Vector[] vectors = featureVectors(args, symbolTable);
		System.out.println("VECTORS");
		for (int i = 0; i < vectors.length; ++i)
			System.out.println(i + ") " + vectors[i]);
		System.out.println(" SYMBOL TABLE");
		System.out.println(symbolTable);
	}
}
