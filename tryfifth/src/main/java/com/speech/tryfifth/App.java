package com.speech.tryfifth;

import java.io.IOException;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Configuration configuration = new Configuration();
        
     // Set path to acoustic model.
     configuration.setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
     // Set path to dictionary.
     configuration.setDictionaryPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
     // Set language model.
     configuration.setLanguageModelPath("models/language/en-us.lm.dmp");
     
     LiveSpeechRecognizer recognizer;
	try {
		recognizer = new LiveSpeechRecognizer(configuration);
		  // Start recognition process pruning previously cached data.
		  recognizer.startRecognition(true);
		  SpeechResult result = recognizer.getResult();
		  // Pause recognition process. It can be resumed then with startRecognition(false).
		  recognizer.stopRecognition();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
}
