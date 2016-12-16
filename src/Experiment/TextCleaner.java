package Experiment;
import java.io.*;

public class TextCleaner {
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		int c;
		
		boolean noespacio = true;
		
		StringBuilder texto = new StringBuilder(100000000);
		File file = new File("english.txt");
		try (InputStream in = new FileInputStream(file); Reader reader = new InputStreamReader(in)) {
			while (((c = reader.read()) != -1) && (texto.length() < 100000000)) {
				Character d = new Character((char) c);
				if (d==' '||d=='\n'){
					if(noespacio) {
						texto.append(' ');
						noespacio = false;
					}		
				}else if (((char) d>='a'&&(char) d<='z')||((char) d>='A'&&(char) d<='Z')) {
						texto.append(d);
						noespacio = true;
					}
				}
			}
		
		PrintWriter writer = new PrintWriter("texto.txt", "UTF-8");
		writer.println(texto);
		writer.close();

	}

}
