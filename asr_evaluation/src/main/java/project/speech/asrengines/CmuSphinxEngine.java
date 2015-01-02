package project.speech.asrengines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import project.speech.globalAccess.Globals;
import project.speech.readerAndWriter.*;

public class CmuSphinxEngine {

	private String languageModel;
	private String dictionaryModel;
	private String acousticModel;
	private ArrayList<String> outputSentencesCmuList = new ArrayList<String>();
	private ArrayList<String> outputFileNamesCmuList = new ArrayList<String>();
	private double startTimeMsCmu;
	private double stopTimeMsCmu;
	private double timeDifferenceCmu;

	public CmuSphinxEngine(File dictionary, String acoustic, File language) {
		System.out.println("cmu object instantiated...");
		dictionaryModel = dictionary.getPath();
		acousticModel = acoustic;
		languageModel = language.getPath();
	}

	public Configuration configure() {
		System.out.println("Configuring cmusphinx models...");
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath(acousticModel);
		configuration.setDictionaryPath(dictionaryModel);
		configuration.setLanguageModelPath(languageModel);
		return configuration;
	}

	public void recognizeSpeech(Configuration config, File currentSpeechFolder, File currentSpeechFiles, File referenceFile) throws IOException {

		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
		System.out.println("Start of recognition...\n");
		
		FileReader frCmu = new FileReader();
		FileDetails fdCmu = frCmu.reader(currentSpeechFiles);

		startTimeMsCmu = System.currentTimeMillis();
		outputSentencesCmuList.clear();
		outputFileNamesCmuList.clear();
		for (int idx = 0; idx < fdCmu.getFilePath().size(); idx++) {
			String cmuCurrentPath = fdCmu.getFilePath().get(idx);
			System.out.println("recognition started...");
				try{
					recognizer.startRecognition(new FileInputStream(cmuCurrentPath));
					SpeechResult result = recognizer.getResult();
					System.out.println("result obtined...");
					recognizer.stopRecognition();
					System.out.println("recognition stopped...");
					String sentenceDetected = result.getHypothesis();
					System.out.println("result get hypothesis...");
					String fileName = FilenameUtils.removeExtension(fdCmu.getFileNameExtension().get(idx));
					System.out.println("sentence detected adding...");
					outputSentencesCmuList.add(sentenceDetected);
					outputFileNamesCmuList.add(fileName);
				}
				catch (Exception e){
					System.out.println("It is null bro...");
					String sentenceDetected = "  ";
					System.out.println("result get hypothesis...");
					String fileName = FilenameUtils.removeExtension(fdCmu.getFileNameExtension().get(idx));
					System.out.println("sentence detected adding...");
					outputSentencesCmuList.add(sentenceDetected);
					System.out.println("sentence completed...");
					outputFileNamesCmuList.add(fileName);	
					System.out.println("file name completed...");
				}
		}
			
		stopTimeMsCmu = System.currentTimeMillis();
		timeDifferenceCmu = (stopTimeMsCmu - startTimeMsCmu)/1000;
		FileScripter.writer(Globals.asr1SelectionNameUI, currentSpeechFolder, referenceFile, outputFileNamesCmuList, outputSentencesCmuList , timeDifferenceCmu);
		System.out.println("End of recognition...\n");
		System.out.println("Exit...");
	}
}