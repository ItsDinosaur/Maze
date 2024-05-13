package code;

import java.util.*;
import code.Node;

public class GraphMaker implements Runnable {
    private char[][] maze;
    private Maze mazeFull;
    private static final int[][] kierunki = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    private ArrayList<Node> listaNodow;
    private boolean[][] odwiedzone;

    public GraphMaker(Maze mazeFull) {
        this.mazeFull = mazeFull;
        przepiszNaChary(mazeFull);
        // printMaze(maze);
    }

    public void przepiszNaChary(Maze mazeFull) {
        maze = new char[mazeFull.getXSize()][mazeFull.getYSize()];
        for (int i = 0; i < mazeFull.getXSize(); i++) {
            maze[i] = mazeFull.getMazeFromTXT().get(i).toCharArray();
        }
    }

    public void printMaze(char[][] maze) {
        for (int i = 0; i < mazeFull.getXSize(); i++) {
            System.out.println(maze[i]);
        }
    }

    private Node nowaPozycja(int row, int col, int i, int j) {
        return new Node(row + i, col + j);
    }

    private boolean czyToMur(int r, int c) {
        return this.maze[r][c] == 'X';
    }
    
    public static boolean czyToMurT(char[][] t ,int r, int c) {
        try{
        return t[r][c] == 'X';
        }
        catch (ArrayIndexOutOfBoundsException ex){
            return true;
        }
    }

    private boolean czyPrzeszukane(int r, int c) {
        return odwiedzone[r][c];
    }

    private boolean czyToWyjscie(int x, int y) {
        return (mazeFull.getEnd()[0] == x && mazeFull.getEnd()[1] == y);
    }

    public boolean czyIstnieje(int r, int c) {
        char dummy;
        try {
            dummy = maze[r][c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }

    private void szukaj(int r, int c, Node currentRodzic) {
        int kontrolka = 0;
        System.out.println("Rozwazam punkt " + r + " " + c);
        if (!czyIstnieje(r, c)) kontrolka = 1;
        if (kontrolka == 0){
        if (czyToMur(r, c) || czyPrzeszukane(r, c)) kontrolka = 1;
        }
        
        if (kontrolka == 0) {
            Node tmp = new Node(r, c);
            // System.out.println("Dodaje do rozwiazania punkt " + r + " " + c);
            odwiedzone[r][c] = true;

            if(Node.czyToNode(maze,r,c)){
                listaNodow.add(tmp);
                if (currentRodzic != null){
                currentRodzic.dzieci.add(tmp);
                }
                currentRodzic = tmp;
            }

            for (int[] strona : kierunki) {
                Node nodzik = nowaPozycja(r, c, strona[0], strona[1]);
                szukaj(nodzik.x, nodzik.y, currentRodzic);
            }
        }
    }

    public ArrayList<Node> stworzGraf() {

        odwiedzone = new boolean[mazeFull.getXSize()][mazeFull.getYSize()];
        listaNodow = new ArrayList<Node>();
        przepiszNaChary(mazeFull);

        // tymczasowe od tad
        for (int i = 0; i < odwiedzone.length; i++) {
            for (int j = 0; j < odwiedzone[i].length; j++) {
                if (maze[i][j] == 'P') {
                    mazeFull.setStart(i, j);
                }
                if (maze[i][j] == 'K') {
                    mazeFull.setEnd(i, j);
                }
            }
        }
        // tymczasowe do tad, bedzie mozna usunac gdy w klasa Maze bedzie sama
        // znajdowala poczatek i koniec

        for (int i = 0; i < odwiedzone.length; i++) {
            for (int j = 0; j < odwiedzone[i].length; j++) {
                odwiedzone[i][j] = false;
            }
        }

        Node poczatek = new Node(mazeFull.getStart()[0], mazeFull.getStart()[1]);

        szukaj(poczatek.x, poczatek.y, poczatek);
        
        return listaNodow;
    }

    public void wypiszListeNodow() {
        for (Node p : this.listaNodow) {
            System.out.println(p);
        }
    }

    /*public ArrayList<Node> getRozwiazanie() {
        return this.rozwiazanie;
    }*/

    public void run() {
        try {
            stworzGraf();
            this.wypiszListeNodow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tymczasowo to nie rozwiazanie
    public ArrayList<Node> getRozwiazanie(){
        return listaNodow;
    }
}
