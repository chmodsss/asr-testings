package project.speech.asrengines;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import project.speech.readerAndWriter.FileDetails;
import project.speech.readerAndWriter.FileReader;
import project.speech.readerAndWriter.FileScripter;

import com.iSpeech.SpeechResult;
import com.iSpeech.iSpeechRecognizer;
import com.iSpeech.iSpeechRecognizer.SpeechRecognizerEvent;

public class IspeechEngine implements SpeechRecognizerEvent {
	// Ispeech
	private static String api;
	private static boolean production;
	private String asrName;

	public IspeechEngine() {
		api = "developerdemokeydeveloperdemokey"; // Get your API key at http://www.ispeech.org/developers
		production = true; // Your API key server access, false is development and true is production
		asrName = "iSpeech";
	}

	public void runFile(File databaseName,File speeches, File referenceFile) throws Exception {
		FileDetails fd = FileReader.reader(speeches);
		iSpeechRecognizer iSpeech = new iSpeechRecognizer(api, production);
		iSpeech.setFreeForm(3);
		for (int idx = 0; idx < fd.getFilePath().size(); idx++) {
			String currentPath = fd.getFilePath().get(idx);
			SpeechResult result = iSpeech.startFileRecognize(currentPath, new File(currentPath), this);
			// iSpeech.setLocale("es-ES");
			String sentenceDetected = result.Text;
			System.out.println("Result = " + result.Text + " "+ result.Confidence);
			String fileName = FilenameUtils.removeExtension(fd.getFileNameExtension().get(idx));
			FileScripter.writer(asrName, databaseName, referenceFile, fileName, sentenceDetected);
		}	
	}
	
	// @Override
	public void stateChanged(int event, int freeFormValue,	Exception lastException) {
		  if(event == SpeechRecognizerEvent.RECORDING_ERROR);
		  System.out.println(RECORDING_ERROR);
	}
}
