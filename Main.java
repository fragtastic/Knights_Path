import java.util.LinkedList;
import java.util.Random;

public class Main {

	int boardWidth;
	int boardHeight;

	private Node[][] board;

	Node start, end;

	public Main(int boardWidth, int boardHeight, int sX, int sY, int eX, int eY) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		// Init board grid
		System.out.println("Initializing game board");
		buildGrid();
		start = board[sX][sY];
		end = board[eX][eY];
		// build links
		System.out.println("Creating links");
		buildLinks();
		// Start "playing"
		doPathFinding();
	}

	private void doPathFinding() {
		System.out.println("Generating path weights");
		int[][] weights = new int[boardWidth][boardHeight];
		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				weights[x][y] = Integer.MAX_VALUE;
			}
		}

		LinkedList<Node> toVisit = new LinkedList<Node>();
		LinkedList<Node> temp;
		Node f = start;

		toVisit.addAll(start.getNeighbors());
		weights[start.x][start.y] = 0;

		// Generate weights
		while (!toVisit.isEmpty()) {
			Node c = toVisit.pop();
			// Weigh current node
			if (weights[c.x][c.y] > weights[f.x][f.y]) {
				weights[c.x][c.y] = weights[f.x][f.y] + 1;
			}
			// Weigh current neighbors
			for (Node n : c.getNeighbors()) {
				if (weights[n.x][n.y] > weights[c.x][c.y]) {
					weights[n.x][n.y] = weights[c.x][c.y] + 1;
					// add changed neighbors
					toVisit.addLast(n);
				}
			}
			f = c;
		}

		// Prints board weights without extra spaces/newlines
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				System.out.print(weights[x][y]);
				if (x < boardWidth - 1) {
					System.out.print("  ");
				}
			}
			//if (y < boardHeight - 1) {
				System.out.print("\n\n");
			//}
		}

		// find path
		System.out.println("Finding path");
		LinkedList<Node> path = new LinkedList<Node>();
		f = end;
		path.addFirst(f);
		while (f != start) {
			for (Node t : f.getNeighbors()) {
				if (weights[t.x][t.y] < weights[f.x][f.y]) {
					f = t;
				}
			}
			path.addFirst(f);
		}

		// Print path + "map"
		int[][] map = new int[boardWidth][boardHeight];
		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				map[x][y] = -1;
			}
		}
		System.out.print("START -> ");
		for (Node n : path) {
			System.out.print("("+n.x+","+n.y+") -> ");
			map[n.x][n.y] = weights[n.x][n.y];
		}
		System.out.println("END");

		// Print "map"
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				System.out.print((map[x][y] != -1) ? map[x][y] : " ");
				if (x < boardWidth - 1) {
					System.out.print("  ");
				}
			}
			System.out.print("\n\n");
		}

	}

	private void buildGrid() {
		board = new Node[boardWidth][boardHeight];
		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				// + 1 for naming
				board[x][y] = new Node(x, y);
			}
		}
	}

	private void buildLinks() {
		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				genNeighbors(x, y);
			}
		}
	}

	private void genNeighbors(int x, int y) {
		/*
			x-1  y+2		x+1  y+2
		x-2  y+1				x+2  y+1
					START
		x-2  y-1				x+2  y-1
			x-1  y-2	x+1  y-2
		*/
		final int[] d = {
							-1,+2,		+1,+2,
					-2,+1,						+2,+1,
					-2,-1,						+2,-1,
							-1,-2,		+1,-2
					};
		for (int i = 0; i < d.length - 1; i+=2) {
			if (isValidSpot(x, y, d[i], d[i+1])) {
				board[x][y].addNeighbor(board[x+d[i]][y+d[i+1]]);
			}
		}
	}

	private boolean isValidSpot(int x, int y, int dX, int dY) {
		return (x + dX >= 0) && (x + dX < boardWidth) &&
			(y + dY >= 0) && (y + dY < boardHeight);
	}

	public class Node {
		LinkedList<Node> neighbors = new LinkedList<Node>();
		public int x;
		public int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void addNeighbor(Node n) {
			neighbors.add(n);
		}

		public LinkedList<Node> getNeighbors() {
			return neighbors;
		}
	}

	public static void main(String[] args) {
		try {
			if (args.length == 6) {
				new Main(
					Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), Integer.parseInt(args[3]),
					Integer.parseInt(args[4]), Integer.parseInt(args[5])
				);
			} else {

			}
		} catch (Exception e) {
			System.out.println("Invalid arguments");
			System.out.println("\twidth height startX startY endX endY");
		}
	}
}