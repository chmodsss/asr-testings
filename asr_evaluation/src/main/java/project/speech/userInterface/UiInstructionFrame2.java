package project.speech.userInterface;

import javax.swing.*;
import java.awt.Font;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class UiInstructionFrame2 extends JFrame {

	private JPanel contentPane;
	private JPanel panelInstructions;
	private JLabel lblSteps;
	private JPanel panelTranscript;
	private JLabel lblTranscript;


	public UiInstructionFrame2() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 774, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=================== Panels ===================//
		
		panelInstructions = new JPanel();
		panelInstructions.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Instruction steps to use the tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInstructions.setBounds(5, 30, 519, 221);
		contentPane.add(panelInstructions);
		panelInstructions.setLayout(null);
		
		panelTranscript = new JPanel();
		panelTranscript.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transcript file example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTranscript.setBounds(534, 30, 220, 221);
		contentPane.add(panelTranscript);
		panelTranscript.setLayout(null);
		
		//=================== Labels ===================//
		
		lblSteps = new JLabel("<html> <b>Properties pane </b> <br>\r\nStep 1: Browse the path containing the reference text file. <br>\r\nStep 2: Browse the path containing the hypothesis (transcript) text file.<br>\r\n<b> Criteria pane </b><br>\r\nStep 4: Select the alignment algorithm to be used for evaluation.<br>\r\nStep 5: Choose the performance measures to be indicated in output.<br>\r\n<b>Evaluation pane</b><br>\r\nStep 6: Click the check button to check the requirements for evaluation, if not satisfied check the missing requirements<br>\r\nStep 7: Click Evaluate button to start the evaluation (it will take some time) <br>\r\nStep 8: Click Get result to view the evaluation result\r\n</html>");
		lblSteps.setBounds(10, 11, 499, 210);
		panelInstructions.add(lblSteps);
		lblSteps.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblSteps.setVerticalAlignment(SwingConstants.TOP);
		
		lblTranscript = new JLabel("<html>\r\nwd1 wd2 wd3. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2 wd3 wd4. <br>\r\nwd1. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2 wd3 wd4 <br>\r\n</html>");
		lblTranscript.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblTranscript.setVerticalAlignment(SwingConstants.TOP);
		lblTranscript.setBounds(15, 20, 200, 169);
		panelTranscript.add(lblTranscript);
		
	}
}
