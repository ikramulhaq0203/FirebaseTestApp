
public class CuttingRod_6 {

	static int maxProfit(int[] arr, int[] val, int length) {

		int table[][] = new int[arr.length][length + 1];

		table[0][0] = 0;
		for (int j = 1; j <= length; j++) {
			table[0][j] = j * val[0];
		}

		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= length; j++) {
				if (j < arr[i]) {
					table[i][j] = table[i - 1][j];
				} else {
					table[i][j] = max(val[i] + table[i][j - arr[i]], table[i - 1][j]);
				}
			}
		}
		
		System.out.println("......... items ............");


		int i = arr.length-1;
		int j = length;
		
		int max_profit = table[arr.length - 1][length];
		
		while (i > 0 && j > 0) {
			if (table[i][j] != (table[i-1][j])) {
				System.out.print(arr[i] +", ");
				max_profit = max_profit- val[i];
				j = j - arr[i];
			}else {
				i--;	
			}
		}
		if (max_profit > 0) {
			System.out.print(arr[i] +", ");
			max_profit = 0;
		}
		System.out.println("");
		System.out.println("......... END ............");
		
		return table[arr.length - 1][length];
	}

	private static int max(int i, int j) {
		// TODO Auto-generated method stub
		return i > j ? i : j;
	}

	public static void main(String[] args) {

		int arr[] = { 1, 2, 3, 4 };
		int val[] = { 2, 5, 7, 8 };
		int len = 5;
		System.out.println(maxProfit(arr, val, len));

	}
}
