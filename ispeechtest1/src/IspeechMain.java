import com.iSpeech.InvalidApiKeyException;

public class IspeechMain 
{
	public static String api = "developerdemokeydeveloperdemokey"; //Get your API key at http://www.ispeech.org/developers
	public static boolean production = true; //Your API key server access, false is development and true is production
	
	public static void main(String [] args) throws InvalidApiKeyException, Exception
	{
		long time = System.currentTimeMillis();
		
		IspeechEngine iSpeechASR = new IspeechEngine();
		iSpeechASR.runFile(api, production);
		//tries to recognize speech from tts.wav
		
		System.out.println("Transaction took: " + (System.currentTimeMillis()-time) + " milliseconds");
	}
}
