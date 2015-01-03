package project.speech.evaluator;
import java.io.*;
import java.util.*;

import project.speech.globalAccess.Globals;


public class EvaluationAligner {

		private int hits = 0;
		private int numSubTotal = 0;
		private int numDelTotal = 0;
		private int numInsTotal = 0;
		private int numSub = 0;
		private int numDel = 0;
		private int numIns = 0;
		private int numberOfWords = 0;
		private int markLimit = 1000;
		private static String empty = "*****";
		private String timeSpan;
		
		private String model1= "model1";
		private String model2 = "model2";
		private String model;
		private File alignmentFile;
		
		private String asrUSed;
		private File subSpeechFolder;
		
		File ref = null;
		File hyp = null;
		File time = null;
		
		public EvaluationAligner(File referenceFile, File hypothesisFile, File timeFile) {
			this.ref = referenceFile;
			this.hyp = hypothesisFile;
			this.time = timeFile;
			this.model= model1;
		}

		public EvaluationAligner(File referenceFile, File hypothesisFile) {
			this.ref = referenceFile;
			this.hyp = hypothesisFile;
			this.model = model2;
		}
		
		@SuppressWarnings("resource")
		public EvaluatorResult evaluateWithTime(File subSpeechFolder , String asrUsed) throws IOException {
			this.asrUSed = asrUsed;
			this.subSpeechFolder = subSpeechFolder;
			BufferedReader readTime = new BufferedReader(new FileReader(time));
			timeSpan = readTime.readLine();
			this.evaluate();
			EvaluatorResult eval = new EvaluatorResult(hits - 1, numSubTotal, numDelTotal, numInsTotal , numberOfWords -1, timeSpan);
			return eval;
		}
		
		public EvaluatorResult evaluateNoTime() throws IOException {
			this.evaluate();
			EvaluatorResult eval = new EvaluatorResult(hits - 1, numSubTotal, numDelTotal, numInsTotal , numberOfWords -1);
			return eval;
		}
		

		void insertEmptyLines(List<String> refWords, BufferedReader readHyp, List<List<String>> newHypList) throws IOException{
			List<String> tempRefWords = new ArrayList<String>();
			System.out.println("temp ref created.. ");
			tempRefWords.add(refWords.get(0));
			for (int i=0; i<refWords.size()-1; i++){
				System.out.println("looping.. ");
				tempRefWords.add(empty);
			}
			newHypList.add(tempRefWords);
			System.out.println("temp ref wrods added.. ");
			readHyp.reset();
			System.out.println("reset completed.. ");				
		}
		
		public void evaluate() throws IOException{
			
			int deletionPenalty = 75;
			int substitutionPenalty = 100;
			int insertionPenalty = 75;
			
			final int OK = 0;  
			final int SUB = 1;
			final int INS = 2;
			final int DEL = 3;
			
			BufferedReader readRef = new BufferedReader(new FileReader(ref));
			BufferedReader readHyp = new BufferedReader(new FileReader(hyp));
			String refLine;
			String hypLine;
			
			List<List<String>> newHypList = new ArrayList<List<String>>();
			List<List<String>> newRefList = new ArrayList<List<String>>();
			

			while  ((refLine = readRef.readLine()) !=null ) {
				refLine = refLine.replace(".", " ");
				System.out.println("read hyp marking.. "); 
				System.out.println("read hyp .. ");
				readHyp.mark(markLimit);
				List<String> refWords = new ArrayList<String>(Arrays.asList(refLine.split(" ")));
				
				try{
				hypLine = readHyp.readLine();

				hypLine = hypLine.replace(".", " ");
				List<String> hypWords = new ArrayList<String>(Arrays.asList(hypLine.split(" ")));
				
				if (! refWords.get(0).equals( hypWords.get(0))){
					insertEmptyLines(refWords , readHyp, newHypList);
				}
				else{
					System.out.println("ordinary hyp words... ");
					newHypList.add(hypWords);
				}
				newRefList.add(refWords);
			}
				catch(Exception e){
					System.out.println("Come on.. its empty..");
						insertEmptyLines(refWords , readHyp, newHypList);
						newRefList.add(refWords);
				}
			}

			if(model == model1){
/*
				if (Globals.RecogniseAndEvaluateResultDirectory.exists()){
					FileUtils.deleteDirectory(Globals.RecogniseAndEvaluateResultDirectory);
					Globals.RecogniseAndEvaluateResultDirectory.mkdirs();
					System.out.println("Entered once in recognise and eval..");
				}
				else{
					Globals.RecogniseAndEvaluateResultDirectory.mkdirs();
				}
	*/				
				File currentFile = new File("");
				String currentPath = currentFile.getAbsolutePath();
				String newPath = currentPath +"/"+ Globals.RecogniseAndEvaluateResultDirectory;
				if (Globals.RecogniseAndEvaluateResultDirectory.exists()){
					System.out.println("Dir exists..");
				}
				alignmentFile = new File(newPath, Globals.recogniseAndEvaluateAlignmentFileName);
			}
			else if (model == model2){

				Globals.textEvaluationResultDirectory.mkdirs();
				File currentFile = new File("");
				String currentPath = currentFile.getAbsolutePath();
				String newPath = currentPath + "/"+Globals.textEvaluationResultDirectory;
				alignmentFile = new File(newPath, Globals.textEvaluationAlignmentFileName);
			}
			
			System.out.println("alignment file creating");
			PrintWriter alignmentPrintFile = new PrintWriter(new FileWriter((alignmentFile),true));
			if (model == model1){
				alignmentPrintFile.println("\n$-----------------------------  "+ subSpeechFolder.getName() +"  -----------------------------$    \n");
				alignmentPrintFile.print("   (< -------------------------  "+ asrUSed +"  ------------------------- >) \n\n ");
			}
			System.out.println("alignment file created");
			for (int index=0 ; index < newRefList.size(); index++){
				List<String> hypWords = newHypList.get(index);
				List<String> refWords = newRefList.get(index);
				
				numberOfWords = numberOfWords + refWords.size();
		
//		String refLine = "hey dude how are you doing here";
//		String hypLine = "hello hey dude how  are doing you here it"; 
				
		int [][] cost = new int[refWords.size()+1][hypWords.size() + 1];
		int [][] backtrace = new int[refWords.size() + 1][hypWords.size() + 1];
		
		cost[0][0] = 0;
		backtrace[0][0] = OK;
		
		// First column represents the case where we achieve zero hypothesis words by deleting all reference words.
		for(int i=1; i<cost.length; i++) {
			cost[i][0] = deletionPenalty * i;
			backtrace[i][0] = DEL; 
		}

		// First row represents the case where we achieve the hypothesis by inserting all hypothesis words into a zero-length reference.
		for(int j=1; j<cost[0].length; j++) {
			cost[0][j] = insertionPenalty * j;
			backtrace[0][j] = INS; 
		}
		
		// For each next column, go down the rows, recording the min cost edit operation (and the cumulative cost). 
		for(int i=1; i<cost.length; i++) {
			for(int j=1; j<cost[0].length; j++) {
				int subOp, cs;  // it is a substitution if the words aren't equal, but if they are, no penalty is assigned.
				if(refWords.get(i-1).toLowerCase().equals(hypWords.get(j-1).toLowerCase())) {
					subOp = OK;
					cs = cost[i-1][j-1];
				} else {
					subOp = SUB;
					cs = cost[i-1][j-1] + substitutionPenalty;
				}
				int ci = cost[i][j-1] + insertionPenalty;
				int cd = cost[i-1][j] + deletionPenalty;
				
				int mincost = Math.min(cs, Math.min(ci, cd));
				if(cs == mincost) {
					cost[i][j] = cs;
					backtrace[i][j] = subOp;
				} else if(ci == mincost) {
					cost[i][j] = ci;
					backtrace[i][j] = INS;					
				} else {
					cost[i][j] = cd;
					backtrace[i][j] = DEL;					
				}
			}
		}
		
		// Now that we have the minimal costs, find the lowest cost edit to create the hypothesis sequence
		LinkedList<String> alignedReference = new LinkedList<String>();
		LinkedList<String> alignedHypothesis = new LinkedList<String>();

		int i = cost.length - 1;
		int j = cost[0].length - 1;
		while(i > 0 || j > 0) {
			switch(backtrace[i][j]) {
			case OK: alignedReference.add(0, refWords.get(i-1).toLowerCase()); alignedHypothesis.add(0,hypWords.get(j-1).toLowerCase()); i--; j--; break;
			case SUB: alignedReference.add(0, refWords.get(i-1).toUpperCase()); alignedHypothesis.add(0,hypWords.get(j-1).toUpperCase()); i--; j--; numSub++; break;
			case INS: alignedReference.add(0, empty); alignedHypothesis.add(0,hypWords.get(j-1).toUpperCase()); j--; numIns++; break;
			case DEL: alignedReference.add(0, refWords.get(i-1).toUpperCase()); alignedHypothesis.add(0, empty); i--; numDel++; break;
			}
		}
		
		numSubTotal = numSubTotal + numSub;
		numDelTotal = numDelTotal + numDel;
		numInsTotal = numInsTotal + numIns;
		
		String resultRef = alignedReference.toString();
		String resultHyp = alignedHypothesis.toString();
		hits = alignedHypothesis.size() - (numSub + numIns);
		
		System.out.println(resultRef);
		System.out.println(resultHyp);
		
		alignmentPrintFile.println(resultRef);
		alignmentPrintFile.println(resultHyp);
		alignmentPrintFile.println();
		System.out.println("in the loop");
		}
			readRef.close();
			alignmentPrintFile.println("===============================================================================\n");
			alignmentPrintFile.close();
			System.out.println("Everything closed");
	}
}