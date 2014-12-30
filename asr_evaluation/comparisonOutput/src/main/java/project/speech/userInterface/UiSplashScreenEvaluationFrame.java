package project.speech.userInterface;

import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.*;

import project.speech.evaluationsystem.EvaluationSystem;
import project.speech.globalAccess.Globals;

@SuppressWarnings("serial")
public class UiSplashScreenEvaluationFrame extends JWindow {
	
	    public UiSplashScreenEvaluationFrame
	    ( final File speechDatabaseDirectory, final ArrayList<UiAsrProperties> asrPropertiesObj, final ArrayList<String> selectedPerformanceList , final ArrayList<String> selectedAsrList, final String algorithmSelected) throws InvocationTargetException, InterruptedException  {
	    	EventQueue.invokeLater(new Runnable(){
	    	      public void run() {
				    	try{
							UIManager.setLookAndFeel(Globals.theme2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				    	JWindow guiWindow = createGUI();
				        guiWindow.setVisible( true );
					    MySwingWorker worker = new MySwingWorker(speechDatabaseDirectory,  asrPropertiesObj,  selectedPerformanceList ,  selectedAsrList, algorithmSelected, guiWindow);
					    worker.execute();
			           }
	    });
	    }
	    
	    private JWindow createGUI(){
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
	       	
	       	JLabel lblLoading = new JLabel("");
	       	lblLoading.setIcon(new ImageIcon(UiSplashScreenEvaluationFrame.class.getResource("/project/speech/userInterface/load.GIF")));
	       	label.add(lblLoading);
	       	lblLoading.setFont(new Font("SansSerif", Font.PLAIN, 16));
	       	lblLoading.setBounds(170, 251, 200, 30);
	       	
	       	JLabel lblgif = new JLabel("");
	       	lblgif.setIcon(new ImageIcon(UiSplashScreenEvaluationFrame.class.getResource("/project/speech/userInterface/evaluategif.GIF")));
	       	lblgif.setBounds(40, 280, 190, 50);
	       	getContentPane().add(lblgif);
	       	
	       	JLabel label_1 = new JLabel("");
	       	label_1.setIcon(new ImageIcon(UiSplashScreenEvaluationFrame.class.getResource("/project/speech/userInterface/evaluategif.GIF")));
	       	label_1.setBounds(225, 280, 200, 50);
	       	getContentPane().add(label_1);

	        
	        content.setBorder(BorderFactory.createLineBorder(Globals.turquoise, 5));

	        // Display it
	        setVisible(true);
	        toFront();
	        
	        return this;

	    }


	  }
	  class MySwingWorker extends SwingWorker<String, Double>{

		  private File speechDatabaseDirectory1;
		  private ArrayList<UiAsrProperties> asrPropertiesObj1;
		  private ArrayList<String> selectedPerformanceList1;
		  private ArrayList<String> selectedAsrList1;
		  private String algorithmSelected1;
		  private JWindow guiWindow1;
		  
	    public MySwingWorker(File speechDatabaseDirectory,
				ArrayList<UiAsrProperties> asrPropertiesObj,
				ArrayList<String> selectedPerformanceList,
				ArrayList<String> selectedAsrList, String algorithmSelected, JWindow guiWindow) {
	    	speechDatabaseDirectory1 = speechDatabaseDirectory;
	    	asrPropertiesObj1 = asrPropertiesObj;
			selectedPerformanceList1 = selectedPerformanceList;
			selectedAsrList1 = selectedAsrList;
			algorithmSelected1 = algorithmSelected;
			guiWindow1 = guiWindow;
		}

		@Override
	    protected String doInBackground() throws Exception {
	    	EvaluationSystem.recogniseAndEvaluate(speechDatabaseDirectory1,  asrPropertiesObj1,  selectedPerformanceList1 ,  selectedAsrList1, algorithmSelected1);
	    	return "Finished";
	    }

	    /*
	    @Override
	    protected void process( List<Double> aDoubles ) {
	      //update the percentage of the progress bar that is done
	      int amount = fProgressBar.getMaximum() - fProgressBar.getMinimum();
	      fProgressBar.setValue( ( int ) (fProgressBar.getMinimum() + ( amount * aDoubles.get( aDoubles.size() - 1 ))) );
	    }*/

	    @Override
	    protected void done()  {
	    	  guiWindow1.setVisible(false);
		} 
}
