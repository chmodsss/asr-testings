package project.speech.asrengines;

import java.io.File;
import java.util.ArrayList;

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
		FileDetails fdIspeech = FileReader.reader(speeches);
		iSpeechRecognizer iSpeech = new iSpeechRecognizer(api, production);
		iSpeech.setFreeForm(3);
		ArrayList<String> speechFilesArrayIspeech = new ArrayList<String>();
		for (int idx = 0; idx < fdIspeech.getFilePath().size(); idx++) {
			String ispeechCurrentPath = fdIspeech.getFilePath().get(idx);
			if (! speechFilesArrayIspeech.contains(ispeechCurrentPath)){
				speechFilesArrayIspeech.add(ispeechCurrentPath);
				System.out.println("ispeech File path size..."+ fdIspeech.getFilePath().size());
				System.out.println("idx value..."+ idx);
				SpeechResult result = iSpeech.startFileRecognize(ispeechCurrentPath, new File(ispeechCurrentPath), this);
				System.out.println("current path name... "+ispeechCurrentPath);
				// iSpeech.setLocale("es-ES");
				String sentenceDetected = result.Text;
				System.out.println("start");
				System.out.println("Result = " + result.Text + " "+ result.Confidence);
				System.out.println("stop");
				String fileName = FilenameUtils.removeExtension(fdIspeech.getFileNameExtension().get(idx));
				FileScripter.writer(asrName, databaseName, referenceFile, fileName, sentenceDetected);
			}
		}	
	}
	
	// @Override
	public void stateChanged(int event, int freeFormValue,	Exception lastException) {
		  if(event == SpeechRecognizerEvent.RECORDING_ERROR);
		  System.out.println(RECORDING_ERROR);
	}
}
