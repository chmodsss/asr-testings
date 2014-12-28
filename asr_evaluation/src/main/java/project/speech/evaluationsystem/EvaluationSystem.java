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
	private static double wer;
	private static double mar;
	@SuppressWarnings("unused")
	private static double recall;
	private static boolean evaluationResultFileOpened = false;
	private static boolean isEvalutionDirectoryOccured = false;
	private static boolean isTextEvaluationResultDirectoryOccured = false;
	private static boolean asr1Status = false;
	private static boolean asr2Status = false;

	//=================== Returns the list of files in the path ===================//
	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}


	//=================== Text evaluation ===================//
	
	public static void textEvaluation (File tempReferenceFile, File tempHypothesisFile , ArrayList<String> performanceList , String algorithmSelected) throws IOException {
	
		if (algorithmSelected.equals(Globals.algorithm1)){
		EvaluationAligner e1 = new EvaluationAligner(tempReferenceFile, tempHypothesisFile);
		EvaluatorResult eResult = e1.evaluateNoTime();
		
		if (!isTextEvaluationResultDirectoryOccured ){
			FileUtils.deleteDirectory(Globals.textEvaluationResultDirectory);
			Globals.textEvaluationResultDirectory.mkdirs();
			isTextEvaluationResultDirectoryOccured = true;
		}
		File textEvaluationResultFile = new File(Globals.textEvaluationResultDirectory, Globals.textEvaluationResultFileName);
			hypothesisFileName = tempHypothesisFile.getName();
			writeResultFile(textEvaluationResultFile, performanceList, eResult, hypothesisFileName);
			UiMethod2Frame.btnGetResult2.setEnabled(true);
		}
	}

	
	public static void writeResultFile (File fileToWriteResult , ArrayList<String> tempSelectedPerformanceList , EvaluatorResult eresult , String tempHypothesisFileName) throws IOException{
		
		System.out.println("Text evaluation initiated...");
		PrintWriter evaluationResultFileWriter = new PrintWriter(new FileWriter((fileToWriteResult),true));
		if (!evaluationResultFileOpened){
			evaluationResultFileWriter.print("\n  : : : : : : :  Evaluation Result  : : : : : : :  ");
			evaluationResultFileOpened = true;
		}
		wer = (eresult.getSubstitutions()+eresult.getDeletions()+eresult.getInsertions())/(float)eresult.getNumberOfWords();
		mar = (eresult.getHits()+eresult.getDeletions()+eresult.getInsertions()+eresult.getSubstitutions())/(float)eresult.getHits();
		recall = eresult.getHits()/(float)eresult.getNumberOfWords();
		evaluationResultFileWriter.print("\n\n << ------ "+FilenameUtils.removeExtension(tempHypothesisFileName)+"  ------ >> \n ");

		if (tempSelectedPerformanceList.contains(Globals.werUI))
			evaluationResultFileWriter.print("\t"+"WER : " + wer);
		if (tempSelectedPerformanceList.contains(Globals.serUI))
			evaluationResultFileWriter.print("\t"+"SER : " + 0);
		if (tempSelectedPerformanceList.contains(Globals.mucUI))
			evaluationResultFileWriter.print("\t"+"MUC : " +(1- mar));
		if (tempSelectedPerformanceList.contains(Globals.accUI))
			evaluationResultFileWriter.print("\t"+"ACC : " + mar);				
		
		evaluationResultFileWriter.close();
	}

	public static void recogniseAndEvaluate(File speechDatabaseDirectory, ArrayList<UiAsrProperties> asrPropertiesObj, ArrayList<String> selectedPerformanceList , ArrayList<String> selectedAsrList, String algorithmSelected) throws Exception {
		System.out.println("Recognise and evaluate initiated...");
		
		if (Globals.recognitionOutputDirectory.exists()){
			FileUtils.deleteDirectory(Globals.recognitionOutputDirectory);
		}
		
		if (selectedAsrList.contains(Globals.asr1Name))
			asr1Status = true;
		if (selectedAsrList.contains(Globals.asr2Name))
			asr2Status = true;

		
		// Setting up speech database for recognition
//		speechCorpora = speechDatabaseDirectory;
		
		speechDatabaseFolders = readDirectory(speechDatabaseDirectory);

		for (int i = 0; i < speechDatabaseFolders.size(); i++) {
			System.out.println("speech database size... "+speechDatabaseFolders.size());
			File currentSpeechFolder = speechDatabaseFolders.get(i);
			File originalReferenceFile = null;
			if (currentSpeechFolder.isDirectory() == true) {
				for (File each : currentSpeechFolder.listFiles()) {
					if (each.getName().compareTo(Globals.wavFolder) == 0) {
//						speechFiles = readDirectory(each);
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
				
				System.out.println("Cmu dict file... :"+ cmuDictionaryFile);
				System.out.println("Cmu Acous file... :"+ cmuAcousticString);
				System.out.println("Cmu Lang file... :"+ cmuLanguageFile);
				
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
				}
				else if (selectedAsrList.get(j).contains(Globals.asr2Name)){
					hypothesisFileName = Globals.hypothesisOutputFileNameAsr2;
					timeTakenCount = Globals.hypothesisTimeFileNameAsr2;
				}
				File currentFolder = recognitionOutputFolders.get(i);
				if (currentFolder.isDirectory() == true) {
					for (File each : currentFolder.listFiles()) {
						if (each.getName().compareTo(Globals.referenceFileName) == 0){
							System.out.println("found reference file...");
						referenceFile = each;
						}
						else if (each.getName().compareTo(hypothesisFileName) == 0){
						hypothesisFile = each;
						}
						else if (each.getName().compareTo(timeTakenCount) == 0){
						timeTakenFile = each;
						}
					}
					
					if (algorithmSelected.equals(Globals.algorithm1)){
						
					EvaluationAligner e = new EvaluationAligner(referenceFile , hypothesisFile , timeTakenFile);
					System.out.println("ref file :"+ referenceFile.getAbsolutePath());
					System.out.println("hyp file :"+ hypothesisFile.getAbsolutePath());
					System.out.println("time file :"+ timeTakenFile.getAbsolutePath());
					EvaluatorResult eResult = e.evaluateWithTime();
				
					if (!isEvalutionDirectoryOccured ){
						FileUtils.deleteDirectory(Globals.RecogniseAndEvaluateResultDirectory);
						Globals.RecogniseAndEvaluateResultDirectory.mkdirs();
						isEvalutionDirectoryOccured = true;
					}
					File evaluationResult = new File(Globals.RecogniseAndEvaluateResultDirectory, Globals.recogniseAndEvaluateResultFileName);
					
					writeResultFile(evaluationResult, selectedPerformanceList, eResult, hypothesisFileName);
					}
//					evalOutFile.print("\t"+"Recall : " + recall);
//					evalOutFile.print("\t"+"Timetaken : " + result.getTime()+"s");
					
				}
			}
			isEvalutionDirectoryOccured = false;
		}
		UiMethod1Frame.btnResult.setEnabled(true);
	}

}