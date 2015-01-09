package project.speech.userInterface;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class UiAboutMainFrame extends JDialog {

	private JPanel contentPane;
	private JLabel lblAsrLogo;

	public UiAboutMainFrame() {
		setTitle("About software");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UiAboutMainFrame.class.getResource("/project/speech/userInterface/logo.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 496, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblAsrLogo = new JLabel("");
		lblAsrLogo.setIcon(new ImageIcon(UiAboutMainFrame.class.getResource("/project/speech/userInterface/logo.png")));
		lblAsrLogo.setBounds(135, 21, 209, 195);
		contentPane.add(lblAsrLogo);
		
		JLabel lblNewLabel = new JLabel("<html>\r\n<u><center><font size=\"6\"> ASR Evaluation toolkit </font> </center> </u><br>\r\n<center> Version 1.0 Initial release </center> <br>\r\n<center><font size=\"3\"> Redistributable and modifiable under the terms and condions provided in the license file </font></center> <br>\r\n<center> Developed by: Sivasurya santhanam & Rhama dwiputra </center> <br>\r\n<center> Copyright &copy 2014. All rights reserved </center> <br>\r\n</html>");
		lblNewLabel.setFont(new Font("Cambria Math", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(29, 233, 425, 187);
		contentPane.add(lblNewLabel);
		
	}
}
