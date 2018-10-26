

//number of ways to make some value
public class CoinChangeWays_9 {

	static int numberWays(int[] arr, int val) {

		int table[][] = new int[arr.length][val + 1];

		for (int i = 0; i < arr.length; i++) {
			table[i][0] = 1;
		}

		for (int j = 1; j <= val; j++) {
			table[0][j] = 1;
		}

		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= val; j++) {
				if (j >= arr[i]) {
					table[i][j] = table[i][j - arr[i]] + table[i - 1][j];
				} else {
					table[i][j] = table[i - 1][j];
				}
			}
		}

		return table[arr.length - 1][val];
	}

	public static void main(String[] args) {

		int arr[] = { 1, 2, 3 };
		int val = 5;

		System.out.println(numberWays(arr, val));

	}
}
