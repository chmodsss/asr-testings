package project.speech.globalAccess;

import java.awt.Color;
import java.io.File;

import javax.swing.UIManager;

public class Globals {
	
	public static String theme1 = UIManager.getSystemLookAndFeelClassName();
	public static String theme2 = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public static String theme3 = "javax.swing.plaf.metal.MetalLookAndFeel";
	
	public static Color turquoise = new Color(0, 162, 232, 255);
	
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
	
	
	public static String referenceFileName = "prompts-original";
	public static String hypothesisOutputFileNameAsr1 = asr1SelectionNameUI+"-output.txt";
	public static String hypothesisOutputFileNameAsr2 = asr2SelectionNameUI+"-output.txt";
	
	public static String hypothesisTimeFileNameAsr1 = asr1SelectionNameUI+"-time.txt";
	public static String hypothesisTimeFileNameAsr2 = asr2SelectionNameUI+"-time.txt";
	
	public static String textEvaluationResultFileName = "comparison-result.txt";
	public static String recogniseAndEvaluateResultFileName = "evaluation-result.txt";
	
	public static String model1OutputFilePath = "/"+RecogniseAndEvaluateResultDirectory+"/"+recogniseAndEvaluateResultFileName;
	public static String model2OutputFilePath = "/"+textEvaluationResultDirectory+"/"+textEvaluationResultFileName;
	
}
