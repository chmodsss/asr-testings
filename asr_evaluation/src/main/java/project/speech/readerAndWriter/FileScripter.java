package project.speech.readerAndWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

public class FileScripter {
	public static void writer(String asrName, File databaseName, File referenceFile, String fileName ,String sentenceDetected) throws IOException {
		File createFolder = new File("asrOutput",databaseName.getName());
		createFolder.mkdirs();
		
		File promptOriginal = new File(createFolder, "prompt-original.txt");
		FileUtils.copyFile(referenceFile, promptOriginal);
		
		File createFile = new File(createFolder, asrName+"-output.txt");
		PrintWriter asrOutFile = new PrintWriter(new FileWriter((createFile),true));
		asrOutFile.print(fileName + " ");
		asrOutFile.println(sentenceDetected);
		asrOutFile.close();
	}
}
