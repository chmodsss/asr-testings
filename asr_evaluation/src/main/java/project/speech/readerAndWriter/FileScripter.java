package project.speech.readerAndWriter;

import project.speech.globalAccess.Globals;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileScripter {
	public static void writer(String asrName, File databaseName, File referenceFile, ArrayList<String> fileNameList ,ArrayList<String> sentenceDetectedList, double timeDifference) throws IOException {
		File createFolder = new File(Globals.recognitionOutputDirectory,databaseName.getName());
		createFolder.mkdirs();
		System.out.println("Entered writer directory");
		
		File promptOriginal = new File(createFolder, Globals.referenceFileName);
		System.out.println("prompt origitnal created");
		try{
			FileUtils.copyFile(referenceFile, promptOriginal);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("copy complete");
		File createAsrFile = new File(createFolder,  asrName+"-output");

		System.out.println("create asr file");
		PrintWriter asrOutFile = new PrintWriter(new FileWriter((createAsrFile),true));
		System.out.println("asr out file created");
		System.out.println("Going to enter loop");
		for (int i=0 ; i<fileNameList.size() ; i++){
		asrOutFile.print(fileNameList.get(i) + " ");
		asrOutFile.println(sentenceDetectedList.get(i));
		System.out.println("something detectd : "+sentenceDetectedList.get(i));
		}
		asrOutFile.close();
		
		File createTimeFile = new File(createFolder, asrName+"-time");
		PrintWriter timeOutFile = new PrintWriter(createTimeFile);
		timeOutFile.print(timeDifference);
		timeOutFile.close();
	}
}