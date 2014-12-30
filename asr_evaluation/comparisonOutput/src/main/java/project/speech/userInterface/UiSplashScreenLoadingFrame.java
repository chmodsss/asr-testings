package project.speech.userInterface;

import java.awt.*;

import javax.swing.*;

import project.speech.globalAccess.Globals;

@SuppressWarnings("serial")
public class UiSplashScreenLoadingFrame extends JWindow {
	
	private int threadCount = 10;
	static JProgressBar progressBar;
	
	    public UiSplashScreenLoadingFrame() {
			try {
				UIManager.setLookAndFeel(Globals.theme1);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	showSplash();
	    	System.out.println("Entered splash eval frame...");
	    	}
	    
	    public void showSplash() {

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
	        
	        progressBar = new JProgressBar();
	        getContentPane().add(progressBar);
	        progressBar.setBounds(75, 300, 345, 20);
	        
	        JLabel lblLoading = new JLabel("Loading . . .");
	        lblLoading.setFont(new Font("Cambria", Font.PLAIN, 14));
	        lblLoading.setBounds(200, 275, 80, 20);
	       	label.add(lblLoading);

	        
	        content.setBorder(BorderFactory.createLineBorder(Globals.turquoise, 5));

	        // Display it
	        setVisible(true);
	        toFront();
	        

	        try{
	        	for (int i=0 ; i < progressBar.getMaximum() ; i++){
	        		UiSplashScreenLoadingFrame.progressBar.setStringPainted(true);
	        		UiSplashScreenLoadingFrame.progressBar.setForeground(Globals.turquoise);
	        		UiSplashScreenLoadingFrame.progressBar.setValue(i);
					Thread.sleep(threadCount);
	        	}
	        	setVisible(false);
				} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        }
}
