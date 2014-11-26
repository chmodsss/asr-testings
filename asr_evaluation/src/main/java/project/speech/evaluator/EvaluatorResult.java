package project.speech.evaluator;

public class EvaluatorResult {
	private int hits;
	private int substitutions;
	private int deletions;
	private int insertions;
	private String timeSpan;

	public EvaluatorResult(int h, int s, int d, int i, String t) {
		hits = h;
		substitutions = s;
		deletions = d;
		insertions = i;
		timeSpan = t;
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
	
	public String getTime(){
		return timeSpan;
	}

}
