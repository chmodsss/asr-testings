package project.speech.userInterface;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;


@SuppressWarnings("serial")
public class UiInstructionFrame1 extends JFrame {

	private JPanel contentPane;
	private JPanel panelTree;
	private JPanel panelSteps;
	private JLabel lblSteps;
	private JPanel panelNote;
	private JLabel lblNote;
	private JPanel panelTranscript;
	private JLabel lblTranscript;
	@SuppressWarnings("unused")
	private BufferedImage myPicture = null;

	public UiInstructionFrame1() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UiInstructionFrame1.class.getResource("/project/speech/userInterface/logo.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=================== Panels ===================//
		
		panelTree = new JPanel();
		panelTree.setBorder(new TitledBorder(null, "Speech database structure", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTree.setBounds(530, 30, 220, 179);
		contentPane.add(panelTree);
		panelTree.setLayout(null);
		JLabel picTree = new JLabel(new ImageIcon(UiInstructionFrame1.class.getResource("/project/speech/userInterface/tree.png")));
		picTree.setBounds(10, 22, 215, 157);
		panelTree.add(picTree);
		picTree.setText("pic display");
		
		panelSteps = new JPanel();
		panelSteps.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Instruction steps to use the tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSteps.setBounds(5, 30, 520, 258);
		contentPane.add(panelSteps);
		panelSteps.setLayout(null);
		
		panelNote = new JPanel();
		panelNote.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "NOTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNote.setBounds(5, 294, 520, 130);
		contentPane.add(panelNote);
		panelNote.setLayout(null);
		
		panelTranscript = new JPanel();
		panelTranscript.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Transcript file example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTranscript.setBounds(530, 220, 220, 205);
		contentPane.add(panelTranscript);
		panelTranscript.setLayout(null);

		//=================== Labels ===================//
		
		lblSteps = new JLabel("<html> <b>Properties pane </b> <br>\r\nStep 1: Browse the path containing the speech database using Speech Corpus button (see NOTE)<br>\r\nStep 2: Choose one of the speech recognition systems from the dropdown list. <br>\r\nStep 3: Select the respective model files (dictionary, language and acoustic).<br>\r\n<b> Result choice pane </b><br>\r\nStep 4: Select the speech engines to be evaluated for output.<br>\r\nStep 5: Choose the performance measures to be indicated in output.<br>\r\n<b>Evaluation pane</b><br>\r\nStep 6: Click the check button to check the requirements for evaluation, if not satisfied check the missing requirements<br>\r\nStep 7: Click Evaluate button to start the evaluation (it will take some time)\r\n</html>");
		lblSteps.setBounds(10, 11, 494, 246);
		panelSteps.add(lblSteps);
		lblSteps.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblSteps.setVerticalAlignment(SwingConstants.TOP);
		
		lblNote = new JLabel("<html>\r\nSpeech corpus can have multiple folders, where each folder should contain two subfolders <b>wav</b> and <b>etc</b> (Refer the tree).<br>\r\nTranscription of all the files are stored in a single file \"prompts-original.txt\" with each line consisting of audio file name(without extension) followed by  the spoken text (eg: wd1 wd2 wd3 ,...)\r\n</html>");
		lblNote.setBounds(10, 11, 500, 108);
		panelNote.add(lblNote);
		lblNote.setFont(new Font("Rockwell", Font.PLAIN, 14));
		
		lblTranscript = new JLabel("<html>\r\nspeech1 wd1 wd2 wd3. <br>\r\nspeech2 wd1 wd2. <br>\r\nspeech3 wd1 wd2 wd3 wd4. <br>\r\nspeech4 wd1. <br>\r\nspeech5 wd1 wd2. <br>\r\nspeech6 wd1 wd2. <br>\r\nspeech7 wd1 wd2 wd3 wd4 <br>\r\n</html>");
		lblTranscript.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblTranscript.setVerticalAlignment(SwingConstants.TOP);
		lblTranscript.setBounds(15, 20, 200, 169);
		panelTranscript.add(lblTranscript);
	}
}
