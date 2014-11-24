import com.iSpeech.InvalidApiKeyException;

public class iSpeechAPI 
{
	public static String api = "developerdemokeydeveloperdemokey"; //Get your API key at http://www.ispeech.org/developers
	public static boolean production = true; //Your API key server access, false is development and true is production
	
	public static void main(String [] args) throws InvalidApiKeyException, Exception
	{
		long time = System.currentTimeMillis();
		
		mainimain iSpeechTTS = new mainimain(api, production);
		//downloads text to file tts.wav
		
		iSpeechASR iSpeechASR = new iSpeechASR();
		iSpeechASR.runFile(api, production);
		//tries to recognize speech from tts.wav
		
		System.out.println("Transaction took: " + (System.currentTimeMillis()-time) + " milliseconds");
	}
}
