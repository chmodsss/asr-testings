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
		
		File promptOriginal = new File(createFolder, Globals.referenceFileName);
		FileUtils.copyFile(referenceFile, promptOriginal);
		File createAsrFile = new File(createFolder,  asrName+"-output");

		PrintWriter asrOutFile = new PrintWriter(new FileWriter((createAsrFile),true));
		for (int i=0 ; i<fileNameList.size() ; i++){
		asrOutFile.print(fileNameList.get(i) + " ");
		asrOutFile.println(sentenceDetectedList.get(i));
		}
		asrOutFile.close();
		
		File createTimeFile = new File(createFolder, asrName+"-time");
		PrintWriter timeOutFile = new PrintWriter(createTimeFile);
		timeOutFile.print(timeDifference);
		timeOutFile.close();
	}
}