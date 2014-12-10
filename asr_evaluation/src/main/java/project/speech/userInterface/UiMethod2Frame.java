package project.speech.userInterface;

import project.speech.evaluationsystem.*;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import java.util.ArrayList;


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
	public static JButton btnGetResult2;
	
	private static JLabel lblModel2;
	
	private static JPanel panelPerformance;
	private static JPanel panelFileChooser ;
	private static JPanel panelEvaluate;
	private static JPanel panelCriteria ;
	
	private static JCheckBox chkWER;
	private static JCheckBox chkSER;
	private static JCheckBox chkMUC;
	private static JCheckBox chkACC;
	private static JCheckBox chkALL;
	
	private static JFileChooser referenceFileChooser;
	private static JFileChooser hypothesisFileChooser;
	
	private static File referenceFilePath = null;
	private static File hypothesisFilePath = null;
	
	private static String referenceChooserTitle = "Select the reference file";
	private static String hypothesisChooserTitle = "Select the hypothesis file";
	private static String select = "--- Select ---";
	private static String algorithmSelected = null;
	private static String outputFilePath = "/comparisonOutput/comparison-result.txt";
	
	private static boolean referenceFileLoaded = false;
	private static boolean hypothesisFileLoaded = false;
	
	private static ArrayList<JCheckBox> performanceListChecked = new ArrayList<JCheckBox>();
	private static ArrayList<String> performanceListSelected = new ArrayList<String>();
	


	
	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void initialize(){
				
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 726, 479);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setTitle("Method 2 ...");
		frame2.setResizable(false);
		
		final UiInstructionFrame2 frameInstructions2 = new UiInstructionFrame2();
		frame2.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (frameInstructions2 != null) {
					frameInstructions2.dispose();
				}
			}
		});
		
		// Set UI to look more cool
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//=================== Panels ===================//

		
		// Panel to display performance measures
		panelPerformance = new JPanel();
		panelPerformance.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Performance measures", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPerformance.setBounds(48, 167, 170, 170);
		frame2.getContentPane().add(panelPerformance);
		panelPerformance.setLayout(null);
				
		// Panel to choose files
		panelFileChooser = new JPanel();
		panelFileChooser.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "File chooser", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFileChooser.setBounds(480, 92, 200, 262);
		frame2.getContentPane().add(panelFileChooser);
		panelFileChooser.setLayout(null);
				
		// Panel to select the criteria
		panelCriteria = new JPanel();
		panelCriteria.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Criteria", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelCriteria.setBounds(31, 92, 200, 262);
		frame2.getContentPane().add(panelCriteria);
		
		// Algorithm select button
		comboAlgorithm = new JComboBox();
		panelCriteria.add(comboAlgorithm);
		comboAlgorithm.setFont(new Font("SansSerif", Font.PLAIN, 14));
		comboAlgorithm.setModel(new DefaultComboBoxModel(new String[] {"--- Select ---", "Algorithm1"}));
		
		// Panel for evaluation
		panelEvaluate = new JPanel();
		panelEvaluate.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Evaluate", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelEvaluate.setBounds(260, 167, 190, 187);
		frame2.getContentPane().add(panelEvaluate);
		panelEvaluate.setLayout(null);

		//=================== Labels ===================//
		
		lblModel2 = new JLabel("Model - 2");
		lblModel2.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel2.setFont(new Font("Georgia", Font.ITALIC, 30));
		lblModel2.setBounds(151, 25, 384, 39);
		frame2.getContentPane().add(lblModel2);
		
		//=================== Buttons ===================//
		
		// Instruction button
		btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(285, 117, 135, 28);
		frame2.getContentPane().add(btnInstructions);
		
		// Hypothesis file selection
		btnHypFile = new JButton("Hypothesis File");
		btnHypFile.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnHypFile.setBounds(30, 169, 135, 28);
		panelFileChooser.add(btnHypFile);
		
		// Reference file selection
		btnRefFile = new JButton("Reference File");
		btnRefFile.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnRefFile.setBounds(30, 85, 135, 28);
		panelFileChooser.add(btnRefFile);
		
		// Check button
		btnCheck = new JButton("Check");
		btnCheck.setBounds(23, 40, 135, 28);
		panelEvaluate.add(btnCheck);
		btnCheck.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		// Evaluate button
		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setBounds(23, 93, 135, 28);
		panelEvaluate.add(btnEvaluate);
		btnEvaluate.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnEvaluate.setEnabled(false);
		
		// Get result button
		btnGetResult2 = new JButton("Get Result");
		btnGetResult2.setBounds(23, 133, 135, 28);
		panelEvaluate.add(btnGetResult2);
		btnGetResult2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnGetResult2.setEnabled(false);
		
		// Check boxes
		
		chkWER = new JCheckBox("WER");
		chkWER.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkWER.setBounds(21, 25, 130, 28);
		panelPerformance.add(chkWER);
		
		chkSER = new JCheckBox("SER");
		chkSER.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkSER.setBounds(21, 51, 130, 28);
		panelPerformance.add(chkSER);
		
		chkMUC = new JCheckBox("MUC");
		chkMUC.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkMUC.setBounds(21, 75, 130, 28);
		panelPerformance.add(chkMUC);
		
		chkACC = new JCheckBox("ACC");
		chkACC.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkACC.setBounds(21, 101, 130, 28);
		panelPerformance.add(chkACC);
		
		chkALL = new JCheckBox("ALL");
		chkALL.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chkALL.setBounds(21, 125, 130, 28);
		panelPerformance.add(chkALL);
		
		
		//=================== Action listener ===================//
		
		// Set false to evaluate when something changes
		comboAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEvaluate.setEnabled(false);
			}
		});
		
		// Check all - to switch off all other check boxes
		chkALL.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				btnEvaluate.setEnabled(false);
				if (e.ACTION_PERFORMED != 0 && (!chkALL.isSelected())) {
					System.out.println("action" + e.ACTION_PERFORMED);
					chkWER.setSelected(false);
					chkSER.setSelected(false);
					chkMUC.setSelected(false);
					chkACC.setSelected(false);
				}
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
				System.out.println("entry poinnt...");
				referenceFileChooser = new JFileChooser();
				referenceFileChooser.setCurrentDirectory(new java.io.File("."));
				referenceFileChooser.setDialogTitle(referenceChooserTitle);
				referenceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				referenceFileChooser.setAcceptAllFileFilterUsed(false);
				if (referenceFileChooser.showOpenDialog(frame2) == JFileChooser.APPROVE_OPTION) {
					btnEvaluate.setEnabled(false);
					referenceFilePath = referenceFileChooser.getSelectedFile();
					referenceFileLoaded = true;
					btnRefFile.setBackground(Color.GREEN);
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
					hypothesisFileLoaded = true;
					btnHypFile.setBackground(Color.GREEN);
				}
			}
		});
		
		// Check - to check the required conditions
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnEvaluate.setEnabled(false);
				// Store method of algorithm in String
				algorithmSelected = (String) comboAlgorithm.getSelectedItem();
				if (select.equals(algorithmSelected)) {
					algorithmSelected = null;
				}
				
				if (!performanceListSelected.isEmpty())
					performanceListSelected.clear();
				
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
			}
		});

		// Evaluate - send the options for evaluation
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						EvaluationSystem.evaluateFromFiles(referenceFilePath, hypothesisFilePath, performanceListSelected, algorithmSelected);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		// Get result - retrieve the output file
		btnGetResult2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JTextArea ta = new JTextArea(50, 100);
					File currentFolder = new java.io.File("");
					String currentPath = currentFolder.getAbsolutePath();
					outputFilePath = currentPath + outputFilePath;
					ta.read(new FileReader(outputFilePath), null);
					ta.setEditable(false);
					JOptionPane.showMessageDialog(btnGetResult2, new JScrollPane(ta));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			});
		
		
		//=================== Item listener ===================//
		
		chkALL.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				btnEvaluate.setEnabled(false);
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
				btnEvaluate.setEnabled(false);
				if (e.getStateChange() == 2) {
					chkALL.setSelected(false);
				}
			}
		});
		
		chkMUC.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			btnEvaluate.setEnabled(false);
			if (e.getStateChange() == 2) {
				chkALL.setSelected(false);
				}
			}
		});
		
		chkSER.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			btnEvaluate.setEnabled(false);
			if (e.getStateChange() == 2) {
				chkALL.setSelected(false);
				}
			}
		});
		
		chkWER.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			btnEvaluate.setEnabled(false);
			if (e.getStateChange() == 2) {
				chkALL.setSelected(false);
				}
			}
		});		
	}
}