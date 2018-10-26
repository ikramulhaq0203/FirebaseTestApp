import java.util.ArrayList;


// longest increasing sequesnce
// items in lIS  ???

public class LIS_4 {
	
	private static int lis_length(int[] arr) {
		
		int[] lis= new int[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			lis[i] = 1;
		}
		
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j<i; j++) {
				if (arr[i] > arr[j] && lis[i] <= lis[j]) {
					lis[i] = lis[j] +1;
				}
			}
		}

		return lis[arr.length-1];
	}
	public static void main(String[] args) {
		
		int arr[] = {3,4,-1,0,6,2,3};
		System.out.println(lis_length(arr));
	}

}
