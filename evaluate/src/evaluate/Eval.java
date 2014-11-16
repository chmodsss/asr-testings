package evaluate;

import java.io.*;
import java.util.*;

public class Eval {

	public static void main(String[] args) throws IOException {
		File ref = new File("resource/prompts-original");
		File hyp = new File("resource/resultOutput");
		System.out.println("Hello world...");
		BufferedReader readRef = new BufferedReader(new FileReader(ref));
		BufferedReader readHyp = new BufferedReader(new FileReader(hyp));
		String refLine;
		String[] refWords;
		String[] hypWords;
		while ((refLine = readRef.readLine()) != null) {
			String hypLine = readHyp.readLine();
		    System.out.println("REF : "+refLine);
		    System.out.println("HYP : "+hypLine);
		    refLine = refLine.replace("."," ");
		    refWords = refLine.split(" ");
		    hypWords = hypLine.split(" ");
		    Boolean flag = false;
		    int hits = 0;
		    for(String each : refWords){
			    for(int idx=0; idx <hypWords.length; idx++){
			    	int result = each.compareToIgnoreCase(hypWords[idx]);
			    		if ((result==0) && (flag == false)){
			    			System.out.println("Matched words..."+each);
			    			hits++;
			    			flag = true;
			    	}
		    	}
			    flag = false;
		   }
		    System.out.println("Number of hits.. "+(hits-1));
		}
		readRef.close();
	}

}
