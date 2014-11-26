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


	public CmuSphinxEngine() {
		System.out.println("cmu object instantiated...");
		languageModel = "resource/models/language/en-us.lm.dmp";
		dictionaryModel = "resource/models/dictionary/cmudict.0.6d";
		acousticModel = "resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz";
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
		
		ArrayList<String> speechFilesArrayCmu = new ArrayList<String>();
		FileDetails fdCmu = FileReader.reader(speeches);

		for (int idx = 0; idx < fdCmu.getFilePath().size(); idx++) {
			String cmuCurrentPath = fdCmu.getFilePath().get(idx);
			if (! speechFilesArrayCmu.contains(cmuCurrentPath)){
				recognizer.startRecognition(new FileInputStream(cmuCurrentPath));
				System.out.println("Cmu File path size..."+ fdCmu.getFilePath().size());
				SpeechResult result = recognizer.getResult();
				recognizer.stopRecognition();
				String sentenceDetected = result.getHypothesis();
				String fileName = FilenameUtils.removeExtension(fdCmu.getFileNameExtension().get(idx));
				FileScripter.writer(asrName, databaseName, referenceFile, fileName, sentenceDetected);
			}
		}
		System.out.println("End of recognition...\n");
		System.out.println("Exit...");
	}
}