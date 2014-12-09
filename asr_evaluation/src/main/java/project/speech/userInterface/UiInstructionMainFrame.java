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

public class UiInstructionMainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UiInstructionMainFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 773, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Model1", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelMain.setBounds(5, 30, 370, 250);
		contentPane.add(panelMain);
		panelMain.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html> <b>Recognition and Evaluation :</b> <br>\r\nChoose this model, if you have to recognise speeches and evaluate them using the CmuSphinx and iSpeech<br>\r\nOther speech recognition SDK using java implementation could also be added <br>\r\n<b>Requirements</b>\r\n<ul>\r\n<li> Speech database (speech files and respective transcription) </li>\r\n<li> Speech recognition SDK in java </li>\r\n<li> Dictionary, languae and acoustic models </li>\r\n</ul>\r\n</html>");
		lblNewLabel.setBounds(20, 20, 350, 220);
		panelMain.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Model2", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(383, 30, 370, 250);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSpeechWordWord = new JLabel("<html>\r\n<b>Evaluation :</b><br>\r\nChoose this model, if you have to evaluate the following two files<br>\r\n<ul>\r\n<li> Reference file (transcription) </li>\r\n<li> Hypothesis file (recognition output) </li>\r\n</ul>\r\nThe evaluation is carried out with respect to the corresponding lines in the files. ie, the first line of the reference file is compared with the first line of the hypothesis file.\r\n</html>");
		lblSpeechWordWord.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblSpeechWordWord.setVerticalAlignment(SwingConstants.TOP);
		lblSpeechWordWord.setBounds(20, 20, 350, 220);
		panel_1.add(lblSpeechWordWord);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("tree.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
