package project.speech.evaluator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class EvaluationAligner {

		private int hits = 0;
		private int numSubTotal = 0;
		private int numDelTotal = 0;
		private int numInsTotal = 0;
		private int numSub = 0;
		private int numDel = 0;
		private int numIns = 0;
		private int numberOfWords = 0;
		private static String empty = "*****";
		private String timeSpan;
		File ref = null;
		File hyp = null;
		File time = null;
		
		public EvaluationAligner(File referenceFile, File hypothesisFile, File timeFile) {
			this.ref = referenceFile;
			this.hyp = hypothesisFile;
			this.time = timeFile;
		}

		public EvaluationAligner(File referenceFile, File hypothesisFile) {
			this.ref = referenceFile;
			this.hyp = hypothesisFile;
		}
		
		@SuppressWarnings("resource")
		public EvaluatorResult evaluateWithTime() throws IOException {
			BufferedReader readTime = new BufferedReader(new FileReader(time));
			timeSpan = readTime.readLine();
			this.evaluate();
			EvaluatorResult eval = new EvaluatorResult(hits - 1, numSubTotal, numDelTotal, numInsTotal , numberOfWords -1, timeSpan);
			return eval;
		}
		
		public EvaluatorResult evaluateNoTime() throws IOException {
			evaluate();
			EvaluatorResult eval = new EvaluatorResult(hits - 1, numSubTotal, numDelTotal, numInsTotal , numberOfWords -1);
			return eval;
		}

		@SuppressWarnings("resource")
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

			while  (((refLine = readRef.readLine()) !=null) && ((hypLine = readHyp.readLine()) !=null) ){
				refLine = refLine.replace(".", " ");
				hypLine = hypLine.replace(".", " ");
				List<String> refWords = new ArrayList<String>(Arrays.asList(refLine.split(" ")));
				List<String> hypWords = new ArrayList<String>(Arrays.asList(hypLine.split(" ")));
				
				if (! refWords.get(0).equals(hypWords.get(0))){
					System.out.println("refwords :"+refWords.get(0)+" and hypwords :"+hypWords.get(0));
					break;
				}
				
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
		
		}
			readRef.close();
	}
}