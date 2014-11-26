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

public class EvaluationSystem {

	private static File speechCorpora = new File("resource/speechCorpora");
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
	private static String asrName;
	private static String timeSpan;
	private static boolean fileOccured = false;
	private static boolean directoryOccured = false;

	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}

	public static void main(String[] args) throws Exception {

		if (asrOutput.exists()){
			FileUtils.deleteDirectory(asrOutput);
		}
		
		CmuSphinxEngine cmu = new CmuSphinxEngine();
		IspeechEngine ise = new IspeechEngine();
		Configuration conf = cmu.configure();
		speechDatabase = readDirectory(speechCorpora);

		for (int i = 0; i < speechDatabase.size(); i++) {
			System.out.println("speech database size... "+speechDatabase.size());
			File currentDatabase = speechDatabase.get(i);
			File promptOriginal = null;
			if (currentDatabase.isDirectory() == true) {
				for (File each : currentDatabase.listFiles()) {
					if (each.getName().compareTo("wav") == 0) {
						// speechFiles = readDirectory(each);
						speechFile = each;
						System.out.println("speech file paths... "+speechFile.getAbsolutePath());
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

			cmu.recognizeSpeech(conf, currentDatabase, speechFile, promptOriginal);
			ise.runFile(currentDatabase, speechFile, promptOriginal);
			
		}
		outputDatabase = readDirectory(asrOutput);
		for (int i = 0; i < outputDatabase.size(); i++) {
			for (int j=0; j<2; j++){
				if (j==1){
					asrName = "CmuSphinx-output.txt";
					timeSpan = "CmuSphinx-time.txt";
				}
				else if (j==0){
					asrName = "iSpeech-output.txt";
					timeSpan = "iSpeech-time.txt";
				}
				File currentFolder = outputDatabase.get(i);
				if (currentFolder.isDirectory() == true) {
					for (File each : currentFolder.listFiles()) {
						if (each.getName().compareTo("prompt-original.txt") == 0){
						hypEvaluationFile = each;
						}
						else if (each.getName().compareTo(asrName) == 0){
						refEvaluationFile = each;
						}
						else if (each.getName().compareTo(timeSpan) == 0){
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
					evalOutFile.print("\n"+FilenameUtils.removeExtension(asrName));
					evalOutFile.print("\t \t"+"Hits : " + result.getHits());
					evalOutFile.print("\t"+"Insertions : " + result.getInsertions());
					evalOutFile.print("\t"+"Deletions : " + result.getDeletions());
					evalOutFile.print("\t"+"Substitutions : " + result.getSubstitutions());
					evalOutFile.print("\t"+"Timetaken : " + result.getTime()+"s");
					evalOutFile.close();
				}
			}
			fileOccured = false;
		}
	}
}