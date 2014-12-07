package project.speech.userInterface;

import project.speech.evaluationsystem.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.GroupLayout.Alignment;

import org.apache.commons.io.FilenameUtils;

public class UiWindow {

	// Buttons on the UI
	private static JFrame frame;

	private static JCheckBox chkWER;
	private static JCheckBox chkSER;
	private static JCheckBox chkMUC;
	private static JCheckBox chkACC;
	private static JCheckBox chkALL;

	private static JComboBox comboAsrSelect;
	private static JButton btnDictionaryModel;
	private static JButton btnLanguageModel;
	private static JButton btnAcousticModel;

	private static JButton btnCheck;
	private static JButton btnInstructions;
	private static JButton btnEvaluate;
	private static JComboBox comboAsrResult;
	private static JLabel btnShow;

	final static UiAsrProperties cmuProperties = new UiAsrProperties();
	final static UiAsrProperties iSpeechProperties = new UiAsrProperties();

	private static JFileChooser speechCorpusChooser;
	private static JFileChooser dictionaryChooser;
	private static JFileChooser acousticChooser;
	private static JFileChooser languageChooser;

	private static String speechCorpusChoosertitle = "Select the path of Speech corpus...";
	private static String dictionaryChoosertitle = "Select the dictionary file...";
	private static String acousticChoosertitle = "Select the acoustic file...";
	private static String languageChoosertitle = "Select the language file...";
	
	private static String asr1 = "cmusphinx";
	private static String asr2 = "ispeech";

	private static String currentAsrSelected = null;
	private static boolean speechCorpusLoaded = false;
	private static boolean modelsNeeded = false;
	private static boolean dictLoadedCmu = false;
	private static boolean acousLoadedCmu = false;
	private static boolean langLoadedCmu = false;
	private static boolean dictLoadedIspeech = false;
	private static boolean acousLoadedIspeech = false;
	private static boolean langLoadedIspeech = false;
	private static File speechCorpusPathResult = null;

	private static ArrayList<UiAsrProperties> speechPropertiesList = new ArrayList<UiAsrProperties>();
	private static ArrayList<JCheckBox> performanceListChecked = new ArrayList<JCheckBox>();
	private static ArrayList<String> performanceListSelected = new ArrayList<String>();
	private static ArrayList<String> asrSystemsSelected = new ArrayList<String>();

	private static String outputFilePath = "C:/Users/SIVASURYA/java_workspace/asr_evaluation/evalOutput/chumma.java";
	/*
	 * public UiMain() { this.initialize(); }
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static UiAsrProperties initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 695, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Asr evlauation...");
		final UiInstructionFrame frameInstructions = new UiInstructionFrame();
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (frameInstructions != null) {
					frameInstructions.dispose();
				}
			}
		});

		// Set UI to look more cool
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Instructions
		btnInstructions = new JButton("Instructions");
		btnInstructions.setBounds(250, 115, 160, 25);
		frame.getContentPane().add(btnInstructions);

		// Opening a new frame for instructions
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameInstructions.setVisible(true);
			}
		});

		// objects for storing the properties
		speechPropertiesList.add(cmuProperties);
		speechPropertiesList.add(iSpeechProperties);

		// Combo box to select properties
		comboAsrSelect = new JComboBox();
		comboAsrSelect.setModel(new DefaultComboBoxModel(new String[] {
				"--- Select ---", "CmuSphinx", "iSpeech" }));
		comboAsrSelect.setToolTipText("");
		comboAsrSelect.setBounds(460, 150, 160, 25);
		frame.getContentPane().add(comboAsrSelect);

		// Speech corpus path button
		final JButton btnSpeechCorpus = new JButton("Speech corpus");
		btnSpeechCorpus.setBounds(460, 100, 160, 25);
		frame.getContentPane().add(btnSpeechCorpus);

		// Dictionary model path button
		btnDictionaryModel = new JButton("Dictionary model");
		btnDictionaryModel.setBounds(460, 230, 160, 25);
		frame.getContentPane().add(btnDictionaryModel);
		btnDictionaryModel.setEnabled(false);

		// Language model path button
		btnLanguageModel = new JButton("Language model");
		btnLanguageModel.setBounds(460, 280, 160, 25);
		frame.getContentPane().add(btnLanguageModel);
		btnLanguageModel.setEnabled(false);

		// Acoustic model path button
		btnAcousticModel = new JButton("Acoustic model");
		btnAcousticModel.setBounds(460, 330, 160, 25);
		frame.getContentPane().add(btnAcousticModel);
		btnAcousticModel.setEnabled(false);

		// Evaluation button
		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Sending values to Evaluation system...");
					EvaluationSystem.executeEvaluation(speechCorpusPathResult,speechPropertiesList , performanceListSelected, asrSystemsSelected);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEvaluate.setEnabled(false);
		btnEvaluate.setBounds(250, 280, 160, 25);
		frame.getContentPane().add(btnEvaluate);

		// Select output speech engines
		comboAsrResult = new JComboBox();
		comboAsrResult.setModel(new DefaultComboBoxModel(new String[] {
				"--- Select ---", "CmuSphinx", "iSpeech", "All" }));
		comboAsrResult.setBounds(40, 150, 160, 25);
		frame.getContentPane().add(comboAsrResult);

		// Title
		JLabel lblAsrEvaluationTool = new JLabel("ASR evaluation tool");
		lblAsrEvaluationTool.setFont(new Font("Georgia", Font.ITALIC, 30));
		lblAsrEvaluationTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsrEvaluationTool.setBounds(55, 10, 516, 39);
		frame.getContentPane().add(lblAsrEvaluationTool);

		// Select models
		btnShow = new JLabel();
		btnShow.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
		btnShow.setBounds(450, 188, 194, 31);
		frame.getContentPane().add(btnShow);

		// check button to check whether all conditions are met
		btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				asrSystemsSelected.clear();
				performanceListSelected.clear();
				// Computing the selected asr systems
				Object resultAsrSelectedObj = comboAsrResult.getSelectedItem();
				if ("CmuSphinx".equals(resultAsrSelectedObj)) {
					asrSystemsSelected.add(asr1);
					System.out.println("CmuSphnix's result is required...");
				}
				if ("iSpeech".equals(resultAsrSelectedObj)) {
					asrSystemsSelected.add(asr2);
					System.out.println("ispeech''s result is required...");
				}
				if ("All".equals(resultAsrSelectedObj)) {
					asrSystemsSelected.add(asr1);
					asrSystemsSelected.add(asr2);
					System.out.println("All results are required...");
				}
				if ("--- Select ---".equals(resultAsrSelectedObj)) {
					asrSystemsSelected = null;
					System.out.println("Nothing is selected...");
				}
				
				// Strings for storing the performance measures
				performanceListChecked.clear();
				performanceListChecked.add(chkWER);
				performanceListChecked.add(chkSER);
				performanceListChecked.add(chkMUC);
				performanceListChecked.add(chkACC);
				

				// Computing the performance list
				if (chkALL.isSelected()) {
					for (int i = 0; i < performanceListChecked.size(); i++){
						performanceListSelected.add(performanceListChecked.get(i).getText());
						System.out.println("Selected ones All..."+performanceListChecked.get(i).getText());
					}
				} else {
					for (int j = 0; j < performanceListChecked.size(); j++) {
						JCheckBox each = performanceListChecked.get(j);
						if(each != null){
						if (each.isSelected()){
							performanceListSelected.add(each.getText());
							System.out.println("Selected ones..."+each.getText());
							}
						}
					}
				}
				
				boolean checkBool = dictLoadedCmu
						&& acousLoadedCmu
						&& langLoadedCmu
						&& speechCorpusLoaded
						&& (!performanceListSelected.isEmpty()) && (!asrSystemsSelected.isEmpty());
				if (checkBool) {
					btnEvaluate.setEnabled(true);
				}
			}
		});
		btnCheck.setBounds(250, 240, 160, 25);
		frame.getContentPane().add(btnCheck);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null), "Properties", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		panel.setBounds(440, 60, 200, 310);
		frame.getContentPane().add(panel);

		final JButton btnResult = new JButton("Get result");
		btnResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JTextArea ta = new JTextArea(50, 100);  
					ta.read(new FileReader(outputFilePath), null);
					ta.setEditable(false);  
					JOptionPane.showMessageDialog(btnResult, new JScrollPane(ta));
					}  
					catch (IOException ioe) {
					ioe.printStackTrace();  
					}  
			}
		});
		btnResult.setBounds(250, 320, 160, 25);
		frame.getContentPane().add(btnResult);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null), "Evaluation", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(230, 209, 200, 161);
		frame.getContentPane().add(panel_1);

		chkWER = new JCheckBox("WER");
		chkWER.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					chkALL.setSelected(false);
				}
			}
		});
		chkWER.setBounds(40, 231, 97, 23);
		frame.getContentPane().add(chkWER);

		chkSER = new JCheckBox("SER");
		chkSER.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					chkALL.setSelected(false);
				}
			}
		});
		chkSER.setBounds(40, 257, 97, 23);
		frame.getContentPane().add(chkSER);

		chkMUC = new JCheckBox("MUC");
		chkMUC.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					chkALL.setSelected(false);
				}
			}
		});
		chkMUC.setBounds(40, 281, 97, 23);
		frame.getContentPane().add(chkMUC);

		chkACC = new JCheckBox("ACC");
		chkACC.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					chkALL.setSelected(false);
				}
			}
		});
		chkACC.setBounds(40, 307, 97, 23);
		frame.getContentPane().add(chkACC);

		chkALL = new JCheckBox("ALL");
		chkALL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.ACTION_PERFORMED != 0 && (!chkALL.isSelected())) {
					System.out.println("action" + e.ACTION_PERFORMED);
					chkWER.setSelected(false);
					chkSER.setSelected(false);
					chkMUC.setSelected(false);
					chkACC.setSelected(false);
				}
			}
		});
		chkALL.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					chkWER.setSelected(true);
					chkSER.setSelected(true);
					chkMUC.setSelected(true);
					chkACC.setSelected(true);
				}
			}
		});
		chkALL.setBounds(40, 331, 97, 23);
		frame.getContentPane().add(chkALL);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "Performance measures",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(32, 209, 128, 161);
		frame.getContentPane().add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panel_3.setBounds(10, 88, 210, 298);
		frame.getContentPane().add(panel_3);

		// Combo box selection action
		comboAsrSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionAsrSelected) {
				JComboBox comboAsrSelected = (JComboBox) actionAsrSelected
						.getSource();
				Object asrSelectedObj = comboAsrSelected.getSelectedItem();
				if ("CmuSphinx".equals(asrSelectedObj)) {
					System.out.println("CmuSphnix is selected...");
					modelsNeeded = true;
					setSelectedAsr("cmusphinx");
					btnShow.setText("Set the properties of "
							+ currentAsrSelected);
					btnShow.setVisible(true);
					btnDictionaryModel.setEnabled(true);
					btnAcousticModel.setEnabled(true);
					btnLanguageModel.setEnabled(true);

					setDefaultColor();
					if (dictLoadedCmu)
						btnDictionaryModel.setBackground(Color.GREEN);
					if (acousLoadedCmu)
						btnAcousticModel.setBackground(Color.GREEN);
					if (langLoadedCmu)
						btnLanguageModel.setBackground(Color.GREEN);
				}

				if ("iSpeech".equals(asrSelectedObj)) {
					System.out.println("iSpeech is selected...");
					setSelectedAsr("ispeech");
					modelsNeeded = false;
					btnShow.setText("No models are needed for iSpeech ");
					btnShow.setVisible(true);

					setDefaultColor();
					if (dictLoadedIspeech)
						btnDictionaryModel.setBackground(Color.GREEN);
					if (acousLoadedIspeech)
						btnAcousticModel.setBackground(Color.GREEN);
					if (langLoadedIspeech)
						btnLanguageModel.setBackground(Color.GREEN);

					btnDictionaryModel.setEnabled(false);
					btnAcousticModel.setEnabled(false);
					btnLanguageModel.setEnabled(false);
				}
				if ("--- Select ---".equals(asrSelectedObj)) {
					System.out.println("Select is selected...");
					setSelectedAsr(null);
					btnShow.setText("Select anyone...");

					setDefaultColor();
					btnDictionaryModel.setEnabled(false);
					btnAcousticModel.setEnabled(false);
					btnLanguageModel.setEnabled(false);
				}
			}
		});

		// Speech corpus path selection
		btnSpeechCorpus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speechCorpusChooser = new JFileChooser();
				speechCorpusChooser.setCurrentDirectory(new java.io.File("."));
				speechCorpusChooser.setDialogTitle(speechCorpusChoosertitle);
				speechCorpusChooser
						.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				speechCorpusChooser.setAcceptAllFileFilterUsed(false);
				if (speechCorpusChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					speechCorpusPathResult = speechCorpusChooser
							.getSelectedFile();
					speechCorpusLoaded = true;
					btnSpeechCorpus.setBackground(Color.GREEN);
				}
			}
		});

		// Dictionary path selection
		btnDictionaryModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dictionaryPathResult = null;
				if (currentAsrSelected != null && modelsNeeded) {
					dictionaryChooser = new JFileChooser();
					dictionaryChooser
							.setCurrentDirectory(new java.io.File("."));
					dictionaryChooser.setDialogTitle(dictionaryChoosertitle);
					dictionaryChooser
							.setFileSelectionMode(JFileChooser.FILES_ONLY);
					dictionaryChooser.setAcceptAllFileFilterUsed(false);

					if (dictionaryChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
						dictionaryPathResult = getRelativePath(dictionaryChooser
								.getSelectedFile());
						System.out.println("dict path"
								+ getRelativePath(dictionaryPathResult));
						btnDictionaryModel.setBackground(Color.GREEN);
						dictLoadedCmu = true;
					}
					if (currentAsrSelected == "cmusphinx") {
						cmuProperties.setUiDictionary(dictionaryPathResult);
					}
					/*
					 * if(currentAsrSelected == "ispeech" ){
					 * iSpeechProperties.setUiDictionary(dictionaryPathResult);
					 * }
					 */
				}
			}
		});

		// Acoustic path selection
		btnAcousticModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File acousticPathResult = null;
				if (currentAsrSelected != null && modelsNeeded) {
					acousticChooser = new JFileChooser();
					acousticChooser.setCurrentDirectory(new java.io.File("."));
					acousticChooser.setDialogTitle(acousticChoosertitle);
					acousticChooser
							.setFileSelectionMode(JFileChooser.FILES_ONLY);
					acousticChooser.setAcceptAllFileFilterUsed(false);

					if (acousticChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
						acousticPathResult = getRelativePath(acousticChooser
								.getSelectedFile());
						System.out.println("acous path : "
								+ FilenameUtils
										.removeExtension(acousticPathResult
												.getName()));
						btnAcousticModel.setBackground(Color.GREEN);
						acousLoadedCmu = true;
					}
					if (currentAsrSelected == "cmusphinx") {
						cmuProperties.setUiAcoustic(acousticPathResult);
					}
					/*
					 * if(currentAsrSelected == "ispeech"){
					 * iSpeechProperties.setUiAcoustic(acousticPathResult); }
					 */
				}
			}
		});

		// Language path selection
		btnLanguageModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File languagePathResult = null;
				if (currentAsrSelected != null && modelsNeeded) {
					languageChooser = new JFileChooser();
					languageChooser.setCurrentDirectory(new java.io.File("."));
					languageChooser.setDialogTitle(languageChoosertitle);
					languageChooser
							.setFileSelectionMode(JFileChooser.FILES_ONLY);
					languageChooser.setAcceptAllFileFilterUsed(false);

					if (languageChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
						languagePathResult = getRelativePath(languageChooser
								.getSelectedFile());
						langLoadedCmu = true;
						btnLanguageModel.setBackground(Color.GREEN);
					}
					if (currentAsrSelected == "cmusphinx") {
						cmuProperties.setUiLanguage(languagePathResult);
					}
					/*
					 * if(currentAsrSelected == "ispeech"){
					 * iSpeechProperties.setUiLanguage(languagePathResult); }
					 */
				}
			}
		});
		return cmuProperties;
	}

	// Returns relative path
	public static File getRelativePath(File file) {
		String filePath = file.getAbsolutePath();
		File currentFolder = new java.io.File("");
		String folderPath = currentFolder.getAbsolutePath();
		System.out.println(filePath);
		System.out.println(folderPath);
		if (filePath.startsWith(folderPath)) {
			System.out.println(filePath);
			File returnFile = new java.io.File(filePath.substring(folderPath
					.length() + 1));
			return returnFile;
		} else
			return null;
	}

	// function to set default background colour to three models
	public static void setDefaultColor() {
		btnDictionaryModel.setBackground(null);
		btnAcousticModel.setBackground(null);
		btnLanguageModel.setBackground(null);
	}

	// function returning selected asr system
	public static void setSelectedAsr(String received) {
		currentAsrSelected = received;
	}

	// Main function
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// UiAsrProperties propertiesResult = initialize();
					// UiWindow newWindow = new UiWindow();
					UiWindow.initialize();
					UiWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}