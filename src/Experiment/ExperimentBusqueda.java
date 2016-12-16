package Experiment;
	import java.io.*;
	import java.util.Random;

import SuffixTree.SearchResult;
import SuffixTree.SuffixTree;
import SuffixTree.SuffixTreeFactory;
	public class ExperimentBusqueda {
		private static Random rand=new Random();
		public static void main(String[] args) throws IOException {
			PrintWriter writer = new PrintWriter("resul-exp.txt", "UTF-8");
			for(int exp=20;exp>14;exp--){
			//for(int exp=15;exp<26;exp++){			
				BufferedReader br = new BufferedReader(new FileReader("texto.txt"));
				String text = br.readLine();
				int largo=(int) Math.pow(2, exp);
				text=text.substring(0,largo<text.length()?largo:text.length());
				System.out.println("comenzando construccion "+exp);
				long tic = System.currentTimeMillis();
				SuffixTree st = SuffixTreeFactory.build(text.concat("$"));
				long tfc = System.currentTimeMillis();
		    	long tiempoc=tfc-tic;
				//st.buildInnerNodeLabels();
				System.out.println("build terminada en "+tiempoc);
				writer.print(tiempoc);
				//int costo=st.costo();
				//writer.println("costo construccion= "+costo);
				int n=20000;
				System.out.println("waa"+n);
				int[] largos={2,4,8,12,16};
				int [] patrones;
				for(int l:largos){
					patrones=getRandomsPatterns(text.length()-l,n);
					System.out.println("comenzando busqueda"+largo+" "+l);
					long ti = System.currentTimeMillis();
					for(int patronindex :patrones){
						String patron=text.substring(patronindex,patronindex+l);
						SearchResult res = st.searchAll(patron);					
						//long time=res.getTime();
						//writer.println(pat.length()+":"+time+":"+res.getResults().size());
					}
					long tf = System.currentTimeMillis();
			    	long tiempo=tf-ti;
			    	writer.print(","+tiempo);
				}
				writer.print("\n");
				
				br.close();
				
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