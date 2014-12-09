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

public class UiMethod1Frame {

	// Buttons on the UI
	static JFrame frame1;

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
	private static JComboBox comboAlgorithm;
	public static JButton btnResult;

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

	private static String select = "--- Select ---";
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
	private static String algorithmSelected = null;

	private static String outputFilePath = "/evalOutput/chumma.java";
	private static JLabel lblModel1;

	/*
	 * public UiMain() { this.initialize(); }
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void initialize() {

		frame1 = new JFrame();
		frame1.setBounds(100, 100, 695, 450);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		frame1.setTitle("Asr evlauation...");
		final UiInstructionFrame1 frameInstructions1 = new UiInstructionFrame1();
		frame1.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (frameInstructions1 != null) {
					frameInstructions1.dispose();
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
		
				JPanel panelProperties = new JPanel();
				panelProperties.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Properties", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				panelProperties.setBounds(440, 60, 200, 310);
				frame1.getContentPane().add(panelProperties);
				panelProperties.setLayout(null);
				
				// Speech corpus path button
				final JButton btnSpeechCorpus = new JButton("Speech corpus");
				btnSpeechCorpus.setFont(new Font("SansSerif", Font.PLAIN, 14));
				btnSpeechCorpus.setBounds(30, 40, 135, 28);
				panelProperties.add(btnSpeechCorpus);
				
				// Combo box to select properties
				comboAsrSelect = new JComboBox();
				comboAsrSelect.setFont(new Font("SansSerif", Font.PLAIN, 14));
				comboAsrSelect.setBounds(30, 87, 135, 28);
				panelProperties.add(comboAsrSelect);
				comboAsrSelect.setModel(new DefaultComboBoxModel(new String[] { select, "CmuSphinx", "iSpeech" }));
				comboAsrSelect.setToolTipText("");
				
				// Select models
				btnShow = new JLabel();
				btnShow.setBounds(6, 128, 194, 31);
				panelProperties.add(btnShow);
				btnShow.setFont(new Font("Dialog", Font.PLAIN, 12));
				
				// Dictionary model path button
				btnDictionaryModel = new JButton("Dictionary model");
				btnDictionaryModel.setFont(new Font("SansSerif", Font.PLAIN, 14));
				btnDictionaryModel.setBounds(28, 160, 138, 28);
				panelProperties.add(btnDictionaryModel);
				btnDictionaryModel.setEnabled(false);
				
				// Language model path button
				btnLanguageModel = new JButton("Language model");
				btnLanguageModel.setFont(new Font("SansSerif", Font.PLAIN, 14));
				btnLanguageModel.setBounds(28, 210, 138, 28);
				panelProperties.add(btnLanguageModel);
				btnLanguageModel.setEnabled(false);
				
				// Acoustic model path button
				btnAcousticModel = new JButton("Acoustic model");
				btnAcousticModel.setFont(new Font("SansSerif", Font.PLAIN, 14));
				btnAcousticModel.setBounds(28, 260, 138, 28);
				panelProperties.add(btnAcousticModel);
				btnAcousticModel.setEnabled(false);
				
				// Acoustic path selection
				btnAcousticModel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						File acousticPathResult = null;
						if (currentAsrSelected != null && modelsNeeded) {
							acousticChooser = new JFileChooser();
							acousticChooser.setCurrentDirectory(new java.io.File("."));
				acousticChooser.setDialogTitle(acousticChoosertitle);
				acousticChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				acousticChooser.setAcceptAllFileFilterUsed(false);
				
				if (acousticChooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
					acousticPathResult = getRelativePath(acousticChooser.getSelectedFile());
					System.out.println("acous path : " + FilenameUtils.removeExtension(acousticPathResult.getName()));
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
				languageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				languageChooser.setAcceptAllFileFilterUsed(false);
				
				if (languageChooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
					languagePathResult = getRelativePath(languageChooser.getSelectedFile());
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
				
				// Dictionary path selection
				btnDictionaryModel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						File dictionaryPathResult = null;
						if (currentAsrSelected != null && modelsNeeded) {
							dictionaryChooser = new JFileChooser();
							dictionaryChooser.setCurrentDirectory(new java.io.File("."));
						dictionaryChooser.setDialogTitle(dictionaryChoosertitle);
						dictionaryChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						dictionaryChooser.setAcceptAllFileFilterUsed(false);
				
						if (dictionaryChooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
							dictionaryPathResult = getRelativePath(dictionaryChooser.getSelectedFile());
							System.out.println("dict path" + getRelativePath(dictionaryPathResult));
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
				
					// Combo box selection action
					comboAsrSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent actionAsrSelected) {
							JComboBox comboAsrSelected = (JComboBox) actionAsrSelected.getSource();
							Object asrSelectedObj = comboAsrSelected.getSelectedItem();
							if ("CmuSphinx".equals(asrSelectedObj)) {
								System.out.println("CmuSphnix is selected...");
								modelsNeeded = true;
								setSelectedAsr("cmusphinx");
								btnShow.setText("Set the properties of " + currentAsrSelected);
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
				
							if (select.equals(asrSelectedObj)) {
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
									speechCorpusChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
									speechCorpusChooser.setAcceptAllFileFilterUsed(false);
									if (speechCorpusChooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
										speechCorpusPathResult = speechCorpusChooser.getSelectedFile();
										speechCorpusLoaded = true;
										btnSpeechCorpus.setBackground(Color.GREEN);
									}
								}
							});

		// Instructions
		btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(250, 115, 135, 28);
		frame1.getContentPane().add(btnInstructions);

		// Opening a new frame for instructions
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameInstructions1.setVisible(true);
			}
		});

		// objects for storing the properties
		speechPropertiesList.add(cmuProperties);
		speechPropertiesList.add(iSpeechProperties);

		JPanel panelEvaluation = new JPanel();
		panelEvaluation.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Evaluation", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelEvaluation.setBounds(230, 209, 200, 161);
		frame1.getContentPane().add(panelEvaluation);
		panelEvaluation.setLayout(null);
		
				// check button to check whether all conditions are met
				btnCheck = new JButton("Check");
				btnCheck.setFont(new Font("SansSerif", Font.PLAIN, 14));
				btnCheck.setBounds(20, 31, 135, 28);
				panelEvaluation.add(btnCheck);
				
						// Evaluation button
						btnEvaluate = new JButton("Evaluate");
						btnEvaluate.setFont(new Font("SansSerif", Font.PLAIN, 14));
						btnEvaluate.setBounds(20, 69, 135, 28);
						panelEvaluation.add(btnEvaluate);
						btnEvaluate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									System.out.println("Sending values to Evaluation system...");
									EvaluationSystem.recogniseAndEvaluate(speechCorpusPathResult, speechPropertiesList, performanceListSelected, asrSystemsSelected, algorithmSelected);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
						btnEvaluate.setEnabled(false);
						
								btnResult = new JButton("Get result");
								btnResult.setFont(new Font("SansSerif", Font.PLAIN, 14));
								btnResult.setBounds(20, 111, 135, 28);
								panelEvaluation.add(btnResult);
								btnResult.setEnabled(false);
								btnResult.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										try {
											JTextArea ta = new JTextArea(50, 100);
											File currentFolder = new java.io.File("");
											String currentPath = currentFolder.getAbsolutePath();
											outputFilePath = currentPath + outputFilePath;
											System.out.println("output file path..." + outputFilePath);
											ta.read(new FileReader(outputFilePath), null);
											ta.setEditable(false);
											JOptionPane.showMessageDialog(btnResult, new JScrollPane(ta));
										} catch (IOException ioe) {
											ioe.printStackTrace();
										}
									}
								});
				btnCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!asrSystemsSelected.isEmpty())
							asrSystemsSelected.clear();
						if (!performanceListSelected.isEmpty())
							performanceListSelected.clear();

						// Computing the selected asr systems
						Object resultAsrSelectedObj = comboAsrResult.getSelectedItem();
						System.out.println("contents of asr systems selected.." + resultAsrSelectedObj);
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
						if (select.equals(resultAsrSelectedObj)) {
							asrSystemsSelected.clear();
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
							for (int i = 0; i < performanceListChecked.size(); i++) {
								performanceListSelected.add(performanceListChecked.get(i).getText());
								System.out.println("Selected ones All..." + performanceListChecked.get(i).getText());
							}
						} else {
							for (int j = 0; j < performanceListChecked.size(); j++) {
								JCheckBox each = performanceListChecked.get(j);
								if (each != null) {
									if (each.isSelected()) {
										performanceListSelected.add(each.getText());
										System.out.println("Selected ones..." + each.getText());
									}
								}
							}
						}

						// Store method of algorithm in String
						algorithmSelected = (String) comboAlgorithm.getSelectedItem();
						System.out.println("alog one.." + algorithmSelected);
						if (select.equals(algorithmSelected)) {
							algorithmSelected = null;
							System.out.println("alog.." + algorithmSelected);
						}

						boolean checkBool = dictLoadedCmu && acousLoadedCmu && langLoadedCmu && speechCorpusLoaded && (algorithmSelected != null) 
								&& (!performanceListSelected.isEmpty()) && (!asrSystemsSelected.isEmpty());

						if (checkBool) {
							btnEvaluate.setEnabled(true);
						}
					}
				});

		JPanel panelResultChoice = new JPanel();
		panelResultChoice.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Criteria", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelResultChoice.setBounds(18, 60, 200, 310);
		frame1.getContentPane().add(panelResultChoice);
		panelResultChoice.setLayout(null);
		
				// Select output speech engines
				comboAsrResult = new JComboBox();
				comboAsrResult.setFont(new Font("SansSerif", Font.PLAIN, 14));
				comboAsrResult.setBounds(30, 51, 135, 28);
				panelResultChoice.add(comboAsrResult);
				comboAsrResult.setModel(new DefaultComboBoxModel(new String[] { select, "CmuSphinx", "iSpeech", "All" }));
				
						// Combo box algorithm selection
						comboAlgorithm = new JComboBox();
						comboAlgorithm.setFont(new Font("SansSerif", Font.PLAIN, 14));
						comboAlgorithm.setBounds(30, 100, 135, 28);
						panelResultChoice.add(comboAlgorithm);
						comboAlgorithm.setModel(new DefaultComboBoxModel(new String[] {"--- Select ---", "Algorithm1"}));
						
								JPanel panelPerformance = new JPanel();
								panelPerformance.setBounds(18, 143, 163, 161);
								panelResultChoice.add(panelPerformance);
								panelPerformance.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Performance measures", TitledBorder.CENTER, TitledBorder.TOP, null, null));
								panelPerformance.setLayout(null);
								
										chkWER = new JCheckBox("WER");
										chkWER.setFont(new Font("SansSerif", Font.PLAIN, 14));
										chkWER.setBounds(16, 27, 130, 28);
										panelPerformance.add(chkWER);
										
												chkSER = new JCheckBox("SER");
												chkSER.setFont(new Font("SansSerif", Font.PLAIN, 14));
												chkSER.setBounds(16, 53, 130, 28);
												panelPerformance.add(chkSER);
												
														chkMUC = new JCheckBox("MUC");
														chkMUC.setFont(new Font("SansSerif", Font.PLAIN, 14));
														chkMUC.setBounds(16, 77, 130, 28);
														panelPerformance.add(chkMUC);
														
																chkACC = new JCheckBox("ACC");
																chkACC.setFont(new Font("SansSerif", Font.PLAIN, 14));
																chkACC.setBounds(16, 103, 130, 28);
																panelPerformance.add(chkACC);
																
																		chkALL = new JCheckBox("ALL");
																		chkALL.setFont(new Font("SansSerif", Font.PLAIN, 14));
																		chkALL.setBounds(16, 127, 130, 28);
																		panelPerformance.add(chkALL);
																		
																		lblModel1 = new JLabel("Model - 1");
																		lblModel1.setHorizontalAlignment(SwingConstants.CENTER);
																		lblModel1.setFont(new Font("Georgia", Font.ITALIC, 30));
																		lblModel1.setBounds(118, 9, 384, 39);
																		frame1.getContentPane().add(lblModel1);
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
																chkACC.addItemListener(new ItemListener() {
																	public void itemStateChanged(ItemEvent e) {
																		if (e.getStateChange() == 2) {
																			chkALL.setSelected(false);
																		}
																	}
																});
														chkMUC.addItemListener(new ItemListener() {
															public void itemStateChanged(ItemEvent e) {
																if (e.getStateChange() == 2) {
																	chkALL.setSelected(false);
																}
															}
														});
												chkSER.addItemListener(new ItemListener() {
													public void itemStateChanged(ItemEvent e) {
														if (e.getStateChange() == 2) {
															chkALL.setSelected(false);
														}
													}
												});
										chkWER.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												if (e.getStateChange() == 2) {
													chkALL.setSelected(false);
												}
											}
										});
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
			File returnFile = new java.io.File(filePath.substring(folderPath.length() + 1));
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
	/*
	 * // Main function public static void main(String[] args) {
	 * EventQueue.invokeLater(new Runnable() { public void run() { try { //
	 * UiAsrProperties propertiesResult = initialize(); // UiWindow newWindow =
	 * new UiWindow(); UiWindow.initialize(); UiWindow.frame.setVisible(true); }
	 * catch (Exception e) { e.printStackTrace(); } } }); }
	 */
}