
// wt[] and val[] given ...find out max profit for W weight

// what all items need to selected


public class Knapsack_0_1_2 {

	private static long maxProfit(long[] weigh, long[] val, long wt) {
		
		long table[][] = new long[weigh.length][(int) (wt + 1)];
		
		table[0][0] = 0;
		
		int index = 1;
		while (index < weigh[0]) {
			table[0][index] = 0;
		}
		
		for(int i= (int) weigh[0]; i<=wt; i++) {
				table[0][i] = val[0];	
		}
		
		for (int i = 1; i < weigh.length; i++) {
			for (int j = 0; j<=wt; j++) {
				if (j >= weigh[i]) {
					table[i][j] = max (table[i-1][j], val[i] + table[i][(int) (j-weigh[i])]);
				} else {
					table[i][j] = table[i-1][j];
				}
			}
		}
		
		System.out.println(" ...........items...........");

		int j = (int) wt;
		int i;
		for (i = weigh.length - 1; i > 0; i--) {
			if (j == 0)
				break;
			if (table[i][j] == table[i-1][j]) {
				System.out.print(weigh[i-1] +", ");
				j = (int) (j - weigh[i-1]);
			}
		}

		System.out.println("");
		System.out.println(" ...........End...........");

		return table[weigh.length -1][(int) wt];
	}
	
	private static long max(long l, long m) {
		// TODO Auto-generated method stub
		return l> m ? l:m;
	}

	public static void main(String[] args) {
		
		long w[] = {1,3,4,5};
		long v[] = {1,4,5,7};
		long res = 7;
		
		System.out.println(maxProfit(w, v, res));
	}
}
