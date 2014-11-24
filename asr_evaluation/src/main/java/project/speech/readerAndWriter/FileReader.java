package project.speech.readerAndWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FileReader {
	private static ArrayList<String> filePath = new ArrayList<String>();
	private static ArrayList<String> fileNameExtension = new ArrayList<String>();
	
	public static FileDetails reader(File speeches) throws IOException{
		for (File eachSpeech : speeches.listFiles()) {
			filePath.add(eachSpeech.getPath());
			fileNameExtension.add(eachSpeech.getName());
		}
		Collections.sort(filePath);
		Collections.sort(fileNameExtension);
		FileDetails fd = new FileDetails(filePath, fileNameExtension);
		return fd;
	}
}
