import java.util.Scanner;
import java.util.LinkedList;

public class SelfDriving2017 {
	int H;//Height
	int W;//Width
	char map[][] = new char[500][500];//map
	
	

	public void input_data()  {
		Scanner sc = new Scanner(System.in);
		H = sc.nextInt();  
		W = sc.nextInt();  
 
		for (int i = 0; i < H; i++) {
			map[i] = sc.next().toCharArray();
		}
	}
	
	class State {
		int h;
		int w;
		int t;
		State(int h, int w, int t) {
			this.h = h;
			this.w = w;
			this.t = t;
		}
	};
	
	int bfs(SelfDriving2017 m) {
		
			
		int hh[] = {-1, 1, 0, 0};
		int ww[] = {0, 0, -1, 1};
		boolean visited[][] = new boolean[500][500];
		
		LinkedList<State> queue = new LinkedList();
		queue.add(new State(0,0,0));
		visited[0][0] = true;
		State s = null;
		while (!queue.isEmpty()) {
			 s = queue.poll();
			int h1;
			int w1;// = s.w;
			int t1;
			
			//System.out.println("h1 = "+h1 +" w1 = "+w1 +" t1 = "+t1);
			
			for (int i = 0; i< 4; i++) {
				h1 = s.h + hh[i];
				w1 = s.w + ww[i];
				t1 = s.t;
				//System.out.println("h1 = "+h1 +" w1 = "+w1 +" t1 = "+t1);
				if (h1 < 0 || h1  >=m.H || w1 < 0 || w1 >=m.W) continue;
				else if (visited[h1][w1]) continue;
				else if(m.map[h1][w1] == 'X')continue;
				else if((h1==H-1) && (w1==W-1)){
					return t1 + 1;
				}
				else {
					queue.add(new State(h1 ,w1,t1+1));
					visited[h1][w1] = true;
				}
			}
		}
		
		return s.t;
	}

	public static void main(String[] args){
		SelfDriving2017 m = new SelfDriving2017();
		m.input_data();
		int  ans=0;

		// Todo : write the code
	
		System.out.println(m.bfs(m));
	}
}