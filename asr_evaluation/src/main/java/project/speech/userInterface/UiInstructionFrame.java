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

public class UiInstructionFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiInstructionFrame frameInstruct = new UiInstructionFrame();
					frameInstruct.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UiInstructionFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTree = new JPanel();
		panelTree.setBorder(new TitledBorder(null, "Speech database structure", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTree.setBounds(530, 30, 220, 179);
		contentPane.add(panelTree);
		panelTree.setLayout(null);
		JLabel picLabel = new JLabel(new ImageIcon("C:\\Users\\SIVASURYA\\java_workspace\\asr_evaluation\\tree.png"));
		picLabel.setBounds(10, 22, 215, 157);
		panelTree.add(picLabel);
		picLabel.setText("pic display");
		
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Instruction steps to use the tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMain.setBounds(5, 30, 520, 258);
		contentPane.add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html> <b>Properties pane </b> <br>\r\nStep 1: Browse the path containing the speech database using Speech Corpus button (see NOTE)<br>\r\nStep 2: Choose one of the speech recognition systems from the dropdown list. <br>\r\nStep 3: Select the respective model files (dictionary, language and acoustic).<br>\r\n<b> Result choice pane </b><br>\r\nStep 4: Select the speech engines to be evaluated for output.<br>\r\nStep 5: Choose the performance measures to be indicated in output.<br>\r\n<b>Evaluation pane</b><br>\r\nStep 6: Click the check button to check the requirements for evaluation, if not satisfied check the missing requirements<br>\r\nStep 7: Click Evaluate button to start the evaluation (it will take some time)\r\n</html>");
		lblNewLabel.setBounds(10, 11, 494, 246);
		panelMain.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "NOTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(5, 294, 520, 130);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("<html>\r\nSpeech corpus can have multiple folders, where each folder should contain two subfolders <b>wav</b> and <b>etc</b> (Refer the tree).<br>\r\nTranscription of all the files are stored in a single file \"prompts-original.txt\" with each line consisting of audio file name(without extension) followed by  the spoken text (eg: wd1 wd2 wd3 ,...)\r\n</html>");
		lblNewLabel_1.setBounds(10, 11, 500, 108);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Rockwell", Font.PLAIN, 14));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transcript file example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(530, 220, 220, 205);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSpeechWordWord = new JLabel("<html>\r\nspeech1 wd1 wd2 wd3. <br>\r\nspeech2 wd1 wd2. <br>\r\nspeech3 wd1 wd2 wd3 wd4. <br>\r\nspeech4 wd1. <br>\r\nspeech5 wd1 wd2. <br>\r\nspeech6 wd1 wd2. <br>\r\nspeech7 wd1 wd2 wd3 wd4 <br>\r\n</html>");
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
