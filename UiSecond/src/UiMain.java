import java.awt.EventQueue;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.SystemColor;
import java.io.File;

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

	public UiMain() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Combo box to select properties
		comboAsrSelect = new JComboBox();
		comboAsrSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionAsrSelected) {
				System.out.println(actionAsrSelected.getActionCommand());
			}
		});
		comboAsrSelect.setModel(new DefaultComboBoxModel(new String[] {
				"--- Select ---", "CmuSphinx", "iSpeech" }));
		comboAsrSelect.setToolTipText("");
		comboAsrSelect.setBounds(425, 98, 127, 61);
		frame.getContentPane().add(comboAsrSelect);

		// Speech corpus path button
		JButton btnSpeechCorpus = new JButton("Speech corpus");
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
		
		JButton button = new JButton("Select models");
		button.setBounds(430, 180, 116, 27);
		frame.getContentPane().add(button);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Properties", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(390, 60, 210, 326);
		frame.getContentPane().add(panel);

		// Speech corpus path selection
		btnSpeechCorpus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File speechCorpusPathResult;
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
				File dictionaryPathResult;
				dictionaryChooser = new JFileChooser();
				dictionaryChooser.setCurrentDirectory(new java.io.File("."));
				dictionaryChooser.setDialogTitle(dictionaryChoosertitle);
				dictionaryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dictionaryChooser.setAcceptAllFileFilterUsed(false);
				
				if (dictionaryChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					dictionaryPathResult = dictionaryChooser.getSelectedFile();
				}
			}
		});
	
		// Acoustic path selection
		btnAcousticModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File acousticPathResult;
				acousticChooser = new JFileChooser();
				acousticChooser.setCurrentDirectory(new java.io.File("."));
				acousticChooser.setDialogTitle(acousticChoosertitle);
				acousticChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				acousticChooser.setAcceptAllFileFilterUsed(false);
				
				if (acousticChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					acousticPathResult = acousticChooser.getSelectedFile();
				}
			}
		});
	
		// Language path selection
		btnLanguageModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File languagePathResult;
				languageChooser = new JFileChooser();
				languageChooser.setCurrentDirectory(new java.io.File("."));
				languageChooser.setDialogTitle(languageChoosertitle);
				languageChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				languageChooser.setAcceptAllFileFilterUsed(false);
				
				if (languageChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					languagePathResult = languageChooser.getSelectedFile();
				}
			}
		});
	}
	
	// Main function
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiMain window = new UiMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}