import java.io.*;
import java.util.Random;
public class Experiment {
	private static Random rand=new Random();
	public static void main(String[] args) {
		
		for(int exp=15;exp<26;exp++){
			PrintWriter writer = new PrintWriter("resul-exp-"+exp+".txt", "UTF-8");
			int patsize=20;
			BufferedReader br = new BufferedReader(new FileReader("texto.txt"));
			String text = br.readLine();
			text=text.substring(0, 2^exp);
			SuffixTree st = SuffixTreeFactory.build(text.concat("$"));
			int costo=st.costo();
			writer.println("costo construccion= "+costo);
			int n=text.length()/10;
			
			
			for(int i=0;i<n;i++){
				String pat=getRandomWordFromText(text);
				long time=st.search(pat).getTime();
				writer.println(pat.length()+":"+time);
			}
			writer.close();
		}
	}
	
	public static String getRandomWordFromText(String text){
		
		int i = randomBetween(0, text.length());
		int ini=text.lastIndexOf(" ", i);
		int fin=text.indexOf(" ", i+1);
		return text.substring(ini==-1?0:ini,fin==-1?text.length():fin);
	}
	
	private static int randomBetween(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

}
