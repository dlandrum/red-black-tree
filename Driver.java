// Written by Don Landrum on October 10, 2017 for personal use
import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;
import java.math.RoundingMode;
public class Driver {
	public static final int INPUT_SIZE = 1000000; //the number of numbers we want to try to insert
	public static void main(String[] args) {
		int testMode = Integer.parseInt(args[0]); //decides what inputs we will use
		Tree<Integer> t = new Tree<Integer>();
		Random r = new Random();
		int additions = 0; //number of successful additions
		if (testMode == 0) { //random number mode
			for (int i = 0; i < INPUT_SIZE; ++i) {
				//the tree does not allow duplicate values and will return null if one is entered
				if (t.add(r.nextInt(5*INPUT_SIZE)) != null) {
					//if we successfully add a random value, increment additions
					++additions;
				}
			}
		}
		else if (testMode == 1) { //ascending order mode
			for (int i = 0; i < INPUT_SIZE; ++i) {
				//adds every number between 0 and INPUT_SIZE
				if (t.add(i) != null) {
					++additions;
				}
			}
		}
		else { //descending order mode
			for (int i = INPUT_SIZE-1; i >= 0; --i) {
				//adds every number between INPUT_SIZE and 0
				if (t.add(i) != null) {
					++additions;
				}
			}
		}
		if (!t.isValid()) { //internal check for validity
			System.out.println("Tree is broken after additions.");
		}
		int deletions = 0; //number of successful deletions
		for (int i = 0; i < INPUT_SIZE/4; ++i) {
			//try INPUT_SIZE/4 times to remove a random number that may be in the tree
			if (t.remove(r.nextInt(5*INPUT_SIZE)) != null) {
				//similar to the add method, this returns null if the target is not found
				++deletions;
			}
		}
		System.out.println("After "+additions+" additions and "+deletions+" deletions, is the tree valid?");
		if (t.isValid()) {
			System.out.println("Yes!");
			int elements = additions - deletions;
			int blackHeight = t.getBlackHeight();
			double log2 = (Math.log(((double)elements)+1.0) / Math.log(2.0));
			DecimalFormat df = new DecimalFormat("#.##"); //for formatting the log2 variable
			df.setRoundingMode(RoundingMode.CEILING); //for formatting the log2 variable
			String s = "The tree has "+elements+" elements and a black height of "+blackHeight+".\n";
			s += "The black-height of a proper tree should be <= 1 + log base 2 of (n + 1).\n";
			s += "Log base 2 of (n + 1) is "+df.format(log2)+".\n";
			s += "Also, the height of a red-black tree <= twice the black-height of the tree.\n";
			s += "As we can observe, this means that the height is bounded by O(log n).\n";
			s += "The total height is no more than twice the black-height, or "+(2*blackHeight)+" in our case.";
			System.out.println(s);
		}
		else {
			System.out.println("No.");
		}
		Scanner in = new Scanner(System.in);
		System.out.println("Enter search queries if you would like to search the tree. \nEntering -1 will end the program.");
		int target = in.nextInt();
		while (target != -1) {
			System.out.println(t.search(target)); //performs the search
			target = in.nextInt(); //brings in the next target
		}
		in.close();
	}
}
