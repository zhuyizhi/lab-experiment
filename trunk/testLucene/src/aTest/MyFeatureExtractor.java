package aTest;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Counter;
import com.aliasi.util.FeatureExtractor;
import com.aliasi.util.ObjectToCounterMap;
import com.aliasi.util.Strings;

import java.util.*;
import java.math.*;
import java.lang.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import com.aliasi.spell.TfIdfDistance;
import com.aliasi.tokenizer.*;
/**
 * 自己实现的用于抽取tfIdf信息的类，没有实现序列化功能。
 * 
 * @author ucai
 *
 */
public class MyFeatureExtractor implements FeatureExtractor<CharSequence> {
	private final TokenizerFactory mTokenizerFactory;
	private final TfIdfDistance tfIdf;
    public MyFeatureExtractor(TokenizerFactory factory,TfIdfDistance tfIdf) {
//    	super(factory);
        mTokenizerFactory = factory;
        this.tfIdf=tfIdf;
    }
   /* public Map<String,Counter> features(CharSequence in) {
        ObjectToCounterMap<String> map = new ObjectToCounterMap<String>();
        char[] cs = Strings.toCharArray(in);
        Tokenizer tokenizer = mTokenizerFactory.tokenizer(cs,0,cs.length);
        for (String token : tokenizer)
            map.increment(token);
        return map;
    }*/
    public Map<String,Double> features(CharSequence in){
    	HashMap<String,Double> map=new HashMap<String,Double>();
    	ObjectToCounterMap<String> otcMap=new ObjectToCounterMap<String>();
    	otcMap=tfIdf.termFrequencyVector(in);
    	for(String token:otcMap.keySet()){
    		double tf=java.lang.Math.sqrt(otcMap.get(token).intValue());
    		double idf=tfIdf.idf(token);
    		double tfIdfScore=tf*idf;
    		map.put(token, tfIdfScore);
    	}
    	return map;
    }

}
