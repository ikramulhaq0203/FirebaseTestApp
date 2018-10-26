
public class MinimumJumpInArray_7 {

	static int minJumpRequired(int[] arr) {

		int tmp[] = new int[arr.length];
		int act_jump[] = new int[arr.length];

		for (int i = 1; i < arr.length; i++) {
			tmp[i] = Integer.MAX_VALUE;
		}

		tmp[0] = 0;
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (i - j <= arr[j]) {
					if (tmp[i] > tmp[j]+1) {
						act_jump[i] = j;
					}
					tmp[i] = min(tmp[i], tmp[j] + 1);
				}
			}
		}

		System.out.println("......... coins ............");
		
		for (int i = 0; i<arr.length; i++) {
			System.out.print(act_jump[i] +", ");
		}
		System.out.println("");
		System.out.println("......... END ............");
		
		return tmp[arr.length - 1];
	}

	private static int min(int i, int j) {
		// TODO Auto-generated method stub
		return i > j ? j : i;
	}

	public static void main(String[] args) {

		int arr[] = { 2, 3, 1, 1, 2, 4, 2, 0, 1, 1 };
		System.out.println(minJumpRequired(arr));
	}
}
