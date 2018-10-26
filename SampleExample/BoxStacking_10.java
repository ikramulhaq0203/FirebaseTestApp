import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class BoxStacking_10 {

	class BoxStack implements Comparable<BoxStack> {
		int l;
		int w;
		int h;
		int area;

		public BoxStack(int l, int w, int h, int area) {
			// TODO Auto-generated constructor stub
			this.l = l;
			this.w = w;
			this.h = h;
			this.area = area;
		}

		@Override
		public int compareTo(BoxStack o) {
			// TODO Auto-generated method stub
			return o.area - this.area;
		}
	}

	static int maxHeight() {

		return 0;
	}

	public static void main(String[] args) {
		int l, w, h, area;

		Scanner sc = new Scanner(System.in);

		BoxStacking_10 b = new BoxStacking_10();
		ArrayList<BoxStack> al = new ArrayList<>();

		int item1[] = { 1, 2, 4 };
		int item2[] = { 3, 2, 5 };

		Arrays.sort(item1);

		l = item1[2];
		w = item1[1];
		h = item1[0];
		area = item1[2] * item1[1];

		BoxStack b1 = b.new BoxStack(l, w, h, area);

		l = item1[2];
		w = item1[0];
		h = item1[1];
		area = item1[2] * item1[0];

		BoxStack b2 = b.new BoxStack(l, w, h, area);

		l = item1[1];
		w = item1[0];
		h = item1[2];
		area = item1[1] * item1[0];

		BoxStack b3 = b.new BoxStack(l, w, h, area);

		al.add(b1);
		al.add(b2);
		al.add(b3);
		
		
		Arrays.sort(item2);

		l = item2[2];
		w = item2[1];
		h = item2[0];
		area = item2[2] * item2[1];

		BoxStack b4 = b.new BoxStack(l, w, h, area);

		l = item2[2];
		w = item2[0];
		h = item2[1];
		area = item2[2] * item2[0];

		BoxStack b5 = b.new BoxStack(l, w, h, area);

		l = item2[1];
		w = item2[0];
		h = item2[2];
		area = item2[1] * item2[0];

		BoxStack b6 = b.new BoxStack(l, w, h, area);

		al.add(b4);
		al.add(b5);
		al.add(b6);

		Collections.sort(al);

		for (int i = 0; i < al.size(); i++) {
			System.out.println(al.get(i).l + " , " + al.get(i).w + " , " + al.get(i).h);
		}
		
		int tmp[] = new int[al.size()];

		for (int i = 0; i < al.size();i++) {
			tmp[i] = al.get(i).h;
		}
		
		for (int i = 1; i<al.size(); i++) {
			for (int j = 0; j < i; j++) {
				if ((tmp[i] < tmp[j] + al.get(i).h) && (al.get(i).l < al.get(j).l) && (al.get(i).w < al.get(j).w)) {
					tmp[i] =  tmp[j] + al.get(i).h;
				}
			}
		}
		
		int max = 0;
		System.out.println("");
		System.out.println(" .......");
		for (int i = 0; i < al.size();i++) {
			if (max < tmp[i]) {
				max = tmp[i];
			}
			System.out.print(tmp[i] +", ");
		}
		System.out.println("");
		System.out.println(max);
	}
}
