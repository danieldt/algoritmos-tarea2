import java.io.*;
import java.util.Random;
public class Experiment {
	private static Random rand=new Random();
	public static void main(String[] args) throws IOException {
		
		for(int exp=15;exp<25;exp++){
			PrintWriter writer = new PrintWriter("resul-exp-"+exp+".txt", "UTF-8");
			int patsize=20;
			BufferedReader br = new BufferedReader(new FileReader("texto.txt"));
			String text = br.readLine();
			int largo=(int) Math.pow(2, exp);
			text=text.substring(0,largo<text.length()?largo:text.length());
			System.out.println("empezando onstru");
			SuffixTree st = SuffixTreeFactory.build(text.concat("$"));
			System.out.println("ontru terminada");
			st.buildInnerNodeLabels();
			System.out.println("build terminada");
			//int costo=st.costo();
			//writer.println("costo construccion= "+costo);
			int n=text.length()/10;
			System.out.println("waa"+n);
			
			for(int i=0;i<n;i++){
				String pat=getRandomWordFromText(text);
				SearchResult res = st.searchAllWithLabels(pat);
				//SearchResult res = st.searchAll(pat);
				
				long time=res.getTime();
				writer.println(pat.length()+":"+time+":"+res.getResults().size());
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
