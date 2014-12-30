package project.speech.userInterface;

import javax.swing.*;

import project.speech.globalAccess.Globals;

import java.awt.*;

@SuppressWarnings("serial")
public class UiTest extends JWindow {

	public UiTest() {
		try {
			UIManager.setLookAndFeel(Globals.theme1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        // Set the window's bounds, centering the window
        int width = 500;
        int height = 350;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        getContentPane().setLayout(null);

        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/project/speech/userInterface/splashscreenimg.png")));
        label.setBounds(0, 0, 500, 350);

        content.add(label);
        
        JLabel lblLoading = new JLabel("Please wait... Evaluation in progress..");
        lblLoading.setBounds(140, 270, 250, 30);
        getContentPane().add(lblLoading);
        lblLoading.setFont(new Font("SansSerif", Font.PLAIN, 14));

        Color turquoise = new Color(0, 162, 232, 255);
        content.setBorder(BorderFactory.createLineBorder(turquoise, 5));

        // Display it
        setVisible(true);
        toFront();
	}
}
