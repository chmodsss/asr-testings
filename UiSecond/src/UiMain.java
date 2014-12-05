import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.SystemColor;
import java.io.File;

import javax.swing.GroupLayout.Alignment;

public class UiMain {
	private JFrame frame;
	private JFileChooser speechCorpusChooser;
	private JFileChooser dictionaryChooser;
	private JFileChooser acousticChooser;
	private JFileChooser languageChooser;
	private String speechCorpusChoosertitle = "Select the path of Speech corpus...";
	private String dictionaryChoosertitle = "Select the dictionary file...";
	private String acousticChoosertitle = "Select the acoustic file...";
	private String languageChoosertitle = "Select the language file...";
	private JComboBox comboAsrSelect;
	private JButton btnDictionaryModel;
	private JButton btnLanguageModel;
	private JButton btnAcousticModel;
	private String currentAsrSelected = null;
	private boolean modelsNeeded = false;
	private boolean dictLoadedLedCmu = false;
	private boolean acousLoadedLedCmu = false;
	private boolean langLoadedLedCmu = false;

	public UiMain() {
		this.initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Set UI to look more cool
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}

		// objects for storing the properties
		final UiAsrProperties cmuProperties = new UiAsrProperties();
		final UiAsrProperties iSpeechProperties = new UiAsrProperties();
		
		// Combo box to select properties
		comboAsrSelect = new JComboBox();
		comboAsrSelect.setModel(new DefaultComboBoxModel(new String[] {"--- Select ---", "CmuSphinx", "iSpeech"}));
		comboAsrSelect.setToolTipText("");
		comboAsrSelect.setBounds(425, 120, 140, 39);
		frame.getContentPane().add(comboAsrSelect);
		
		// Speech corpus path button
		final JButton btnSpeechCorpus = new JButton("Speech corpus");
		btnSpeechCorpus.setBounds(220, 164, 141, 61);
		frame.getContentPane().add(btnSpeechCorpus);

		// Dictionary model path button
		btnDictionaryModel = new JButton("Dictionary model");
		btnDictionaryModel.setBounds(410, 230, 161, 25);
		frame.getContentPane().add(btnDictionaryModel);

		// Language model path button
		btnLanguageModel = new JButton("Language model");
		btnLanguageModel.setBounds(410, 280, 161, 25);
		frame.getContentPane().add(btnLanguageModel);

		// Acoustic model path button
		btnAcousticModel = new JButton("Acoustic model");
		btnAcousticModel.setBounds(410, 330, 161, 25);
		frame.getContentPane().add(btnAcousticModel);

		// Evaluation button
		JButton btnEvaluate = new JButton("Evaluate");
//		btnEvaluate.setEnabled(false);
		btnEvaluate.setBackground(Color.BLUE);
		btnEvaluate.setBounds(215, 282, 161, 61);
		frame.getContentPane().add(btnEvaluate);

		// Select output speech engines
		JComboBox comboAsrResult = new JComboBox();
		comboAsrResult.setModel(new DefaultComboBoxModel(new String[] {
				"--- Select ---", "CmuSphinx", "iSpeech", "All" }));
		comboAsrResult.setBounds(50, 150, 127, 31);
		frame.getContentPane().add(comboAsrResult);

		// Select output performance measures
		JComboBox comboPerformance = new JComboBox();
		comboPerformance.setModel(new DefaultComboBoxModel(new String[] {
				"--- Select ---", "WER", "SER", "MUC", "Acc", "ALL" }));
		comboPerformance.setBounds(50, 300, 127, 31);
		frame.getContentPane().add(comboPerformance);

		// Result panel
		JPanel panelResult = new JPanel();
		panelResult.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null), "Result Choices",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59,
						59)));
		panelResult.setBounds(35, 60, 155, 312);
		frame.getContentPane().add(panelResult);

		// Title
		JLabel lblAsrEvaluationTool = new JLabel("ASR evaluation tool");
		lblAsrEvaluationTool.setFont(new Font("Georgia", Font.ITALIC, 30));
		lblAsrEvaluationTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsrEvaluationTool.setBounds(55, 10, 516, 39);
		frame.getContentPane().add(lblAsrEvaluationTool);

		// Instructions
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setBounds(230, 115, 120, 27);
		frame.getContentPane().add(btnInstructions);
		
		// Select models
		final JLabel btnShow = new JLabel();
		btnShow.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
		btnShow.setBounds(400, 164, 194, 31);
		frame.getContentPane().add(btnShow);
		
		// led for loaded dictionary indication
		final JLabel ledDict = new JLabel("•");
		ledDict.setFont(new Font("DejaVu Sans", Font.PLAIN, 60));
		ledDict.setForeground(Color.GREEN);
		ledDict.setBounds(565, 230, 35, 28);
		frame.getContentPane().add(ledDict);
		ledDict.setVisible(false);

		// led for loaded language model indication
		final JLabel ledLang = new JLabel("•");
		ledLang.setForeground(Color.GREEN);
		ledLang.setFont(new Font("DejaVu Sans", Font.PLAIN, 60));
		ledLang.setBounds(565, 280, 35, 28);
		frame.getContentPane().add(ledLang);
		ledLang.setVisible(false);
		
		// led for loaded acoustic model indication
		final JLabel ledAcoustic = new JLabel("•");
		ledAcoustic.setForeground(Color.GREEN);
		ledAcoustic.setFont(new Font("DejaVu Sans", Font.PLAIN, 60));
		ledAcoustic.setBounds(565, 330, 35, 28);
		frame.getContentPane().add(ledAcoustic);
		ledAcoustic.setVisible(false);
		
		// Combo box selection action
		comboAsrSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionAsrSelected) {
				JComboBox comboAsrSelected = (JComboBox)actionAsrSelected.getSource();
				Object asrSelectedObj = comboAsrSelected.getSelectedItem();
				if ("CmuSphinx".equals(asrSelectedObj)){
					System.out.println("CmuSphnix is selected...");
					modelsNeeded = true;
					setSelectedAsr("cmusphinx");
					btnShow.setText("Set the properties of " + currentAsrSelected);
					btnShow.setVisible(true);
					if (dictLoadedLedCmu)
					ledDict.setVisible(true);
					if (acousLoadedLedCmu)
					ledAcoustic.setVisible(true);
					if (langLoadedLedCmu)
					ledLang.setVisible(true);
				}
				if ("iSpeech".equals(asrSelectedObj)){
					System.out.println("iSpeech is selected...");
					setSelectedAsr("ispeech");
					modelsNeeded = false;
					btnShow.setText("No models are needed for iSpeech ");
					btnShow.setVisible(true);
					ledDict.setVisible(false);
					ledAcoustic.setVisible(false);
					ledLang.setVisible(false);
				}
				if ("--- Select ---".equals(asrSelectedObj)){
					System.out.println("Select is selected...");
					setSelectedAsr(null);
					btnShow.setText("Select anyone...");
				}
			}
		});
		
		// Speech corpus path selection
		btnSpeechCorpus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File speechCorpusPathResult = null;
				speechCorpusChooser = new JFileChooser();
				speechCorpusChooser.setCurrentDirectory(new java.io.File("."));
				speechCorpusChooser.setDialogTitle(speechCorpusChoosertitle);
				speechCorpusChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				speechCorpusChooser.setAcceptAllFileFilterUsed(false);
				if (speechCorpusChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					speechCorpusPathResult = speechCorpusChooser.getSelectedFile();
				}
			}
		});
		
		// Dictionary path selection
		btnDictionaryModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dictionaryPathResult = null;
				if (currentAsrSelected != null && modelsNeeded){
				dictionaryChooser = new JFileChooser();
				dictionaryChooser.setCurrentDirectory(new java.io.File("."));
				dictionaryChooser.setDialogTitle(dictionaryChoosertitle);
				dictionaryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dictionaryChooser.setAcceptAllFileFilterUsed(false);
				
				if (dictionaryChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					dictionaryPathResult = dictionaryChooser.getSelectedFile();
					btnDictionaryModel.setBackground(Color.GREEN);
					dictLoadedLedCmu = true;
					ledDict.setVisible(true);
				}
				if(currentAsrSelected == "cmusphinx"){
					cmuProperties.setUiDictionary(dictionaryPathResult);
				}
/*				if(currentAsrSelected == "ispeech" ){
					iSpeechProperties.setUiDictionary(dictionaryPathResult);
				}*/
			}
		}
		});
	
		// Acoustic path selection
		btnAcousticModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File acousticPathResult = null;
				if(currentAsrSelected != null && modelsNeeded){
				acousticChooser = new JFileChooser();
				acousticChooser.setCurrentDirectory(new java.io.File("."));
				acousticChooser.setDialogTitle(acousticChoosertitle);
				acousticChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				acousticChooser.setAcceptAllFileFilterUsed(false);
				
				if (acousticChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					acousticPathResult = acousticChooser.getSelectedFile();
					acousLoadedLedCmu = true;
					ledAcoustic.setVisible(true);
				}
				if(currentAsrSelected == "cmusphinx"){
					cmuProperties.setUiAcoustic(acousticPathResult);
				}
/*				if(currentAsrSelected == "ispeech"){
					iSpeechProperties.setUiAcoustic(acousticPathResult);
				}*/
			}
		}
		});
	
		// Language path selection
		btnLanguageModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File languagePathResult = null;
				if(currentAsrSelected != null && modelsNeeded){
				languageChooser = new JFileChooser();
				languageChooser.setCurrentDirectory(new java.io.File("."));
				languageChooser.setDialogTitle(languageChoosertitle);
				languageChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				languageChooser.setAcceptAllFileFilterUsed(false);
				
				if (languageChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					languagePathResult = languageChooser.getSelectedFile();
					langLoadedLedCmu = true;
					ledLang.setVisible(true);
				}
				if(currentAsrSelected == "cmusphinx"){
					cmuProperties.setUiLanguage(languagePathResult);
				}
/*				if(currentAsrSelected == "ispeech"){
					iSpeechProperties.setUiLanguage(languagePathResult);
				}*/
			}
			}
		});
	}
	
	
	// function returning selected asr system
	public void setSelectedAsr (String received){
		currentAsrSelected = received;		
	}
	
	// Main function
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiMain cmuWindow = new UiMain();
					cmuWindow.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}