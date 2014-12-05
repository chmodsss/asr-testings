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
	private static String api;
	private static boolean production;
	private String asrName;
	private ArrayList<String> outputSentencesIspeech = new ArrayList<String>();
	private ArrayList<String> outputFileNamesIspeech = new ArrayList<String>();
	private double startTimeMsIspeech;
	private double stopTimeMsIspeech;
	private double timeDiffIspeech;

	public IspeechEngine() {
		api = "developerdemokeydeveloperdemokey"; // Get your API key at http://www.ispeech.org/developers
	//	api = "8226f10732ad273c3791002d3d6b8332";
		production = true; // Your API key server access, false is development and true is production
		asrName = "iSpeech";
	}

	public void runFile(File databaseName,File speeches, File referenceFile) throws Exception {
		FileReader frIspeech = new FileReader();
		FileDetails fdIspeech = frIspeech.reader(speeches);
		iSpeechRecognizer iSpeech = new iSpeechRecognizer(api, production);
		iSpeech.setFreeForm(3);
		
		outputSentencesIspeech.clear();
		outputFileNamesIspeech.clear();
		startTimeMsIspeech = System.currentTimeMillis();
		for (int idx = 0; idx < fdIspeech.getFilePath().size(); idx++) {
			String ispeechCurrentPath = fdIspeech.getFilePath().get(idx);
			System.out.println("ispeech File path size..."+ fdIspeech.getFilePath().size());
			System.out.println("idx value..."+ idx);
			SpeechResult result = iSpeech.startFileRecognize(ispeechCurrentPath, new File(ispeechCurrentPath), this);
			System.out.println("current path name... "+ispeechCurrentPath);
			// iSpeech.setLocale("es-ES");
			String sentenceDetected = result.Text;
			System.out.println("Result = " + result.Text + " "+ result.Confidence);
			String fileName = FilenameUtils.removeExtension(fdIspeech.getFileNameExtension().get(idx));
			outputSentencesIspeech.add(sentenceDetected);
			outputFileNamesIspeech.add(fileName);
		}
		stopTimeMsIspeech = System.currentTimeMillis();
		timeDiffIspeech = (stopTimeMsIspeech - startTimeMsIspeech)/1000;
		FileScripter.writer(asrName, databaseName, referenceFile, outputFileNamesIspeech, outputSentencesIspeech , timeDiffIspeech);	
	}
	
	// @Override
	public void stateChanged(int event, int freeFormValue,	Exception lastException) {
		  if(event == SpeechRecognizerEvent.RECORDING_ERROR);
		  System.out.println(RECORDING_ERROR);
	}
}
