
//minimum number of coins needed ?

//which coins makes answer ?

//number of ways to make given value?? -> find out answer



public class CoinChangeProblem_1 {

	private static long minCoinRequired(long val, long[] c) {

		long table[][] = new long[c.length][(int) (val + 1)];

		table[0][0] = 0;

		for (int i = 1; i <= val; i++) {
			table[0][i] = i/c[0];
		}

		for (int i = 1; i < c.length; i++) {
			for (int j = 0; j <= val; j++) {

				if (j < c[i]) {
					table[i][j] = table[i-1][j];
				} else {
					table[i][j] = minimum(table[i-1][j], 1+ table[i][(int) (j-c[i])]);
				}
			}
		}
		

		
		System.out.println("......... coins ............");

		long count = table[c.length - 1][(int) val];
		int j = (int) val;
		int i;
		for ( i = c.length - 1; i > 0; i--) {
			if (count == 0) {
				break;
			}
			if (table[i][j] != table[i-1][j]) {
				System.out.print(c[i] +", ");
				j = (int) (j - c[i]);
				count--;

			}
		}
		if (i == 0 && count != 0) {
			System.out.print(c[i]);
		}
		System.out.println("");
		System.out.println("......... END ............");
		
		return table[c.length-1][(int)val];
	}
	
	

	private static long minimum(long l, long m) {
		// TODO Auto-generated method stub
		return l > m ? m: l;
	}

	public static void main(String[] args) {

		long arr[] = {1,3,5,8};
		long val = 11;
		System.out.println(minCoinRequired(val, arr));
	}

}
