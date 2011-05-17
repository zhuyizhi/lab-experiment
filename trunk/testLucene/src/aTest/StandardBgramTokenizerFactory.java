package aTest;
import com.aliasi.tokenizer.*;
import com.aliasi.tokenizer.Tokenizer;

import java.io.StringReader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.cn.smart.*;
import org.apache.lucene.util.Version;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
public class StandardBgramTokenizerFactory implements TokenizerFactory {
	public Tokenizer tokenizer(char[] ch ,int start, int length ){
		return new StandardBgramTokenizer(ch, start, length);
	}
	class StandardBgramTokenizer extends Tokenizer{
//		private WhitespaceAnalyzer analyzer=null;
		private SmartChineseAnalyzer analyzer=null;
		private TokenStream stream;
		private TermAttribute ta=null;
		@SuppressWarnings("deprecation")
		public StandardBgramTokenizer(char[] ch,int offset,int length){
//			analyzer=new WhitespaceAnalyzer();
			analyzer=new SmartChineseAnalyzer();
			String text=new String(ch);
			if((offset!=0)||(length!=ch.length)){
				text=text.substring(offset, length);
			}
			stream=analyzer.tokenStream("sentence",new StringReader(text));
			ta=(TermAttribute) stream.getAttribute(TermAttribute.class);
		}
		public String nextToken(){
//			Token token=stream.next();
			try{
				if(stream.incrementToken())
					return ta.term();
//				else return null;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	}
}