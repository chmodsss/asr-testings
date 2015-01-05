package project.speech.userInterface;

import project.speech.globalAccess.Globals;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.awt.Toolkit;


public class UiMethod2Frame {

	static JFrame frame2;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboAlgorithm;
	
	private static JButton btnInstructions;
	private static JButton btnRefFile;
	private static JButton btnHypFile;
	private static JButton btnCheck;
	private static JButton btnEvaluate;
	public static JButton btnSaveResult2;
	
	private static JLabel lblModel2;
	
	private static JPanel panelPerformance;
	private static JPanel panelFileChooser ;
	private static JPanel panelEvaluate;
	private static JPanel panelCriteria ;
	
	private static JCheckBox chkPercentHits;
	private static JCheckBox chkPercentSubs;
	private static JCheckBox chkPercentDel;
	private static JCheckBox chkPercentIns;
	private static JCheckBox chkMER;
	private static JCheckBox chkWIL;
	private static JCheckBox chkWER;
	private static JCheckBox chkALL;
	
	private static JFileChooser referenceFileChooser;
	private static JFileChooser hypothesisFileChooser;
	
	private static File referenceFilePath = null;
	private static File hypothesisFilePath = null;
	
	private static String referenceChooserTitle = "Select the reference file";
	private static String hypothesisChooserTitle = "Select the hypothesis file";
	private static String algorithmSelected = null;
	
	private static boolean referenceFileLoaded = false;
	private static boolean hypothesisFileLoaded = false;
	
	private static ArrayList<JCheckBox> performanceListChecked = new ArrayList<JCheckBox>();
	private static ArrayList<String> performanceListSelected = new ArrayList<String>();
	private static JLabel lblAlgorithm;
	private static JLabel lblReferenceFile;
	private static JLabel lblHypothesisFile;
	


	
	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void initialize(){
				
		frame2 = new JFrame();
		frame2.setIconImage(Toolkit.getDefaultToolkit().getImage(UiMethod2Frame.class.getResource("/project/speech/userInterface/logo.jpg")));
		frame2.setBounds(100, 100, 722, 402);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setTitle("Text evaluation");
		frame2.setResizable(false);
		
		final UiInstructionFrame2 frameInstructions2 = new UiInstructionFrame2();
		frame2.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				UiMainFrame framePrimary1 = new UiMainFrame();
				framePrimary1.setVisible(true);
				if (frameInstructions2 != null) {
					frameInstructions2.dispose();
				}
			}
		});
		
		// Set UI to look more cool
		try {
			UIManager.setLookAndFeel(Globals.theme1);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// Panel to choose files
		panelFileChooser = new JPanel();
		panelFileChooser.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "File chooser", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFileChooser.setBounds(499, 92, 190, 262);
		frame2.getContentPane().add(panelFileChooser);
		panelFileChooser.setLayout(null);
				
		// Panel to select the criteria
		panelCriteria = new JPanel();
		panelCriteria.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Output Choices", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelCriteria.setBounds(31, 92, 258, 262);
		frame2.getContentPane().add(panelCriteria);
		panelCriteria.setLayout(null);
		
		// Algorithm select button
		comboAlgorithm = new JComboBox();
		comboAlgorithm.setBounds(91, 37, 112, 25);
		panelCriteria.add(comboAlgorithm);
		comboAlgorithm.setFont(new Font("SansSerif", Font.PLAIN, 14));
		comboAlgorithm.setModel(new DefaultComboBoxModel(new String[] {Globals.select, Globals.hsdiWeightsAlgorithm}));
		
		//=================== Panels ===================//

		
		// Panel to display performance measures
		panelPerformance = new JPanel();
		panelPerformance.setBounds(20, 80, 218, 171);
		panelCriteria.add(panelPerformance);
		panelPerformance.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Performance measures", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPerformance.setLayout(null);
		
		// Check boxes
		
		chkPercentHits = new JCheckBox(Globals.hitsPercentUI);
		chkPercentHits.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkPercentHits.setBounds(20, 30, 75, 28);
		panelPerformance.add(chkPercentHits);
		
		chkPercentSubs = new JCheckBox(Globals.subsPercentUI);
		chkPercentSubs.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkPercentSubs.setBounds(20, 60, 75, 28);
		panelPerformance.add(chkPercentSubs);
		
		chkPercentDel = new JCheckBox(Globals.delPercentUI);
		chkPercentDel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkPercentDel.setBounds(20, 90, 75, 28);
		panelPerformance.add(chkPercentDel);
		
		chkPercentIns = new JCheckBox(Globals.insPercentUI);
		chkPercentIns.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkPercentIns.setBounds(20, 120, 75, 28);
		panelPerformance.add(chkPercentIns);
		
		chkWER = new JCheckBox(Globals.werUI);
		chkWER.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkWER.setBounds(115, 30, 75, 28);
		panelPerformance.add(chkWER);
		
		chkMER = new JCheckBox(Globals.merUI);
		chkMER.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkMER.setBounds(115, 60, 75, 28);
		panelPerformance.add(chkMER);
		
		chkWIL = new JCheckBox(Globals.wilUI);
		chkWIL.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkWIL.setBounds(115, 90, 75, 28);
		panelPerformance.add(chkWIL);
		
		//All the parameters
		chkALL = new JCheckBox(Globals.allUI);
		chkALL.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkALL.setBounds(115, 120, 75, 28);
		panelPerformance.add(chkALL);
		
		lblAlgorithm = new JLabel("Algorithm :");
		lblAlgorithm.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblAlgorithm.setBounds(14, 40, 94, 19);
		panelCriteria.add(lblAlgorithm);
		
		chkALL.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				btnEvaluate.setEnabled(false);
				if (e.ACTION_PERFORMED != 0 && (!chkALL.isSelected())) {
					System.out.println("action" + e.ACTION_PERFORMED);
					chkPercentHits.setSelected(false);
					chkPercentSubs.setSelected(false);
					chkPercentDel.setSelected(false);
					chkPercentIns.setSelected(false);
					chkMER.setSelected(false);
					chkWIL.setSelected(false);
					chkWER.setSelected(false);
				}
			}
		});
					
		//=================== Item listener ===================//
		
		chkALL.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				btnEvaluate.setEnabled(false);
				if (e.getStateChange() == 1) {
					chkPercentHits.setSelected(true);
					chkPercentSubs.setSelected(true);
					chkPercentDel.setSelected(true);
					chkPercentIns.setSelected(true);
					chkMER.setSelected(true);
					chkWIL.setSelected(true);
					chkWER.setSelected(true);				}
			}
		});
		
		chkPercentHits.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});
		
		chkPercentDel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});
		
		chkPercentSubs.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});
		
		chkPercentIns.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});

		chkWER.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});
		
		chkMER.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});	
		
		chkWIL.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
				setChkAllFalse(e);
			}
		});			
		
		// Panel for evaluation
		panelEvaluate = new JPanel();
		panelEvaluate.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Evaluate", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelEvaluate.setBounds(299, 167, 190, 187);
		frame2.getContentPane().add(panelEvaluate);
		panelEvaluate.setLayout(null);

		//=================== Labels ===================//
		
		lblModel2 = new JLabel("Performance calculator");
		lblModel2.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel2.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblModel2.setBounds(151, 25, 384, 39);
		frame2.getContentPane().add(lblModel2);
		
		//=================== Buttons ===================//
		
		// Instruction button
		btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(327, 125, 135, 28);
		frame2.getContentPane().add(btnInstructions);
		
		// Hypothesis file selection
		btnHypFile = new JButton("Hypothesis File");
		btnHypFile.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnHypFile.setBounds(30, 152, 135, 28);
		panelFileChooser.add(btnHypFile);
		
		// Reference file selection
		btnRefFile = new JButton("Reference File");
		btnRefFile.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnRefFile.setBounds(30, 60, 135, 28);
		panelFileChooser.add(btnRefFile);
		
		lblReferenceFile = new JLabel();
		lblReferenceFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblReferenceFile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblReferenceFile.setBounds(30, 99, 135, 20);
		panelFileChooser.add(lblReferenceFile);
		
		lblHypothesisFile = new JLabel();
		lblHypothesisFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblHypothesisFile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblHypothesisFile.setBounds(30, 191, 135, 20);
		panelFileChooser.add(lblHypothesisFile);
		
		// Check button
		btnCheck = new JButton("Check");
		btnCheck.setBounds(23, 40, 135, 28);
		panelEvaluate.add(btnCheck);
		btnCheck.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		// Evaluate button
		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setBounds(23, 88, 135, 28);
		panelEvaluate.add(btnEvaluate);
		btnEvaluate.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnEvaluate.setEnabled(false);
		
		// Get result button
		btnSaveResult2 = new JButton("Save Result");
		btnSaveResult2.setBounds(23, 133, 135, 28);
		panelEvaluate.add(btnSaveResult2);
		btnSaveResult2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSaveResult2.setEnabled(false);
		
		
		//=================== Action listener ===================//
		
		// Set false to evaluate when something changes
		comboAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEvaluate.setEnabled(false);
			}
		});
		
																	
		
		// Instruction - to open the instruction frame
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameInstructions2.setVisible(true);
				frameInstructions2.setTitle("Instructions...");
				frameInstructions2.setResizable(false);
			}
		});
		
		// Reference file - to choose the file
		btnRefFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				referenceFileChooser = new JFileChooser();
				referenceFileChooser.setCurrentDirectory(new java.io.File("."));
				referenceFileChooser.setDialogTitle(referenceChooserTitle);
				referenceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				referenceFileChooser.setAcceptAllFileFilterUsed(false);
				if (referenceFileChooser.showOpenDialog(frame2) == JFileChooser.APPROVE_OPTION) {
					btnEvaluate.setEnabled(false);
					referenceFilePath = referenceFileChooser.getSelectedFile();
					lblReferenceFile.setText(referenceFilePath.getName());
					btnRefFile.setToolTipText(referenceFilePath.getAbsolutePath());
					referenceFileLoaded = true;
					btnRefFile.setBackground(Globals.turquoise);
			}
			}
		});
		
		// Hypothesis file - to choose the file		
		btnHypFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hypothesisFileChooser = new JFileChooser();
				hypothesisFileChooser.setCurrentDirectory(new java.io.File("."));
				hypothesisFileChooser.setDialogTitle(hypothesisChooserTitle);
				hypothesisFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				hypothesisFileChooser.setAcceptAllFileFilterUsed(false);
				if (hypothesisFileChooser.showOpenDialog(frame2) == JFileChooser.APPROVE_OPTION) {
					btnEvaluate.setEnabled(false);
					hypothesisFilePath = hypothesisFileChooser.getSelectedFile();
					lblHypothesisFile.setText(hypothesisFilePath.getName());
					btnHypFile.setToolTipText(hypothesisFilePath.getAbsolutePath());
					hypothesisFileLoaded = true;
					btnHypFile.setBackground(Globals.turquoise);
				}
			}
		});
		
		// Check - to check the required conditions
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnEvaluate.setEnabled(false);
				// Store method of algorithm in String
				algorithmSelected = (String) comboAlgorithm.getSelectedItem();
				if (Globals.select.equals(algorithmSelected)) {
					algorithmSelected = null;
				}
				
				if (!performanceListSelected.isEmpty())
					performanceListSelected.clear();
				
				//--- Strings for storing the performance measures ---
				performanceListChecked.clear();
				performanceListChecked.add(chkPercentHits);
				performanceListChecked.add(chkPercentSubs);
				performanceListChecked.add(chkPercentDel);
				performanceListChecked.add(chkPercentIns);
				performanceListChecked.add(chkWER);
				performanceListChecked.add(chkMER);
				performanceListChecked.add(chkWIL);

				// Computing the performance list
				if (chkALL.isSelected()) {
					for (int i = 0; i < performanceListChecked.size(); i++) {
						performanceListSelected.add(performanceListChecked.get(i).getText());
					}
				} else {
					for (int j = 0; j < performanceListChecked.size(); j++) {
						JCheckBox each = performanceListChecked.get(j);
						if (each != null) {
							if (each.isSelected()) {
								performanceListSelected.add(each.getText());
							}
						}
					}
				}
				if (referenceFileLoaded && hypothesisFileLoaded && (!performanceListSelected.isEmpty() && (algorithmSelected != null))){
					btnEvaluate.setEnabled(true);
				}
				 if(!btnEvaluate.isEnabled())
				 {
					 JOptionPane.showMessageDialog(frame2, "One or more selections are missing !", "Incomplete data", JOptionPane.INFORMATION_MESSAGE);
				 }
			}
		});

		// Evaluate - send the options for evaluation
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							try {
								new UiSplashScreenEvaluationFrame(referenceFilePath, hypothesisFilePath, performanceListSelected, algorithmSelected);
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
			}
		});
		
		// Get result - retrieve the output file
		btnSaveResult2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Executed to save");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showSaveDialog(frame2);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File currentFolder = new java.io.File("");
					String currentPath = currentFolder.getAbsolutePath();
					String newPath;
					newPath = currentPath + Globals.model2CompleteOutputFilePath;
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    File fileToCopy = new File(newPath);
				    try {
						FileUtils.copyFile(fileToCopy, fileToSave);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			});
	}
	//=========== Resets check button and evaluate button ===========//
	public static void setChkAllFalse(ItemEvent e){
		btnEvaluate.setEnabled(false);
		if (e.getStateChange() == 2) {
			chkALL.setSelected(false);
		}
	}
}
