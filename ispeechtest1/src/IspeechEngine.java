
import java.io.File;
import com.iSpeech.SpeechResult;
import com.iSpeech.iSpeechRecognizer;
import com.iSpeech.iSpeechRecognizer.SpeechRecognizerEvent;

public class IspeechEngine implements SpeechRecognizerEvent
{
	public IspeechEngine()
	{
		
	}
	public  void runFile(String api, boolean production)
	{
		try 
		{
			iSpeechRecognizer iSpeech = new iSpeechRecognizer(api, production);
			iSpeech.setFreeForm(3);
			//iSpeech.setLocale("es-ES");
			
			String fil = "resource/speechCorpora/anonymous-20140425-qbj/wav/a0336.wav";
			SpeechResult result = iSpeech.startFileRecognize(fil, new File(fil), this);
			System.out.println("Result = "+ result.Text + " "+result.Confidence);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//@Override
	public void stateChanged(int event, int freeFormValue,Exception lastException) {
		
	/*	if(event == SpeechRecognizerEvent.RECORDING_COMMITTED);
			System.out.println("event="+event + " freeform = "+ freeFormValue +" ");*/
		
	}

}
