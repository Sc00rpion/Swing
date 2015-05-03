import java.util.ArrayList;
import java.util.Random;

/**
 * @author Maciej
 *
 */
public class Ngram /*implements Comparable<Ngram> */{
	private String[] prefiksy;
	private ArrayList<String> sufiksy =new ArrayList<String>(10);
	
	//-------------------------------------------------------
	
	public Ngram(int stopienNgramu){
		prefiksy = new String[stopienNgramu];
	}
	
	public Ngram(String[] p, String s){
		prefiksy= p.clone();
		sufiksy.add(s);
		
	}
	public Ngram(String[] p){
		prefiksy=p;	
	}
//	------------------------------------------------------------
	public String getKey(){
		int n = this.prefiksy.length;
		if (n == 0){
			return null;
		}
		String tmp = new String(this.prefiksy[0]);
		for( int i=1; i < n; i++){
			tmp =tmp + " " + this.prefiksy[i];
		}
		return tmp;
	}
	public void addSufiks(String s){
		this.sufiksy.add(s);
	}
	public String getRandomSufiks(){
		Random r = new Random();
		return sufiksy.get(r.nextInt(sufiksy.size()));
	}
	public String getFirstSufiks(){
		return this.sufiksy.get(0);
	}
	
	/**
	 * @param sufiks - podajemy sufiks z którego ma byæ zrobiony klucz
	 * @return zwraca klucz budowany z czesci prefiksu i jednego sufiksu
	 */
	public String getKey(String sufiks){
		int n = this.prefiksy.length;
		if (n == 0){
			return null;
		}
		String p;
		if(n == 1){
			 p = new String(sufiks);
			 return p;
		}else{
			 p = new String(this.prefiksy[1]);	
		}
		
		for( int i=2; i <n; i++){
			p = p + " " + this.prefiksy[i];
		}
		p = p + " " + sufiks;
		return p;
	}
	
//	@Override
//	public int compareTo(Ngram o) {
//		int n = this.prefiksy.length;
//		int x;
//		for(int i = 0; i < n; i++ ){
//			if ((x = this.prefiksy[i].compareTo(o.prefiksy[i])) != 0);
//			return x;
//			
//		}
//		return 0;
//	}
	
	@Override
	public String toString() {
		String x = new String("");
		for(int i = 0; i < prefiksy.length; i++){
			 x = x + " " + prefiksy[i];
		}
		x = "Prefiksy -->" + x + " Sufiksy -->" + sufiksy.toString() + "\n";
		return x;
	}

}
