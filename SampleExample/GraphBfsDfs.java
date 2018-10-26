import java.util.LinkedList;
import java.util.ListIterator;

public class GraphBfsDfs {

	int v;
	LinkedList<Integer> adj[];

	public GraphBfsDfs(int v) {
		// TODO Auto-generated constructor stub
		this.v = v;

		adj = new LinkedList[v];

		for (int i = 0; i < v; i++) {
			adj[i] = new LinkedList<>();
		}
	}

	void addEdge(int v, int w) {
		adj[v].add(w);
	}

	void bfs(int s) {

		LinkedList<Integer> queue = new LinkedList<>();

		boolean Visited[] = new boolean[v];
		Visited[s] = true;
		queue.add(s);

		while (!queue.isEmpty()) {
			int res = queue.poll();

			System.out.print(res + ", ");

			ListIterator<Integer> iterator = adj[res].listIterator();
			while (iterator.hasNext()) {
				int n = iterator.next();

				if (!Visited[n]) {
					Visited[n] = true;
					queue.add(n);
				}

			}
		}
	}

	void dfs(int s) {

		LinkedList<Integer> stack = new LinkedList<>();
		boolean visited[] = new boolean[v];

		stack.push(s);

		while (!stack.isEmpty()) {
			int res = stack.pop();

			if (!visited[res]) {
				System.out.print(res + " ");
				visited[res] = true;
			}

			ListIterator<Integer> it = adj[res].listIterator();
			while (it.hasNext()) {
				int n = it.next();
				if (!visited[n]) {
					stack.push(n);
				}
			}
		}

	}

	public static void main(String[] args) {

		GraphBfsDfs g = new GraphBfsDfs(4);

		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 3);

		System.out.println("Following is Breadth First Traversal " + "(starting from vertex 2)");

		g.bfs(2);
		System.out.println("");

		System.out.println("Following is Depth First Traversal " + "(starting from vertex 2)");

		g.dfs(2);
	}
}
