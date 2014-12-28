package project.speech.globalAccess;

import java.io.File;

public class Globals {
	public static String select = "--- Select ---";
	public static String asr1Name = "cmusphinx";
	public static String asr2Name = "ispeech";
	
	public static String asr1SelectionNameUI = "CmuSphinx";
	public static String asr2SelectionNameUI = "iSpeech";
	public static String allselectionUI = "All";
	
	public static String algorithm1 = "HSDI weights";
	
	public static String werUI = "WER";
	public static String serUI = "SER";
	public static String mucUI = "MUC";
	public static String accUI = "ACC";
	public static String allUI = "ALL";
	
	public static String wavFolder = "wav";
	public static String etcFolder = "etc";
	
	public static File recognitionOutputDirectory = new File("asrOutput");
	public static File RecogniseAndEvaluateResultDirectory = new File("evaluationOutput");
	public static File textEvaluationResultDirectory = new File ("comparisonOutput");
	
	
	public static String referenceFileName = "prompts-original.txt";
	public static String hypothesisOutputFileNameAsr1 = asr1SelectionNameUI+"-output.txt";
	public static String hypothesisOutputFileNameAsr2 = asr2SelectionNameUI+"-output.txt";
	
	public static String hypothesisTimeFileNameAsr1 = asr1SelectionNameUI+"-time.txt";
	public static String hypothesisTimeFileNameAsr2 = asr2SelectionNameUI+"-time.txt";
	
	public static String textEvaluationResultFileName = "comparison-result.txt";
	public static String recogniseAndEvaluateResultFileName = "evaluation-result.txt";
	
}
