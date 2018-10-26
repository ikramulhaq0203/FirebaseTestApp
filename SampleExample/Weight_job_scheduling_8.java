
public class Weight_job_scheduling_8 {

	static int maxProfit(int arr[][], int val[]) {

		int tmp[] = new int[arr.length];

		for (int i = 0; i < val.length; i++) {
			tmp[i] = val[i];
		}

		for (int i = 1; i < val.length; i++) {

			for (int j = 0; j < i; j++) {

				if (arr[i][0] >= arr[j][1]) {
					tmp[i] = tmp[j] + val[i];
				}
			}
		}
		
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i<val.length; i++) {
			if (max<tmp[i]) max = tmp[i];
			//System.out.print(tmp[i] +", ");
		}
		return max;
	}

	public static void main(String[] args) {

		int arr[][] = { { 1, 3 }, { 2, 5 }, { 4, 6 }, { 6, 7 }, { 5, 8 }, { 7, 9 } };
		int val[] = { 5, 6, 5, 4, 11, 2 };
		System.out.println(maxProfit(arr, val));
	}
}
