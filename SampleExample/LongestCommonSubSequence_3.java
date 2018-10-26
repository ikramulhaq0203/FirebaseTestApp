
//find length of longest common sequence 

// find longest subsequence string


public class LongestCommonSubSequence_3 {

	private static long longestLength(String str1, String str2) {

		long row = str1.length();
		long col = str2.length();
		
		
		long table[][] = new long[(int) (row + 1)][(int) (col + 1)];
		
		for (int i = 0; i <= col; i++) {
			table[0][i] = 0;
		}
		
		for (int i = 0; i <= row; i++) {
			table[i][0] = 0;
		}

		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					table[i][j] = table[i - 1][j - 1] + 1;
				} else {
					table[i][j] = max(table[i - 1][j], table[i][j - 1]);
				}
			}
		}
		
/*		for (int i = 0; i <= row; i++) {
			for (int j = 0; j <= col; j++) {
				System.out.print(table[i][j] +", ");
			}
			System.out.println("");
		}*/
		
		System.out.println("......... coins ............");


		int i = (int) row;
		int j = (int) col;
		
		//System.out.print(i +", "+j +" , "+table[i-1][j-1]);
		while (i > 0 && j > 0) {
			if (table[i][j] == (table[i-1][j])) {
				//System.out.print(str1.charAt(i-1) +", ");
				//j = j -1;
				i = i-1;
			} else if (table[i][j] == table[i][j-1]) {
				j = j-1;
			} else if (table[i][j] == (table[i-1][j-1] +1)){
				System.out.print(str1.charAt(i-1) +", ");
				j = j -1;
				i = i-1;
			}
		}
		System.out.println("");
		System.out.println("......... END ............");
		
		
		return table[(int) (row)][(int) (col)];
	}

	private static long max(long l, long m) {
		// TODO Auto-generated method stub
		return l > m ? l : m;
	}

	public static void main(String[] args) {

		String str1 = "acbcf";
		String str2 = "abcdaf";
		if (str1.length() > str2.length()) {
			System.out.println(longestLength(str2, str1));	
		} else {
			System.out.println(longestLength(str1, str2));
		}
	}
}
