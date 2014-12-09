package project.speech.userInterface;

import project.speech.evaluationsystem.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class UiMethod2Frame {

	static JFrame frame2;
	private JPanel contentPane;
	private static JButton btnRefFile;
	private static JButton btnHypFile;
	public static JButton btnGetResult2;
	private static JComboBox comboAlgorithm;
	
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
	
	private static boolean referenceFileLoaded = false;
	private static boolean hypothesisFileLoaded = false;
	private static JLabel lblModel2;
	
	private static ArrayList<JCheckBox> performanceListChecked = new ArrayList<JCheckBox>();
	private static ArrayList<String> performanceListSelected = new ArrayList<String>();
	private static JButton btnEvaluate;
	
	private static String outputFilePath = "/evalOutput/chumma.java";
	private static JPanel panel_1;
	private static JButton btnInstructions;

	
	static void initialize(){
				
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 726, 479);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		
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

		
		// Panel to display performance measures
		JPanel panelPerformance = new JPanel();
		panelPerformance.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Performance measures", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPerformance.setBounds(48, 167, 170, 170);
		frame2.getContentPane().add(panelPerformance);
		panelPerformance.setLayout(null);
		
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
		
		// Panel to choose files
		JPanel panelFileChooser = new JPanel();
		panelFileChooser.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "File chooser", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFileChooser.setBounds(480, 92, 200, 262);
		frame2.getContentPane().add(panelFileChooser);
		panelFileChooser.setLayout(null);
		
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
		
		lblModel2 = new JLabel("Model - 2");
		lblModel2.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel2.setFont(new Font("Georgia", Font.ITALIC, 30));
		lblModel2.setBounds(151, 25, 384, 39);
		frame2.getContentPane().add(lblModel2);
		
		comboAlgorithm = new JComboBox();
		comboAlgorithm.setFont(new Font("SansSerif", Font.PLAIN, 14));
		comboAlgorithm.setModel(new DefaultComboBoxModel(new String[] {"--- Select ---", "Algorithm1"}));
		comboAlgorithm.setBounds(58, 116, 135, 28);
		frame2.getContentPane().add(comboAlgorithm);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Choices", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(31, 92, 200, 262);
		frame2.getContentPane().add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Evaluate", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(260, 167, 190, 187);
		frame2.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		// Check all the conditions required
		JButton btnCheck = new JButton("Check");
		btnCheck.setBounds(23, 40, 135, 28);
		panel_1.add(btnCheck);
		btnCheck.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setBounds(23, 93, 135, 28);
		panel_1.add(btnEvaluate);
		btnEvaluate.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnEvaluate.setEnabled(false);
		
		// Get result button
		btnGetResult2 = new JButton("Get Result");
		btnGetResult2.setBounds(23, 133, 135, 28);
		panel_1.add(btnGetResult2);
		btnGetResult2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnGetResult2.setEnabled(false);
		
		btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameInstructions2.setVisible(true);
			}
		});
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(285, 117, 135, 28);
		frame2.getContentPane().add(btnInstructions);
		btnGetResult2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JTextArea ta = new JTextArea(50, 100);
					File currentFolder = new java.io.File("");
					String currentPath = currentFolder.getAbsolutePath();
					outputFilePath = currentPath + outputFilePath;
					System.out.println("output file path..." + outputFilePath);
					ta.read(new FileReader(outputFilePath), null);
					ta.setEditable(false);
					JOptionPane.showMessageDialog(btnGetResult2, new JScrollPane(ta));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			});
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						EvaluationSystem.evaluateFromFiles(referenceFilePath, hypothesisFilePath, performanceListSelected, algorithmSelected);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Store method of algorithm in String
				algorithmSelected = (String) comboAlgorithm.getSelectedItem();
				System.out.println("alog one.." + algorithmSelected);
				if (select.equals(algorithmSelected)) {
					algorithmSelected = null;
					if (algorithmSelected == null){
						System.out.println("It works...");
					}
					System.out.println("alog.." + algorithmSelected);
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
				if (referenceFileLoaded && hypothesisFileLoaded && (!performanceListSelected.isEmpty() && (algorithmSelected != null))){
					System.out.println("Conditions satisfied...");
					btnEvaluate.setEnabled(true);
				}
			}
		});
		btnRefFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("entry poinnt...");
				referenceFileChooser = new JFileChooser();
				referenceFileChooser.setCurrentDirectory(new java.io.File("."));
				referenceFileChooser.setDialogTitle(referenceChooserTitle);
				referenceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				referenceFileChooser.setAcceptAllFileFilterUsed(false);
				if (referenceFileChooser.showOpenDialog(frame2) == JFileChooser.APPROVE_OPTION) {
					referenceFilePath = referenceFileChooser.getSelectedFile();
					referenceFileLoaded = true;
					btnRefFile.setBackground(Color.GREEN);
			}
			}
		});
		btnHypFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hypothesisFileChooser = new JFileChooser();
				hypothesisFileChooser.setCurrentDirectory(new java.io.File("."));
				hypothesisFileChooser.setDialogTitle(hypothesisChooserTitle);
				hypothesisFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				hypothesisFileChooser.setAcceptAllFileFilterUsed(false);
				if (hypothesisFileChooser.showOpenDialog(frame2) == JFileChooser.APPROVE_OPTION) {
					hypothesisFilePath = hypothesisFileChooser.getSelectedFile();
					hypothesisFileLoaded = true;
					btnHypFile.setBackground(Color.GREEN);
				}
			}
		});
		frame2.setTitle("Method 2...");
	}
}
