package project.speech.evaluator;

public class EvaluatorResult {
	private int hits;
	private int substitutions;
	private int deletions;
	private int insertions;
	private int numberOfWords;
	private String timeSpan;

	public EvaluatorResult(int h, int s, int d, int i, int n, String t) {
		hits = h;
		substitutions = s;
		deletions = d;
		insertions = i;
		numberOfWords = n;
		timeSpan = t;
	}
	
	public EvaluatorResult(int h, int s, int d, int i, int n) {
		hits = h;
		substitutions = s;
		deletions = d;
		insertions = i;
		numberOfWords = n;
	}

	public int getHits() {
		return hits;
	}

	public int getSubstitutions() {
		return substitutions;
	}

	public int getDeletions() {
		return deletions;
	}

	public int getInsertions() {
		return insertions;
	}
	
	public int getNumberOfWords(){
		return numberOfWords;
	}
	
	public String getTime(){
		return timeSpan;
	}

}
