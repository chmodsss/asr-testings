import java.util.*;
import java.lang.Integer;

class testing_main {
	public static List<String> shift(List<String> original, int leftPose,
			int rightPose) {
		int gap = Math.abs(leftPose - rightPose);
		for (int i = 0; i < gap; i++) {
			original.add(original.get(original.size() - 1));
		}
		int pose = (leftPose < rightPose) ? leftPose : rightPose;
		for (int i = 0; i < gap; i++) {
			for (int k = original.size() - 1; k > pose; k--) {
				original.set(k, original.get(k - 1));
			}
			original.set(pose, "=====");
		}
		return original;
	}

	public static void main(String[] Args) {
		System.out.println("Hello world...");
		String str1 = "The fighting had now become intermittent. ";
		List<String> ref = new ArrayList<String>(Arrays.asList(str1.split(" ")));
		String str2 = "the fighting had now become intimate and";
		List<String> hyp = new ArrayList<String>(Arrays.asList(str2.split(" ")));
		for (int idx = 0; idx < ref.size(); idx++) {
			int store = Integer.MAX_VALUE;
			int index = 0;
			int hit = 20;
			Boolean same = false;
			for (int idxx = 0; idxx < hyp.size(); idxx++) {
				if ((ref.get(idx) != "=====") && (hyp.get(idxx) != "=====")) {
					hit = ref.get(idx).compareToIgnoreCase(hyp.get(idxx));
					if (hit == 0) {
						same = true;
						System.out.println(ref.get(idx) + ":::::"
								+ hyp.get(idxx));
						if (Math.abs(idx - idxx) < store) {
							store = Math.abs(idx - idxx);
							index = idxx;
						}
					}
				}
			}
			if ((same == true) && (idx != index)) {
				if (index > idx) {
					ref = shift(ref, idx, index);
				} else if (idx > index) {
					hyp = shift(hyp, idx, index);
				}
			}
		}
		int difference = hyp.size() - ref.size();
		if (difference != 0){
			for(int i=0; i<difference; i++){
				ref.add("=====");
			}
		}
		for (int k = 0; k < hyp.size(); k++) {
			System.out.println(ref.get(k) + "   " + hyp.get(k));
		}
	}
}