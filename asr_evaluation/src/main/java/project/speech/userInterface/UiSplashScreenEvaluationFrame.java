package project.speech.userInterface;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.commons.io.FileUtils;

import project.speech.evaluationsystem.EvaluationSystem;
import project.speech.globalAccess.Globals;

@SuppressWarnings("serial")
public class UiSplashScreenEvaluationFrame extends JWindow {
	
	static String model1 = "model1";
	static String model2 = "model2";
	
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
					    MySwingWorker worker = new MySwingWorker(speechDatabaseDirectory,  asrPropertiesObj,  selectedPerformanceList ,  selectedAsrList, algorithmSelected, guiWindow , model1);
					    worker.execute();
			           }
	    });
	    }
	    
	    public UiSplashScreenEvaluationFrame  
	    ( final File referenceFilePath, final File hypothesisFilePath, final ArrayList<String> performanceListSelected , final String algorithmSelected) throws InvocationTargetException, InterruptedException  {
	    	EventQueue.invokeLater(new Runnable(){
	    	      public void run() {
				    	try{
							UIManager.setLookAndFeel(Globals.theme2);
						} catch (Exception e) {
							e.printStackTrace();
						}
				    	JWindow guiWindow = createGUI();
				        guiWindow.setVisible( true );
					    MySwingWorker worker = new MySwingWorker(referenceFilePath, hypothesisFilePath, performanceListSelected, algorithmSelected , guiWindow, model2);
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
		  
		  private File referenceFilePath2;
		  private File hypothesisFilePath2;
		  private ArrayList<String> performanceListSelected2;
		  private String algorithmSelected2;
		  private JWindow guiWindow2;
		  
		  private String model;
		  
	    public MySwingWorker(File speechDatabaseDirectory,
				ArrayList<UiAsrProperties> asrPropertiesObj,
				ArrayList<String> selectedPerformanceList,
				ArrayList<String> selectedAsrList, String algorithmSelected, JWindow guiWindow , String model1) {
	    	speechDatabaseDirectory1 = speechDatabaseDirectory;
	    	asrPropertiesObj1 = asrPropertiesObj;
			selectedPerformanceList1 = selectedPerformanceList;
			selectedAsrList1 = selectedAsrList;
			algorithmSelected1 = algorithmSelected;
			guiWindow1 = guiWindow;
			model = model1;
		}
	    
	    public MySwingWorker(File referenceFilePath, File hypothesisFilePath, ArrayList<String> performanceListSelected , String algorithmSelected , JWindow guiWindow, String model2) {
	    	  referenceFilePath2 = referenceFilePath;
			  hypothesisFilePath2 = hypothesisFilePath;
			  performanceListSelected2 = performanceListSelected;
			  algorithmSelected2 = algorithmSelected;
			  guiWindow2 = guiWindow;
			  model = model2;
			  }	    

		@Override
	    protected String doInBackground() throws Exception {
			if (model == UiSplashScreenEvaluationFrame.model1){
	    	EvaluationSystem.recogniseAndEvaluate(speechDatabaseDirectory1,  asrPropertiesObj1,  selectedPerformanceList1 ,  selectedAsrList1, algorithmSelected1);
			}
			else if (model == UiSplashScreenEvaluationFrame.model2){
				EvaluationSystem.textEvaluation(referenceFilePath2, hypothesisFilePath2, performanceListSelected2, algorithmSelected2);
			}
	    	return "Completed";
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
	    	if (model == UiSplashScreenEvaluationFrame.model1){
	    		guiWindow1.setVisible(false);
	    		try {
					openUpResult(Globals.model1ResultFilePath, Globals.model1AlignmentFilePath,  Globals.model1CompleteOutputFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	else if (model == UiSplashScreenEvaluationFrame.model2){
	    	guiWindow2.setVisible(false);
	    	try {
				openUpResult(Globals.model2ResultFilePath, Globals.model2AlignmentFilePath, Globals.model2CompleteOutputFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	} 
	    }
	    
		public void openUpResult(String modelOutputFilePath, String modelAlignmentFilePath, String completeOutputFilePath) throws IOException {

			File currentFile = new File("");
			String currentPath = currentFile.getAbsolutePath();

			File file1 = new File(currentPath+"/"+modelAlignmentFilePath);
			File file2 = new File(currentPath+"/"+modelOutputFilePath);
			File file3 = new File(currentPath+"/"+completeOutputFilePath);
			
			String file1Str = FileUtils.readFileToString(file1);
			String file2Str = FileUtils.readFileToString(file2);

			// Write the file
			FileUtils.write(file3, file1Str);
			FileUtils.write(file3, file2Str, true); // true for append
			
			///
			String newnewPath = currentPath+"/"+completeOutputFilePath;
			UiResultFrame1.initialise(newnewPath);
		}
		
}
