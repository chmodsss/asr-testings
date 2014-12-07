package project.speech.evaluationsystem;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import edu.cmu.sphinx.api.Configuration;
import project.speech.asrengines.*;
import project.speech.evaluator.*;
import project.speech.userInterface.UiAsrProperties;

public class EvaluationSystem {

	private static File speechCorpora = null;
	private static File asrOutput = new File("asrOutput");
	private static File evalOutput = new File("evalOutput");
	private static ArrayList<File> speechDatabase = new ArrayList<File>();
	private static ArrayList<File> outputDatabase = new ArrayList<File>();
	// private static ArrayList<File> speechFiles = new ArrayList<File>();
	private static ArrayList<File> referenceFiles = new ArrayList<File>();
	private static File speechFile;
	private static File refEvaluationFile;
	private static File hypEvaluationFile;
	private static File timeEvaluationFile;
	private static File cmuDictFile;
	private static String cmuAcousFile;
	private static File cmuLangFile;
	private static String asrOutFile;
	private static String timeOutFile;
	private static double wer;
	private static double mar;
	private static double recall;
	private static boolean fileOccured = false;
	private static boolean directoryOccured = false;
	private static boolean asr1Status = false;
	private static boolean asr2Status = false;

	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}

	public static void executeEvaluation(File speechCorpusFile, ArrayList<UiAsrProperties> asrPropertiesObj, ArrayList<String> selectedPerformanceList , ArrayList<String> selectedAsrList) throws Exception {
		System.out.println("Received values...");
		
		if (asrOutput.exists()){
			FileUtils.deleteDirectory(asrOutput);
		}
		
		if (selectedAsrList.contains("cmusphinx"))
			asr1Status = true;
		if (selectedAsrList.contains("ispeech"))
			asr2Status = true;

		
		// Setting up speech database for recognition
		speechCorpora = speechCorpusFile;
		
		speechDatabase = readDirectory(speechCorpora);

		for (int i = 0; i < speechDatabase.size(); i++) {
			System.out.println("speech database size... "+speechDatabase.size());
			File currentDatabase = speechDatabase.get(i);
			File promptOriginal = null;
			if (currentDatabase.isDirectory() == true) {
				for (File each : currentDatabase.listFiles()) {
					if (each.getName().compareTo("wav") == 0) {
//						speechFiles = readDirectory(each);
						speechFile = each;
					}
					if (each.getName().compareTo("etc") == 0) {
						referenceFiles = readDirectory(each);
						for (File files : referenceFiles) {
							if (files.getName().compareTo("prompts-original") == 0) {
								promptOriginal = files;
							}
						}
					}
				}
			}
		
			if (asr1Status){
				
				cmuDictFile = asrPropertiesObj.get(0).getUiDictionary();
				cmuAcousFile = "resource:/"+FilenameUtils.removeExtension(asrPropertiesObj.get(0).getUiAcoustic().getName());
				cmuLangFile = asrPropertiesObj.get(0).getUiLanguage();
				
				System.out.println("Cmu dict file... :"+ cmuDictFile);
				System.out.println("Cmu Acous file... :"+ cmuAcousFile);
				System.out.println("Cmu Lang file... :"+ cmuLangFile);
				
				CmuSphinxEngine cmu = new CmuSphinxEngine(cmuDictFile, cmuAcousFile, cmuLangFile);
				Configuration conf = cmu.configure();

				cmu.recognizeSpeech(conf, currentDatabase, speechFile, promptOriginal);
			}

			if (asr2Status){
				IspeechEngine ise = new IspeechEngine();
				ise.runFile(currentDatabase, speechFile, promptOriginal);
			}
		}
		outputDatabase = readDirectory(asrOutput);
		for (int i = 0; i < outputDatabase.size(); i++) {
			for (int j=0; j<selectedAsrList.size(); j++){
				if (selectedAsrList.get(j).contains("cmusphinx")){
					asrOutFile = "CmuSphinx-output.txt";
					timeOutFile = "CmuSphinx-time.txt";
				}
				else if (selectedAsrList.get(j).contains("ispeech")){
					asrOutFile = "iSpeech-output.txt";
					timeOutFile = "iSpeech-time.txt";
				}
				File currentFolder = outputDatabase.get(i);
				if (currentFolder.isDirectory() == true) {
					for (File each : currentFolder.listFiles()) {
						if (each.getName().compareTo("prompts-original.txt") == 0){
						hypEvaluationFile = each;
						}
						else if (each.getName().compareTo(asrOutFile) == 0){
						refEvaluationFile = each;
						}
						else if (each.getName().compareTo(timeOutFile) == 0){
						timeEvaluationFile = each;
						}
					}
					Evaluator e = new Evaluator(refEvaluationFile , hypEvaluationFile , timeEvaluationFile); 
					EvaluatorResult result = e.evaluate();
				
					if (!directoryOccured ){
						FileUtils.deleteDirectory(evalOutput);
						evalOutput.mkdirs();
						directoryOccured = true;
					}
					File evaluationResult = new File(evalOutput, "evaluation-result.txt");
					PrintWriter evalOutFile = new PrintWriter(new FileWriter((evaluationResult),true));
					if (!fileOccured){
						evalOutFile.print("\n\n:::::::::::::::::::::::::::::::::::::::::::::::  " + currentFolder.getName() + "  :::::::::::::::::::::::::::::::::::::::::::::::");
						fileOccured = true;
					}
					wer = (result.getSubstitutions()+result.getDeletions()+result.getInsertions())/result.getNumberOfWords();
					mar = (result.getHits()+result.getDeletions()+result.getInsertions()+result.getSubstitutions())/result.getHits();
					recall = result.getHits()/result.getNumberOfWords();
					evalOutFile.print("\n"+FilenameUtils.removeExtension(asrOutFile));
//					evalOutFile.print("\t \t"+"Hits : " + result.getHits());
//					evalOutFile.print("\t"+"Insertions : " + result.getInsertions());
//					evalOutFile.print("\t"+"Deletions : " + result.getDeletions());
//					evalOutFile.print("\t"+"Substitutions : " + result.getSubstitutions());
					if (selectedPerformanceList.contains("WER"))
						evalOutFile.print("\t"+"Word error rate : " + wer);
					if (selectedPerformanceList.contains("SER"))
						evalOutFile.print("\t"+"Slot error rate : " + 0);
					if (selectedPerformanceList.contains("MUC"))
						evalOutFile.print("\t"+"Match error rate : " +(1- mar));
					if (selectedPerformanceList.contains("ACC"))
						evalOutFile.print("\t"+"Match accuracy rate : " + mar);				
//					evalOutFile.print("\t"+"Recall : " + recall);
//					evalOutFile.print("\t"+"Timetaken : " + result.getTime()+"s");
					evalOutFile.close();
				}
			}
			fileOccured = false;
		}
	}
}