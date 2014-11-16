package evaluate_pkg;

import java.util.*;
import java.io.*;

public class Files {
private static File refFile;
private static File hypFile;

public static File getRefFile(int s){
	refFile = new File("/resource/prompts-original");
	return refFile;
}

public static File getHypFile(int s){
	hypFile = new File("/resource/prompts-original");
	return hypFile;
}

}
