package project.speech.userInterface;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class UiInstructionMainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panelModel1;
	private JPanel panelModel2;
	private JLabel lblModel1;
	private JLabel lblModel2;

	public UiInstructionMainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UiInstructionMainFrame.class.getResource("/project/speech/userInterface/logo.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 773, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//=================== Panels ===================//
		
		panelModel1 = new JPanel();
		panelModel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Recognise & Evaluate", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelModel1.setBounds(5, 30, 370, 250);
		contentPane.add(panelModel1);
		panelModel1.setLayout(null);
		
		panelModel2 = new JPanel();
		panelModel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Text evaluation", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelModel2.setBounds(383, 30, 370, 250);
		contentPane.add(panelModel2);
		panelModel2.setLayout(null);
		
		
		//=================== Labels ===================//
		
		lblModel1 = new JLabel("<html> <b>Recognition and Evaluation :</b> <br>\r\nChoose this model, if you have to recognise speeches and evaluate them using the CmuSphinx and iSpeech<br>\r\nOther speech recognition SDK using java implementation could also be added <br>\r\n<b>Requirements</b>\r\n<ul>\r\n<li> Speech database (speech files and respective transcription) </li>\r\n<li> Speech recognition SDK in java </li>\r\n<li> Dictionary, languae and acoustic models </li>\r\n</ul>\r\n</html>");
		lblModel1.setBounds(20, 20, 350, 220);
		panelModel1.add(lblModel1);
		lblModel1.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblModel1.setVerticalAlignment(SwingConstants.TOP);
		
		lblModel2 = new JLabel("<html>\r\n<b>Evaluation :</b><br>\r\nChoose this model, if you have to evaluate the following two files<br>\r\n<ul>\r\n<li> Reference file (transcription) </li>\r\n<li> Hypothesis file (recognition output) </li>\r\n</ul>\r\nThe evaluation is carried out with respect to the corresponding lines in the files. ie, the first line of the reference file is compared with the first line of the hypothesis file.\r\n</html>");
		lblModel2.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblModel2.setVerticalAlignment(SwingConstants.TOP);
		lblModel2.setBounds(20, 20, 350, 220);
		panelModel2.add(lblModel2);
		
	}
}
