import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class justforfun {
	private static File hi = new File("heyfolder");
	public static void main(String[] args) throws IOException {
		System.out.println("Helloie world..");
		hi.mkdirs();
		File evaluationResult = new File(hi, "evaluation-result.txt");
		PrintWriter asrOutFile = new PrintWriter(new FileWriter((evaluationResult),true));
/*		int res = LevenshteinDistance.computeLevenshteinDistance(str1, str2);
		System.out.println((double) res / str1.length());
		List<String> testString = new ArrayList<String>(Arrays.asList(str1.split(" ")));
		System.out.println(testString);
		System.out.println(testString.size());
		String strr = "and";
		testString.add(strr);
		testString.add(testString.get(testString.size()-1));
		System.out.println(testString);
		System.out.println(testString.size());
		//for(int k= testString.size()-1 ; k <2; k++)*/
	}
}