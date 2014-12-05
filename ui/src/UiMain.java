import java.awt.EventQueue;


import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.SystemColor;

public class UiMain {
	private JFrame frame;
	private JFileChooser chooser;
	private String choosertitle;
	private JComboBox asrSelectComboBox;
	private JButton btnDictionaryModel;
	private JButton btnLanguageModel;
	private JButton btnAcousticModel;
	
	public UiMain(){
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(369, 56, 203, 328);
		frame.getContentPane().add(panel);
		
		asrSelectComboBox = new JComboBox();
		asrSelectComboBox.setModel(new DefaultComboBoxModel(new String[] {"--- Select ---", "Cmusphinx", "iSpeech"}));
		asrSelectComboBox.setToolTipText("");
		asrSelectComboBox.setBounds(70, 91, 127, 61);
		frame.getContentPane().add(asrSelectComboBox);
		
		JButton speechCorpus = new JButton("Speech corpus");
		speechCorpus.setBounds(201, -9, 184, 86);
		frame.getContentPane().add(speechCorpus);
		speechCorpus.setForeground(new Color(255, 51, 0));
		speechCorpus.setBackground(SystemColor.window);
		
		btnDictionaryModel = new JButton("Dictionary model");
		btnDictionaryModel.setBounds(70, 220, 161, 25);
		frame.getContentPane().add(btnDictionaryModel);
		
		btnLanguageModel = new JButton("Language model");
		btnLanguageModel.setBounds(70, 260, 161, 25);
		frame.getContentPane().add(btnLanguageModel);
		
		btnAcousticModel = new JButton("Acoustic model");
		btnAcousticModel.setBounds(70, 300, 161, 25);
		frame.getContentPane().add(btnAcousticModel);
		dictionaryModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    int result;
		        
			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 
			      System.out.println("getCurrentDirectory(): " 
			         +  chooser.getCurrentDirectory());
			      System.out.println("getSelectedFile() : " 
			         +  chooser.getSelectedFile());
			      }
			    else {
			      System.out.println("No Selection ");
			      }
			}
		});
	}

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
