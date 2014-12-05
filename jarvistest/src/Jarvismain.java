import java.io.File;

import javax.speech.recognition;

public class Jarvismain {

	public static void main(String args[]) throws Exception{
	System.out.println("hello worldie...");
	Recognizer r = new Recognizer( true);
	System.out.println(r.getLanguage());
	System.out.println(r.getProfanityFilter());
	File speech = new File("a0337.wav");
	System.out.println(speech.getAbsolutePath());
	GoogleResponse rep = r.getRecognizedDataForWave(speech);
	System.out.println(rep.getResponse());
}
}