package project.speech.userInterface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


public class UiMainFrame extends JFrame {

	static JFrame frameMain;
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JButton btnModel1;
	private static JButton btnModel2;
	private static JButton btnInstructions;
	private static JLabel lblAsrTool;
	
	// Main function
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiMainFrame frameMain = new UiMainFrame();
					frameMain.setVisible(true);
					frameMain.setTitle("ASR evaluation tool");
					frameMain.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public UiMainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UiMainFrame.class.getResource("/project/speech/userInterface/logo.jpg")));
		frameMain = new JFrame();
		frameMain.setBounds(100, 100, 695, 450);
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);

		
		// Set UI to look more cool
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//*************************** Buttons ***************************//
		
		// Model1 button
		btnModel1 = new JButton("Recognise & Evaluate");
		btnModel1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnModel1.setBounds(70, 224, 180, 50);
		contentPane.add(btnModel1);
		
		// Model2 button
		btnModel2 = new JButton("Text evaluation");
		btnModel2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnModel2.setBounds(323, 224, 180, 50);
		contentPane.add(btnModel2);
		
		// Instruction button
		btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(191, 143, 180, 50);
		contentPane.add(btnInstructions);

		//*************************** Labels ***************************//
		
		// Asr label
		lblAsrTool = new JLabel("ASR evaluation tool");
		lblAsrTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsrTool.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblAsrTool.setBounds(119, 48, 384, 39);
		contentPane.add(lblAsrTool);
		
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon(UiMainFrame.class.getResource("/project/speech/userInterface/logo_reduced.jpg")));
		lblLogo.setBounds(52, 48, 95, 50);
		contentPane.add(lblLogo);

		
		//********************** Action listeners **********************//
		
		// Model1 action
		btnModel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiMethod1Frame.initialize();
				UiMethod1Frame.frame1.setVisible(true);
			}
		});
		
		// Model2 action
		btnModel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiMethod2Frame.initialize();
				UiMethod2Frame.frame2.setVisible(true);
			}
		});

		// Instruction action
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiInstructionMainFrame frameInstructionMain = new UiInstructionMainFrame();
				frameInstructionMain.setTitle("Instructions...");
				frameInstructionMain.setVisible(true);
				frameInstructionMain.setResizable(false);
			}
		});
		
	}
}