package code;
import java.util.*;

public class MazeSolver implements Runnable {
    private char[][] maze;
    private Maze mazeFull;
    private static final int[][] kierunki = {{1,0},{0,1},{-1,0},{0,-1}};
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
            return (this.x == p.x && this.y == p.y);
        }

        public boolean equals(int x, int y){
            return (this.x == x && this.y == y);
        }

        public String toString(){
            return "(" + this.x + "," + this.y + ")";
        }
    }

    public MazeSolver(Maze mazeFull){
        this.mazeFull = mazeFull;
        przepiszNaChary(mazeFull);
        //printMaze(maze);
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
        return (mazeFull.getEnd()[0] == x && mazeFull.getEnd()[1] == y);
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

    private boolean szukaj(int r, int c, ArrayList<Punkt> rozwiazanie, boolean[][] odwiedzone){
        //System.out.println("Rozwazam punkt " + r + " " + c);
        if(!czyIstnieje(r, c)){
            //System.out.println("Punkt " + r + " " + c + "nie istnieje");
            return false;
        }
        if (czyToMur(r, c) || czyPrzeszukane(r, c)) return false;
        Punkt tmp = new Punkt(r, c);
        rozwiazanie.add(tmp);
        //System.out.println("Dodaje do rozwiazania punkt " + r + " " + c);
        odwiedzone[r][c] = true;

        if(czyToWyjscie(r, c)) return true;

        for (int[] strona : kierunki){
            Punkt pkt = nowaPozycja(r, c, strona[0], strona[1]);
            if (szukaj(pkt.x, pkt.y, rozwiazanie, odwiedzone)) return true;
        }

        //System.out.println("Usuwam punkt " + rozwiazanie.get(rozwiazanie.size()-1));
        rozwiazanie.remove(rozwiazanie.size() - 1);
        return false;
    }

    public ArrayList<Punkt> rozwiazLabirynt(){

        odwiedzone = new boolean[mazeFull.getXSize()][mazeFull.getYSize()];
        rozwiazanie = new ArrayList<Punkt>();
        przepiszNaChary(mazeFull);

        //tymczasowe od tad
        for (int i = 0; i < odwiedzone.length; i++){
            for (int j = 0; j < odwiedzone[i].length; j++){
                if(maze[i][j] == 'P'){
                    mazeFull.setStart(i, j);
                }
                if(maze[i][j] == 'K'){
                    mazeFull.setEnd(i, j);
                }
            }
        }     
        //tymczasowe do tad, bedzie mozna usunac gdy w klasa Maze bedzie sama znajdowala poczatek i koniec

        for (int i = 0; i < odwiedzone.length; i++){
            for (int j = 0; j < odwiedzone[i].length; j++){
                odwiedzone[i][j] = false;
            }
        }
        if (szukaj(mazeFull.getStart()[0],mazeFull.getStart()[1], rozwiazanie, odwiedzone)) return rozwiazanie;
        ArrayList<Punkt> puste = new ArrayList<>();
        return puste;
    }

    public void wypiszRozwiazanie(){
        for (Punkt p : this.rozwiazanie){
            System.out.println(p);
        }
    }

    public ArrayList<Punkt> getRozwiazanie(){
        return this.rozwiazanie;
    }

    public void run(){
        try {
            rozwiazLabirynt();
            //this.wypiszRozwiazanie();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
