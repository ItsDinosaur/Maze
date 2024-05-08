package code;
import java.util.*;

public class MazeSolver {
    private char[][] maze;
    private Maze mazeFull;
    private static int[][] kierunki = {{1,0},{0,1},{-1,0},{0,-1}};
    private ArrayList<Punkt> rozwiazanie;
    private boolean[][] odwiedzone;

    public class Punkt{
        public int x;
        public int y;

        public Punkt(int x, int y){
            this.x = x;
            this.y = y;
        }

        public boolean equals(Punkt p){
            if (this.x == p.x && this.y == p.y) return true;
            return false;
        }

        public boolean equals(int x, int y){
            if (this.x == x && this.y == y) return true;
            return false;
        }

        public String toString(){
            return "(" + this.x + "," + this.y + ")";
        }
    }

    public MazeSolver(Maze mazeFull){
        this.mazeFull = mazeFull;
        przepiszNaChary(mazeFull);
        printMaze(maze);
    }

    public void przepiszNaChary(Maze mazeFull){
        maze = new char[mazeFull.getXSize()][mazeFull.getYSize()];
        for (int i = 0; i < mazeFull.getXSize(); i++){
            maze[i] = mazeFull.getMazeFromTXT().get(i).toCharArray();
        }
    }

    public void printMaze(char[][] maze){
        for (int i = 0; i < mazeFull.getXSize() ; i++){
                System.out.println(maze[i]);
        }
    }

    private Punkt nowaPozycja (int row, int col, int i, int j){
        return new Punkt(row + i, col + j);    
    }

    private boolean czyToMur(int r, int c){
        return this.maze[r][c] == 'X';
    }

    private boolean czyPrzeszukane(int r, int c){
        return odwiedzone[r][c];
    }

    private boolean czyToWyjscie(int x, int y){
        if (Xwyjscia == x && Ywyjscia == y) return true; //get exit x & y
        return false;
    }

    private boolean czyIstnieje (int r, int c){
        char dummy;
        try{
            dummy = maze[r][c];
        } catch(ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }

    private boolean szukaj(int r, int c, List<Punkt> rozwiazanie, boolean[][] odwiedzone){
        if(!czyIstnieje(r, c)) return false;
        if (czyToMur(r, c) || czyPrzeszukane(r, c)) return false;
        Punkt tmp = new Punkt(r, c);
        rozwiazanie.add(tmp);
        odwiedzone[r][c] = true;

        if(czyToWyjscie(r, c)) return true;

        for (int[] strona : kierunki){
            Punkt pkt = nowaPozycja(r, c, strona[0], strona[1]);
            if (szukaj(pkt.x, pkt.y, rozwiazanie, odwiedzone)) return true;
        }

        rozwiazanie.remove(rozwiazanie.size() - 1);
        return false;
    }

    public List<Punkt> rozwiazLabirynt(){
        rozwiazanie = new ArrayList<Punkt>();
        for (int i = 0; i < odwiedzone.length; i++){
            for (int j = 0; j < odwiedzone[i].length; j++){
                odwiedzone[i][j] = false;
            }
        }
        if (szukaj(Xwejscia,Ywejscia, rozwiazanie, odwiedzone)) return rozwiazanie;

        return Collections.emptyList();
    }
}
