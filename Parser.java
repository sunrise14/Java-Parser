import java.util.HashMap;
import java.io.IOException;
import java.io.StringReader;
import java.lang.String;

public class Parser {
	private static String token;
	private static StringReader din;
	private static String var;
	private static int number=0;
	private static HashMap<String,Boolean> assignments = new HashMap<String,Boolean>();
	private static HashMap<Integer,Boolean> queries = new HashMap<Integer,Boolean>();
	private enum keyWords{
		LET , QUERY,EQUIVALENCE,VAR, IMPLIES, LPAREN,GT,LT, EQUALS,RPAREN, HYPEN, OR,AND , NEGATION, TRUE, FALSE, SEMICOLON, DOT;

	}
	private static keyWords key;
	public 	static boolean parse(String s) throws IOException{
		boolean eval=false;;
		try{
		int position=0;
		din = new StringReader(s);		
		eval = program(position);
		number=0;
		queries.clear();
		}	
		catch(RuntimeException e){
			System.out.println(e);
		}
		return eval;
	}
	private static boolean program(int position) throws IOException{
		boolean programValue;
		lex();
		while(accept(keyWords.LET)){
			programValue =assignment();
		}
		programValue =query();
		return programValue;		
		
	}
	private static boolean assignment() throws IOException{
		boolean assignValue;
		String varName;
		varName= variable();
		expect(keyWords.EQUALS);	
		assignValue =proposition();
		expect(keyWords.SEMICOLON);
		assignments.put(varName, assignValue);
		return assignValue;
	}
	private static  boolean query() throws IOException{
		boolean queryValue;
		expect(keyWords.QUERY);
		queryValue = proposition();
		expect(keyWords.DOT);
		return queryValue;
	}
	private static boolean proposition() throws IOException{
		boolean propValue1 = false;
		boolean propValue2 = false;
		propValue1 = implication();
		int size =1;
		while(size<= assignments.size() | size<= queries.size()){
		if(accept(keyWords.EQUIVALENCE)){
			propValue2 = implication();
			propValue1 =(((!propValue1) | (propValue2)) & ((!propValue2) | (propValue1)));
			size++;	
			continue;
		}
		break;
		}
		return propValue1;
	
	}
	private static boolean implication() throws IOException{
		boolean impValue1 = false;
		boolean impValue2 = false;
		impValue1 = disjunction();
		int size = 1;
		String s ="";
		while(size<= assignments.size() | size<= queries.size()){		
			if(accept(keyWords.IMPLIES)){
				s ="implication";
				impValue2 = disjunction();
				size++;
				continue;
			}
			break;
		}
		if(queries.size()>1 && s.equals("implication")){
			impValue2 = queries.get(number-1);
			for(int i=number-1;i>0; i--){
				impValue2 = (!queries.get(i-1)) | impValue2;
		    }
			return impValue2;
			
		}
		if(assignments.size()>1 && s.equals("implication")){
			String[] a = new String[assignments.size()];
 			assignments.keySet().toArray(a);
 			impValue2 =assignments.get(a[assignments.size()-1]);
			for(int j= assignments.size()-1; j>=0 ; j--){
				impValue2 = (!assignments.get(a[j-1])) | impValue2 ;
			}
			return impValue2;			
		}			
			
		return impValue1;
	}
	private static boolean disjunction() throws IOException{
		boolean disValue1 = false;
		boolean disValue2 = false;
		disValue1 = conjunction();
		int size = 1;
		while(size<= assignments.size() | size<= queries.size()){
		if(accept(keyWords.OR)){
			disValue2 = conjunction();
			disValue1 = disValue1 | disValue2;
			size++;	
			continue;
		}
		break;
		}
	
		return disValue1;	
		
	}
	private static boolean conjunction() throws IOException{
		boolean conValue1 = false;
		boolean conValue2 = false;
		conValue1 = negation();
		int size = 1;
		while(size<= assignments.size() | size<= queries.size()){
			if(accept(keyWords.AND)){
				conValue2 = negation();	
				conValue1= conValue1 & conValue2;
				size++;	
				continue;
			}
			break;
		}
		
		return conValue1;
	}
	private static boolean negation() throws IOException{
		boolean negValue = false;
		if(accept(keyWords.NEGATION))
			negValue = !expression();
		else
			negValue = expression();
		return negValue;
	}
	private static boolean expression() throws IOException{
		boolean exprValue = false;
		if(accept(keyWords.LPAREN)){
			exprValue = proposition();
			expect(keyWords.RPAREN);
		}
		else 
			exprValue = booleanMethod();
		return exprValue;
	}
	private static boolean booleanMethod() throws IOException{
		boolean boolValue = false;
		
		if((token.compareTo("TRUE"))==0 | (token.compareTo("FALSE"))==0){
			boolValue = literal();
			queries.put(number, boolValue);
			number = number+1;
		}else if(token.equalsIgnoreCase((keyWords.EQUALS).name())){
			lex();
			boolValue = literal();
		}
		else{
			if(accept(keyWords.VAR))
			if(assignments.containsKey(var))
			boolValue = assignments.get(var);
			else{
				System.out.println("Invalid Variable");
				System.exit(1);
			}
		}
		return boolValue;
	}
	private static String variable() throws IOException{
		String varibaleName = var;
		lex();
		if(token.equalsIgnoreCase((keyWords.EQUALS).name()))
			return varibaleName;
		return null;
	}
	private static boolean literal() throws IOException{
		if(accept(keyWords.TRUE))
			return true;
		else if(accept(keyWords.FALSE))
		return  false;
		return false;
	}
	private static boolean accept(keyWords k) throws IOException{
		if(token.equalsIgnoreCase(k.name())){
			lex();
			
			return true;
		}
		else
		return false;
	}
	private static void expect(keyWords k)throws IOException{
		if(token.equalsIgnoreCase(k.name())){
			lex();
			return;
		}
			
		else {
			System.out.println("Expected" +k.name() + "Actual " + token);
			System.exit(0);
		}		
		
	}
	
	private static void lex() throws IOException{
		
		StringBuilder st = new StringBuilder();
		char c ;
		if((c = (char) din.read())>0){
		if(c!=' '){
			if(Character.isLetter(c)){
				din.mark(c);
				st.append(c);
				while((c = (char) din.read())>0){
					if(c!=' ' && Character.isLetter(c)){
						din.mark(c);
						st.append(c);
					}
					else if(c==' '){
						din.mark(c);
						break;
					}	
					else{
						din.reset();
						break;
					}
				}
				if(((st.toString()).toLowerCase()).compareTo("let")==0){				
					key = keyWords.LET;
				}else if(((st.toString()).toLowerCase()).compareTo("query")==0){
					key = keyWords.QUERY;
				}else if(((st.toString()).toLowerCase()).compareTo("true")==0){
					key = keyWords.TRUE;
				}else if(((st.toString()).toLowerCase()).compareTo("false")==0){
					key = keyWords.FALSE;
				}else {
					var = st.toString();
					key = keyWords.VAR;
				}
			}			
			else if(c == '='){
				din.mark(c);
				key = keyWords.EQUALS;
			}
			else if(c== '.'){
				din.mark(c);
				key = keyWords.DOT;
			}
			else if(c == '('){
				din.mark(c);
				key = keyWords.LPAREN;
			}
			else if(c == ')'){
				din.mark(c);
				key = keyWords.RPAREN;
			}
			else if(c == '|'){
				din.mark(c);
				key = keyWords.OR;
			}
			else if(c == '&'){
				din.mark(c);
				key = keyWords.AND;
			}
			else if(c == '~'){
				din.mark(c);
				key = keyWords.NEGATION;
			}
			else if(c == '>'){
				din.mark(c);
				key = keyWords.GT;
			}
			else if(c ==';'){
				din.mark(c);
				key = keyWords.SEMICOLON;
			}
			else if(c == '<'){
				din.mark(c);
				key = keyWords.LT;
				token = key.name();
				if((char)din.read() =='='){				
					if((char)din.read() =='>')
				key = keyWords.EQUIVALENCE;
				}
			}
			else if(c == '-'){
				key = keyWords.HYPEN;
				token = key.name();
				if((char) din.read() == '>'){
				key = keyWords.IMPLIES;
				}
			}
			}
		else{
			din.mark(c);
			lex();
		}
		}
		
		token = key.name();
		token.toLowerCase();
	}
}
