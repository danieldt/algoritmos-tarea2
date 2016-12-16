import java.io.*;
import java.util.Random;
public class Experiment {
	private static Random rand=new Random();
	public static void main(String[] args) throws IOException {
		PrintWriter writer = new PrintWriter("resul-exp.txt", "UTF-8");
		BufferedReader br = new BufferedReader(new FileReader("texto.txt"));
		String textocom = br.readLine();
		String text;
		//for(int exp=25;exp>14;exp--){
		for(int k=0;k<10;k++){
		for(int exp=15;exp<25;exp++){	
			//PrintWriter writer = new PrintWriter("resul-cons-"+exp+".txt", "UTF-8");
			int largo=(int) Math.pow(2, exp);
			text=textocom.substring(0,largo<textocom.length()?largo:textocom.length()).concat("$");
			System.out.println("comenzando construccion "+exp);
			long tic = System.currentTimeMillis();
			SuffixTree st = SuffixTreeFactory.build(text);
			long tfc = System.currentTimeMillis();
	    	long tiempoc=tfc-tic;
			//st.buildInnerNodeLabels();
			System.out.println("build terminada en "+tiempoc);
			writer.print(tiempoc+",");
			/*
			int[] tiempos = st.getTiempos();
			for (int j = 0; j < tiempos.length; j++) {
				writer.println(tiempos[j]);
			}
			*/
			
			br.close();
			
		}
		writer.print("\n");
		}
		writer.close();
	}
	
	public static int[]  getRandomsPatterns(int max, int total){
		int[] res=new int[total];
		for(int i=0;i<total;i++){
			res[i]=rand.nextInt(max+1);
		}
		return res;
	}
	

}
