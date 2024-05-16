package code;

import java.util.*;

public class GraphHandler implements Runnable {
    private char[][] maze;
    private Maze mazeFull;
    private static final int[][] directions = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    private ArrayList<Node> solution;
    private boolean[][] visited;
    private HashMap<Node, Node> graph;
    private Node startNode;
    private Node endNode;
    private Queue<Node> queue;

    public GraphHandler(Maze mazeFull) {
        this.mazeFull = mazeFull;
        makeCharFromString(mazeFull);
    }

    public void makeCharFromString(Maze mazeFull) {
        maze = new char[mazeFull.getXSize()][mazeFull.getYSize()];
        for (int i = 0; i < mazeFull.getXSize(); i++) {
            maze[i] = mazeFull.getMazeFromTXT().get(i).toCharArray();
        }
    }

    private Node newPosition(int row, int col, int i, int j) {
        return new Node(row + i, col + j);
    }

    private boolean isWall(int r, int c) {
        return this.maze[r][c] == 'X';
    }

    private boolean isVisited(int r, int c) {
        return visited[r][c];
    }

    public boolean exists(int r, int c) {
        try {
            char dummy = maze[r][c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }

    private void makeGraph() {
        visited = new boolean[mazeFull.getXSize()][mazeFull.getYSize()];
        makeCharFromString(mazeFull);
        graph = new HashMap<Node, Node>(); // <dziecko,rodzic>, dziecko jest kluczem

        // tymczasowe od tad
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (maze[i][j] == 'P') {
                    mazeFull.setStart(i, j);
                    startNode = new Node(i, j);
                }
                if (maze[i][j] == 'K') {
                    mazeFull.setEnd(i, j);
                    endNode = new Node(i, j);
                }
            }
        }
        // tymczasowe do tad, bedzie mozna usunac gdy w klasa Maze bedzie sama
        // znajdowala startNode i endNode

        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
        visited[startNode.getX()][startNode.getY()] = true;

        queue = new LinkedList<Node>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            visited[current.getX()][current.getY()] = true;

            for (int[] direction : directions) {
                Node nodzik = newPosition(current.getX(), current.getY(), direction[0], direction[1]);
                if (exists(nodzik.getX(), nodzik.getY())) {
                    if (!isWall(nodzik.getX(), nodzik.getY()) && !isVisited(nodzik.getX(), nodzik.getY())) {
                        queue.add(nodzik);
                        graph.put(nodzik, current);
                    }
                }
            }
        }
    }

    private ArrayList<Node> solveGraph(){
        solution = new ArrayList<Node>();
        Node nod = this.endNode;
        solution.add(nod);
        while (graph.get(nod) != null) {
            nod = graph.get(nod);
            solution.add(nod);
        }
        Collections.reverse(solution);

        return solution;
    }

    public void run() {
        try {
            makeGraph();
            solveGraph();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Node> getSolution() {
        return solution;
    }
}
