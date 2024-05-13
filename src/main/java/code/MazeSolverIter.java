package code;

import java.util.*;

public class MazeSolverIter {
    private Maze mazefull;
    private char[][] maze;
    private boolean[][] visited;
    private ArrayList<Point> solution;
    private static final int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void przepiszNaChary(Maze mazeFull){
        maze = new char[mazeFull.getXSize()][mazeFull.getYSize()];
        for (int i = 0; i < mazeFull.getXSize(); i++){
            maze[i] = mazeFull.getMazeFromTXT().get(i).toCharArray();
        }
    }

    public MazeSolverIter(Maze mazefull) {
        this.mazefull = mazefull;
        this.visited = new boolean[maze.length][maze[0].length];
        this.solution = new ArrayList<>();
    }

    public ArrayList<Point> solveMaze(Point start, Point end) {
        Stack<Point> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            if (current.x == end.x && current.y == end.y) {
                solution.add(current);
                return solution;
            }
            if (!visited[current.x][current.y]) {
                visited[current.x][current.y] = true;
                solution.add(current);
                for (int[] dir : directions) {
                    int newX = current.x + dir[0];
                    int newY = current.y + dir[1];
                    if (isValid(newX, newY)) {
                        stack.push(new Point(newX, newY));
                    }
                }
            }
        }

        // Return an empty solution if no path found
        return new ArrayList<>();
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] != 'X' && !visited[x][y];
    }
}

