//This file written by Don Landrum
import java.util.*;
import java.util.Random;
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree tree = new Tree();
		Random r = new Random();
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Type numbers. They will be added until you type -123");
		while (true) {
			int aValue = keyboard.nextInt();
			if (aValue == -123)
				break;
			else 
				tree.Add(aValue);
		}
		for (int i = 0; i < 31; ++i) {
			int x = r.nextInt(1000);
			tree.Add(i);
//			System.out.println("Adding: "+i);
//			tree.PreOrderTraversal();
		}
//		tree.Add(5);
//		tree.Add(3);
//		tree.Delete(4);
//		tree.PreOrderTraversal();
//		tree.InOrderTraversal();
//		tree.PostOrderTraversal();
		for (int i = 0; i < 101; ++i){
			System.out.println(tree.Search(i));
		}
		keyboard.close();
	}
}