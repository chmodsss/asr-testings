package project.speech.evaluationsystem;

import project.speech.userInterface.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private static File evaluationOutputWithRecognition = new File("evaluationOutput");
	private static File evalOutputFileComparison = new File ("comparisonOutput");
	private static ArrayList<File> speechDatabase = new ArrayList<File>();
	private static ArrayList<File> outputDatabase = new ArrayList<File>();
	// private static ArrayList<File> speechFiles = new ArrayList<File>();
	private static ArrayList<File> referenceFilesList = new ArrayList<File>();
	private static File wavFilesPath;
	private static File refEvaluationFile;
	private static File hypEvaluationFile;
	private static File timeEvaluationFile;
	private static File cmuDictFile;
	private static String cmuAcousString;
	private static File cmuLangFile;
	private static String asrOutString;
	private static String timeOutString;
	private static double wer;
	private static double mar;
	private static double recall;
	private static boolean fileOccured = false;
	private static boolean isEvalutionDirectoryOccured = false;
	private static boolean isComparisonDirectoryOccured = false;
	private static boolean asr1Status = false;
	private static boolean asr2Status = false;
	private static String algorithm1 = "Algorithm1";

	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}


	public static void evaluateFromFiles (File referenceFile, File hypothesisFile , ArrayList<String> performanceList , String algorithmSelected) throws IOException {
		
		if (algorithmSelected.equals(algorithm1)){
		Evaluator e1 = new Evaluator(referenceFile, hypothesisFile);
		EvaluatorResult eResult = e1.evaluateNoTime();
		
		if (!isComparisonDirectoryOccured ){
			FileUtils.deleteDirectory(evalOutputFileComparison);
			evalOutputFileComparison.mkdirs();
			isComparisonDirectoryOccured = true;
		}
		File evaluationResult = new File(evalOutputFileComparison, "comparison-result.txt");
			asrOutString = hypothesisFile.getName();
			writeResultFile(evaluationResult, performanceList, eResult, asrOutString);
			UiMethod2Frame.btnGetResult2.setEnabled(true);
		}
	}

	
	public static void writeResultFile (File f , ArrayList<String> performanceList , EvaluatorResult result , String asrName) throws IOException{
		PrintWriter printResultFile = new PrintWriter(new FileWriter((f),true));
		if (!fileOccured){
			printResultFile.print("\n\n:::::::::::::::::::::::::::::::::::::::::::::::  Evaluation Result  :::::::::::::::::::::::::::::::::::::::::::::::");
			fileOccured = true;
		}
		wer = (result.getSubstitutions()+result.getDeletions()+result.getInsertions())/result.getNumberOfWords();
		mar = (result.getHits()+result.getDeletions()+result.getInsertions()+result.getSubstitutions())/result.getHits();
		recall = result.getHits()/result.getNumberOfWords();
		printResultFile.print("\n\n<<<<<<<<<<<<<<<<<<<< "+FilenameUtils.removeExtension(asrOutString)+" >>>>>>>>>>>>>>>>>>>>");

		if (performanceList.contains("WER"))
			printResultFile.print("\t"+"WER : " + wer);
		if (performanceList.contains("SER"))
			printResultFile.print("\t"+"SER : " + 0);
		if (performanceList.contains("MUC"))
			printResultFile.print("\t"+"MUC : " +(1- mar));
		if (performanceList.contains("ACC"))
			printResultFile.print("\t"+"ACC : " + mar);				
		
		printResultFile.close();
	}

	public static void recogniseAndEvaluate(File speechCorpusFile, ArrayList<UiAsrProperties> asrPropertiesObj, ArrayList<String> selectedPerformanceList , ArrayList<String> selectedAsrList, String algorithmSelected) throws Exception {
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
			File currentFolder = speechDatabase.get(i);
			File promptOriginal = null;
			if (currentFolder.isDirectory() == true) {
				for (File each : currentFolder.listFiles()) {
					if (each.getName().compareTo("wav") == 0) {
//						speechFiles = readDirectory(each);
						wavFilesPath = each;
					}
					if (each.getName().compareTo("etc") == 0) {
						referenceFilesList = readDirectory(each);
						for (File files : referenceFilesList) {
							if (files.getName().compareTo("prompts-original") == 0) {
								promptOriginal = files;
							}
						}
					}
				}
			}
		
			if (asr1Status){
				
				cmuDictFile = asrPropertiesObj.get(0).getUiDictionary();
				cmuAcousString = "resource:/"+FilenameUtils.removeExtension(asrPropertiesObj.get(0).getUiAcoustic().getName());
				cmuLangFile = asrPropertiesObj.get(0).getUiLanguage();
				
				System.out.println("Cmu dict file... :"+ cmuDictFile);
				System.out.println("Cmu Acous file... :"+ cmuAcousString);
				System.out.println("Cmu Lang file... :"+ cmuLangFile);
				
				CmuSphinxEngine cmu = new CmuSphinxEngine(cmuDictFile, cmuAcousString, cmuLangFile);
				Configuration conf = cmu.configure();

				cmu.recognizeSpeech(conf, currentFolder, wavFilesPath, promptOriginal);
			}

			if (asr2Status){
				IspeechEngine ise = new IspeechEngine();
				ise.runFile(currentFolder, wavFilesPath, promptOriginal);
			}
		}
		outputDatabase = readDirectory(asrOutput);
		for (int i = 0; i < outputDatabase.size(); i++) {
			for (int j=0; j<selectedAsrList.size(); j++){
				if (selectedAsrList.get(j).contains("cmusphinx")){
					asrOutString = "CmuSphinx-output.txt";
					timeOutString = "CmuSphinx-time.txt";
				}
				else if (selectedAsrList.get(j).contains("ispeech")){
					asrOutString = "iSpeech-output.txt";
					timeOutString = "iSpeech-time.txt";
				}
				File currentFolder = outputDatabase.get(i);
				if (currentFolder.isDirectory() == true) {
					for (File each : currentFolder.listFiles()) {
						if (each.getName().compareTo("prompts-original.txt") == 0){
						hypEvaluationFile = each;
						}
						else if (each.getName().compareTo(asrOutString) == 0){
						refEvaluationFile = each;
						}
						else if (each.getName().compareTo(timeOutString) == 0){
						timeEvaluationFile = each;
						}
					}
					
					if (algorithmSelected.equals(algorithm1)){
						
					Evaluator e = new Evaluator(refEvaluationFile , hypEvaluationFile , timeEvaluationFile); 
					EvaluatorResult eResult = e.evaluateWithTime();
				
					if (!isEvalutionDirectoryOccured ){
						FileUtils.deleteDirectory(evaluationOutputWithRecognition);
						evaluationOutputWithRecognition.mkdirs();
						isEvalutionDirectoryOccured = true;
					}
					File evaluationResult = new File(evaluationOutputWithRecognition, "evaluation-result.txt");
					
					writeResultFile(evaluationResult, selectedPerformanceList, eResult, asrOutString);
					}
//					evalOutFile.print("\t"+"Recall : " + recall);
//					evalOutFile.print("\t"+"Timetaken : " + result.getTime()+"s");
					
				}
			}
			fileOccured = false;
		}
		UiMethod1Frame.btnResult.setEnabled(true);
	}

}