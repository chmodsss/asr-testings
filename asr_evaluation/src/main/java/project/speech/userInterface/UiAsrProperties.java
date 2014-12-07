package project.speech.userInterface;
import java.io.File;

public class UiAsrProperties {
	private File uiSpeechCorpus;
	private File uiDictionary;
	private File uiAcoustic;
	private File uiLanguage;
	
	// Speech corpus get and set methods
	public File getUiSpeechCorpus() {
		return uiSpeechCorpus;
	}
	public void setUiSpeechCorpus(File uiSpeechCorpus) {
		this.uiSpeechCorpus = uiSpeechCorpus;
	}
	
	// dictionary get and set methods
	public File getUiDictionary() {
		return uiDictionary;
	}
	public void setUiDictionary(File uiDictionary) {
		this.uiDictionary = uiDictionary;
	}
	
	// Acoustic get and set methods
	public File getUiAcoustic() {
		return uiAcoustic;
	}
	public void setUiAcoustic(File uiAcoustic) {
		this.uiAcoustic = uiAcoustic;
	}
	
	// Language get and set methods
	public File getUiLanguage() {
		return uiLanguage;
	}
	public void setUiLanguage(File uiLanguage) {
		this.uiLanguage = uiLanguage;
	}
}
