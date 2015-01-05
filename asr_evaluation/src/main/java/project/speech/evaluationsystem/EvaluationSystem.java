package project.speech.evaluationsystem;

import project.speech.globalAccess.*;
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


	private static ArrayList<File> speechDatabaseFolders = new ArrayList<File>();
	private static ArrayList<File> recognitionOutputFolders = new ArrayList<File>();
	private static ArrayList<File> referenceFilesList = new ArrayList<File>();
	private static File currentSpeechFiles;
	private static File referenceFile;
	private static File hypothesisFile;
	private static File timeTakenFile;
	private static File cmuDictionaryFile;
	private static String cmuAcousticString;
	private static File cmuLanguageFile;
	private static String hypothesisFileName;
	private static String timeTakenCount;
	private static String asrUsed;
	
	private static int H;
	private static int S;
	private static int D;
	private static int I;
	private static int N;
	
	private static double percentHits;
	private static double percentSubstitutions;
	private static double percentDeletions;
	private static double percentInsertions;
	private static double wer;
	private static double mer;
	private static double wil;
	
	private static boolean evaluationResultFileOpened = false;
	private static boolean isEvalutionDirectoryOccured = false;
	private static boolean asr1Status = false;
	private static boolean asr2Status = false;

	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}
	
	public static void textEvaluation (File tempReferenceFile, File tempHypothesisFile , ArrayList<String> performanceList , String algorithmSelected) throws IOException {

		if (Globals.textEvaluationResultDirectory.exists()){
			FileUtils.deleteDirectory(Globals.textEvaluationResultDirectory);
			Globals.textEvaluationResultDirectory.mkdirs();
		}
		
		if (algorithmSelected.equals(Globals.hsdiWeightsAlgorithm)){
		EvaluationAligner e1 = new EvaluationAligner(tempReferenceFile, tempHypothesisFile);
		EvaluatorResult eResult = e1.evaluateNoTime();
		
		File currentFile = new File("");
		String currentPath = currentFile.getAbsolutePath();
		String newPath = currentPath +"/"+ Globals.textEvaluationResultDirectory;
		File textEvaluationResultFile = new File(newPath, Globals.textEvaluationResultFileName);
			hypothesisFileName = tempHypothesisFile.getName();
			File noNameFolder = new File("Results");
			writeResultFile(textEvaluationResultFile, performanceList, eResult, hypothesisFileName, noNameFolder);
			UiMethod2Frame.btnSaveResult2.setEnabled(true);
		}
	}
	
	public static void writeResultFile (File fileToWriteResult , ArrayList<String> tempSelectedPerformanceList , EvaluatorResult eresult , String tempHypothesisFileName, File subSpeechFolder) throws IOException{
		
		PrintWriter evaluationResultFileWriter = new PrintWriter(new FileWriter((fileToWriteResult),true));
		if (!evaluationResultFileOpened){
			evaluationResultFileWriter.print("\n [[[---------------------  Performance measures  ---------------------]]]  ");
			evaluationResultFileOpened = true;
		}
		H = eresult.getHits();
		S = eresult.getSubstitutions();
		D = eresult.getDeletions();
		I = eresult.getInsertions();
		N = eresult.getNumberOfWords();
		
		percentHits = ( H/(float)N ) * 100;
		percentSubstitutions = ( S/(float)N ) * 100;
		percentDeletions = ( D/(float)N ) * 100;
		percentInsertions = ( I/(float)N ) * 100;
		wer = (S+D+I) / (float)(H+S+D);
		mer = (S+D+I) / (float)(H+S+D+I);
		wil = 1- ( (H/(float)(H+S+D)) * (H/(float)(H+S+I)) );
		
		evaluationResultFileWriter.print("\n\n <<---------------------- "+FilenameUtils.removeExtension(tempHypothesisFileName)+"  ---------------------->> \n ");
		evaluationResultFileWriter.print("\n ==================== "+ subSpeechFolder.getName() +" ==================== \n\n ");

		if (tempSelectedPerformanceList.contains(Globals.hitsPercentUI))
			evaluationResultFileWriter.println("\t"+" Percent of correct words (% Hits) [0-100] : " + percentHits);
		
		if (tempSelectedPerformanceList.contains(Globals.subsPercentUI))
			evaluationResultFileWriter.println("\t"+" Percent of substituted words (% Subs) [0-100] : " + percentSubstitutions);
		
		if (tempSelectedPerformanceList.contains(Globals.delPercentUI))
			evaluationResultFileWriter.println("\t"+" Percent of deleted words (% Del) [0-100] : " + percentDeletions);
		
		if (tempSelectedPerformanceList.contains(Globals.insPercentUI))
			evaluationResultFileWriter.println("\t"+" Percent of inserted words (% Ins) [0-100] : " + percentInsertions);
		
		if (tempSelectedPerformanceList.contains(Globals.werUI))
			evaluationResultFileWriter.println("\t"+" Word error rate (WER) : " + wer);
		
		if (tempSelectedPerformanceList.contains(Globals.merUI))
			evaluationResultFileWriter.println("\t"+" Match error rate (MER) [0-1] : " + mer);
		
		if (tempSelectedPerformanceList.contains(Globals.wilUI))
			evaluationResultFileWriter.println("\t"+" Word Information Lost (WIL) [0-1] : " + wil);
		
		evaluationResultFileWriter.close();
	}

	public static void recogniseAndEvaluate(File speechDatabaseDirectory, ArrayList<UiAsrProperties> asrPropertiesObj, ArrayList<String> selectedPerformanceList , ArrayList<String> selectedAsrList, String algorithmSelected) throws Exception {
		
		if (! isEvalutionDirectoryOccured){
			if (Globals.RecogniseAndEvaluateResultDirectory.exists()){
				FileUtils.deleteDirectory(Globals.RecogniseAndEvaluateResultDirectory);
				Globals.RecogniseAndEvaluateResultDirectory.mkdirs();
			}
			else{
				Globals.RecogniseAndEvaluateResultDirectory.mkdirs();
			}
			isEvalutionDirectoryOccured = true;
		}
		
		if (Globals.recognitionOutputDirectory.exists()){
			FileUtils.deleteDirectory(Globals.recognitionOutputDirectory);
			Globals.recognitionOutputDirectory.mkdirs();
		}
		else{
			Globals.recognitionOutputDirectory.mkdirs();
		}
		
		if (selectedAsrList.contains(Globals.asr1Name))
			asr1Status = true;
		if (selectedAsrList.contains(Globals.asr2Name))
			asr2Status = true;
		
		speechDatabaseFolders = readDirectory(speechDatabaseDirectory);

		for (int i = 0; i < speechDatabaseFolders.size(); i++) {
			File currentSpeechFolder = speechDatabaseFolders.get(i);
			File originalReferenceFile = null;
			if (currentSpeechFolder.isDirectory() == true) {
				for (File each : currentSpeechFolder.listFiles()) {
					if (each.getName().compareTo(Globals.wavFolder) == 0) {
						currentSpeechFiles = each;
					}
					if (each.getName().compareTo(Globals.etcFolder) == 0) {
						referenceFilesList = readDirectory(each);
						for (File files : referenceFilesList) {
							if (files.getName().compareTo(Globals.referenceFileName) == 0) {
								originalReferenceFile = files;
							}
						}
					}
				}
			}
		
			if (asr1Status){
				
				cmuDictionaryFile = asrPropertiesObj.get(0).getUiDictionary();
				cmuAcousticString = "resource:/"+FilenameUtils.removeExtension(asrPropertiesObj.get(0).getUiAcoustic().getName());
				cmuLanguageFile = asrPropertiesObj.get(0).getUiLanguage();
				
				CmuSphinxEngine cmu = new CmuSphinxEngine(cmuDictionaryFile, cmuAcousticString, cmuLanguageFile);
				Configuration conf = cmu.configure();

				cmu.recognizeSpeech(conf, currentSpeechFolder, currentSpeechFiles, originalReferenceFile);
			}

			if (asr2Status){
				IspeechEngine ise = new IspeechEngine();
				ise.runFile(currentSpeechFolder, currentSpeechFiles, originalReferenceFile);
			}
		}
		recognitionOutputFolders = readDirectory(Globals.recognitionOutputDirectory);
		for (int i = 0; i < recognitionOutputFolders.size(); i++) {
			for (int j=0; j<selectedAsrList.size(); j++){
				if (selectedAsrList.get(j).contains(Globals.asr1Name)){
					hypothesisFileName = Globals.hypothesisOutputFileNameAsr1;
					timeTakenCount = Globals.hypothesisTimeFileNameAsr1;
					asrUsed = Globals.asr1Name;
				}
				else if (selectedAsrList.get(j).contains(Globals.asr2Name)){
					hypothesisFileName = Globals.hypothesisOutputFileNameAsr2;
					timeTakenCount = Globals.hypothesisTimeFileNameAsr2;
					asrUsed = Globals.asr2Name;
				}
				File subSpeechFolder = recognitionOutputFolders.get(i);
				if (subSpeechFolder.isDirectory() == true) {
					for (File each : subSpeechFolder.listFiles()) {
						if (each.getName().compareTo(Globals.referenceFileName) == 0){
							referenceFile = each;
						}
						else if (each.getName().compareTo(hypothesisFileName) == 0){
							hypothesisFile = each;
						}
						else if (each.getName().compareTo(timeTakenCount) == 0){
							timeTakenFile = each;
						}
					}
					
					if (algorithmSelected.equals(Globals.hsdiWeightsAlgorithm)){
						
					EvaluationAligner e = new EvaluationAligner(referenceFile , hypothesisFile , timeTakenFile);
					EvaluatorResult eResult = e.evaluateWithTime(subSpeechFolder , asrUsed);
					
					File evaluationResult = new File(Globals.RecogniseAndEvaluateResultDirectory, Globals.recogniseAndEvaluateResultFileName);
					
					writeResultFile(evaluationResult, selectedPerformanceList, eResult, hypothesisFileName, subSpeechFolder);
					}
				}
			}
			isEvalutionDirectoryOccured = false;
		}
		UiMethod1Frame.btnSaveResult.setEnabled(true);
	}

}