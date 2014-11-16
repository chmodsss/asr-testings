package org.speech.testspeech;

import java.io.*;
import java.util.*;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import org.apache.commons.io.*;

public class App 
{
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Hello World!");
		Configuration configuration = new Configuration();

		// Set path to acoustic model.
		configuration	.setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
		// Set path to dictionary.
		configuration.setDictionaryPath("resource/cmudict.0.6d");
		// Set language model.
		configuration.setLanguageModelPath("resource/en-us.lm.dmp");
		PrintWriter outFile = new PrintWriter("resultOutputNew.txt", "UTF-8");
		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		try 
		{
			File dir = new File("resource/Firefox005-20100820-qku/wav");
			System.out.println("File loading completed...\n");
			System.out.println("Start of recognition...\n");
			ArrayList<String> filePath = new ArrayList<String>();
			ArrayList<String> fileNameExtension = new ArrayList<String>();
			for (File each : dir.listFiles()){
				filePath.add(each.getAbsolutePath());
				fileNameExtension.add(each.getName());
			}
			Collections.sort(filePath);
			Collections.sort(fileNameExtension);
			for (int idx=0; idx<filePath.size(); idx++){
				recognizer.startRecognition(new FileInputStream(filePath.get(idx)));
				SpeechResult result = recognizer.getResult();
				recognizer.stopRecognition();
				String sentenceDetected = result.getHypothesis();
				String fileName = FilenameUtils.removeExtension(fileNameExtension.get(idx));
				outFile.print(fileName+" ");
				outFile.println(sentenceDetected);
				}
				outFile.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not available in the given path...");
		}
		System.out.println("End of recognition...\n");
		System.out.println("Exit...");
	}
}