
public class SumSubSequence_5 {
	
	static boolean isSumexist(int[] arr, int val) {
		
		boolean table[][] = new boolean[arr.length][val+1];
		
		for (int i = 0; i< arr.length; i++) {
			table[i][0] = true;
		}
		
		for (int j = 0; j <=val; j++) {
			if (j == arr[0]) {
				table[0][j] = true; 
			} else {
				table[0][j] = false;
			}
		}
		for (int i = 1; i< arr.length; i++) {
			for (int j = 1; j<= val; j++) {
				if (j >= arr[i]) {
					table[i][j] = table[i-1][j] || (table[i-1][j-arr[i]]) ;	
				} else {
					table[i][j] = table[i-1][j];
				}
			}
		}
		
		System.out.println("......... items ............");


		int i = arr.length-1;
		int j = val;
		
		while (i > 0 && j > 0) {
			if (table[i][j] != (table[i-1][j])) {
				System.out.print(arr[i] +", ");
				j = j - arr[i];
			} else {
				i--;
			}

			
		}
		System.out.println("");
		System.out.println("......... END ............");
		
		return table[arr.length-1][val];
	}
	public static void main(String[] args) {
		
		int arr[] = {2,3,7,8,10};
		int val = 25;
		
		System.out.println(isSumexist(arr, val));
		
	}

}
