package maytestuse;

public class CodeToModify {

    public static void main(String[] args) {
        System.out.println(10+10);
	    System.out.println(calculateDifference(5, 5));
    }

	
	private static int calculateDifference(int a, int b){
		if (a > b) {
			return a-b;
		} else if (b > a) {
			return b-a;
		} else {
			return 0;
		}
	}
}
