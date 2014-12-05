package project.speech.asrengines;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import project.speech.readerAndWriter.*;

public class CmuSphinxEngine {

	private String languageModel;
	private String dictionaryModel;
	private String acousticModel;
	private String asrName;
	private ArrayList<String> outputSentencesCmu = new ArrayList<String>();
	private ArrayList<String> outputFileNamesCmu = new ArrayList<String>();
	private double startTimeMsCmu;
	private double stopTimeMsCmu;
	private double timeDiffCmu;

	public CmuSphinxEngine() {
		System.out.println("cmu object instantiated...");
		languageModel = "resource/models/language/en-us.lm.dmp";
		dictionaryModel = "resource/models/dictionary/cmudict.0.6d";
		acousticModel = "resource:/WSJ_8gau_13dCep_8kHz_31mel_200Hz_3500Hz";
		asrName = "CmuSphinx";
	}

	public Configuration configure() {
		System.out.println("Configuring cmusphinx models...");
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath(acousticModel);
		configuration.setDictionaryPath(dictionaryModel);
		configuration.setLanguageModelPath(languageModel);
		return configuration;
	}

	public void recognizeSpeech(Configuration config, File databaseName,
			File speeches, File referenceFile) throws IOException {

		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
		System.out.println("Start of recognition...\n");
		
		FileReader frCmu = new FileReader();
		FileDetails fdCmu = frCmu.reader(speeches);

		startTimeMsCmu = System.currentTimeMillis();
		outputSentencesCmu.clear();
		outputFileNamesCmu.clear();
		for (int idx = 0; idx < fdCmu.getFilePath().size(); idx++) {
			String cmuCurrentPath = fdCmu.getFilePath().get(idx);
			System.out.println("cmu audio files... "+ cmuCurrentPath);
			recognizer.startRecognition(new FileInputStream(cmuCurrentPath));
			System.out.println("Cmu File path size..."+ fdCmu.getFilePath().size());
			SpeechResult result = recognizer.getResult();
			recognizer.stopRecognition();
			String sentenceDetected = result.getHypothesis();
			String fileName = FilenameUtils.removeExtension(fdCmu.getFileNameExtension().get(idx));
			outputSentencesCmu.add(sentenceDetected);
			outputFileNamesCmu.add(fileName);
		}
		stopTimeMsCmu = System.currentTimeMillis();
		timeDiffCmu = (stopTimeMsCmu - startTimeMsCmu)/1000;
		FileScripter.writer(asrName, databaseName, referenceFile, outputFileNamesCmu, outputSentencesCmu , timeDiffCmu);
		System.out.println("End of recognition...\n");
		System.out.println("Exit...");
	}
}