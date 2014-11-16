package project.speech.evaluationsystem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.cmu.sphinx.api.Configuration;
import project.speech.asrengines.*;
import project.speech.evaluator.Evaluator;
import project.speech.evaluator.EvaluatorResult;

public class EvaluationSystem {

	private static File speechCorpora = new File("resource/speechCorpora");
	private static File asrOutput = new File("asrOutput");
	private static ArrayList<File> speechDatabase = new ArrayList<File>();
	private static ArrayList<File> outputDatabase = new ArrayList<File>();
	// private static ArrayList<File> speechFiles = new ArrayList<File>();
	private static ArrayList<File> referenceFiles = new ArrayList<File>();
	private static File speechFile;
	private static File refEvaluationFile;
	private static File hypEvaluationFile;

	public static ArrayList<File> readDirectory(File currentPath) {
		ArrayList<File> folders = new ArrayList<File>();
		for (File eachPath : currentPath.listFiles()) {
			folders.add(eachPath);
		}
		return folders;
	}

	public static void main(String[] args) throws IOException {

		CmuSphinxEngine cmu = new CmuSphinxEngine();
		Configuration conf = cmu.configure();

		speechDatabase = readDirectory(speechCorpora);

		for (int i = 0; i < speechDatabase.size(); i++) {
			File currentDatabase = speechDatabase.get(i);
			File promptOriginal = null;
			if (currentDatabase.isDirectory() == true) {
				for (File each : currentDatabase.listFiles()) {
					if (each.getName().compareTo("wav") == 0) {
						// speechFiles = readDirectory(each);
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
	//		cmu.recognizeSpeech(conf, currentDatabase, speechFile,promptOriginal);
		}
		outputDatabase = readDirectory(asrOutput);
		for (int i = 0; i < outputDatabase.size(); i++) {
			File currentFolder = outputDatabase.get(i);
			if (currentFolder.isDirectory() == true) {
				for (File each : currentFolder.listFiles()) {
					if (each.getName().compareTo("prompt-original.txt") == 0){
					hypEvaluationFile = each;
					}
					else if (each.getName().compareTo("speech-output.txt") == 0){
					refEvaluationFile = each;
					}
				}
				Evaluator e = new Evaluator(refEvaluationFile , hypEvaluationFile); 
				EvaluatorResult result = e.evaluate();
				File evaluationResult = new File(currentFolder, "evaluation-result.txt");
				PrintWriter outFile = new PrintWriter(evaluationResult, "UTF-8");
				outFile.println("Hits : " + result.getHits());
				outFile.println("Insertions : " + result.getInsertions());
				outFile.println("Deletions : " + result.getDeletions());
				outFile.println("Substitutions : " + result.getSubstitutions());
				outFile.close();
		}
	}
}
}