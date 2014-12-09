package project.speech.userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;


public class UiInstructionFrame2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UiInstructionFrame2() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 774, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Instruction steps to use the tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMain.setBounds(5, 30, 519, 221);
		contentPane.add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html> <b>Properties pane </b> <br>\r\nStep 1: Browse the path containing the reference text file. <br>\r\nStep 2: Browse the path containing the hypothesis (transcript) text file.<br>\r\n<b> Criteria pane </b><br>\r\nStep 4: Select the alignment algorithm to be used for evaluation.<br>\r\nStep 5: Choose the performance measures to be indicated in output.<br>\r\n<b>Evaluation pane</b><br>\r\nStep 6: Click the check button to check the requirements for evaluation, if not satisfied check the missing requirements<br>\r\nStep 7: Click Evaluate button to start the evaluation (it will take some time) <br>\r\nStep 8: Click Get result to view the evaluation result\r\n</html>");
		lblNewLabel.setBounds(10, 11, 494, 246);
		panelMain.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transcript file example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(534, 30, 220, 221);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSpeechWordWord = new JLabel("<html>\r\nwd1 wd2 wd3. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2 wd3 wd4. <br>\r\nwd1. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2. <br>\r\nwd1 wd2 wd3 wd4 <br>\r\n</html>");
		lblSpeechWordWord.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblSpeechWordWord.setVerticalAlignment(SwingConstants.TOP);
		lblSpeechWordWord.setBounds(15, 20, 200, 169);
		panel_1.add(lblSpeechWordWord);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("tree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
