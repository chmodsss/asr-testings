package project.speech.readerAndWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FileReader {
	private static ArrayList<String> filePath = new ArrayList<String>();
	private static ArrayList<String> fileNameExtension = new ArrayList<String>();
	
	public FileDetails reader(File speeches) throws IOException{
		filePath.clear();
		fileNameExtension.clear();
		for (File eachSpeech : speeches.listFiles()) {
			filePath.add(eachSpeech.getAbsolutePath());
			fileNameExtension.add(eachSpeech.getName());
		}
		Collections.sort(filePath);
		Collections.sort(fileNameExtension);
		FileDetails fd = new FileDetails(filePath, fileNameExtension);
		return fd;
	}
}
