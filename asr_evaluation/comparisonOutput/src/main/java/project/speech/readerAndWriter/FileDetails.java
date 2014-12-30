package project.speech.readerAndWriter;

import java.util.ArrayList;

public class FileDetails {
private ArrayList<String> filePath;
private ArrayList<String> fileNameExtension;

	public FileDetails(ArrayList<String> FilePath, ArrayList<String> FileNameExtension ){
		this.filePath = FilePath;
		this.fileNameExtension = FileNameExtension;
	}

	public ArrayList<String> getFilePath() {
		return filePath;
	}

	public ArrayList<String> getFileNameExtension() {
		return fileNameExtension;
	}
}