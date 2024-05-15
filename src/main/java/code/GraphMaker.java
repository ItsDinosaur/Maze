package code;

import java.util.*;
import code.Node;

public class GraphMaker implements Runnable {
    private char[][] maze;
    private Maze mazeFull;
    private static final int[][] kierunki = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    private ArrayList<Node> rozwiazanie;
    private boolean[][] odwiedzone;
    private HashMap<Node, Node> graf;
    private Node poczatek;
    private Node koniec;
    private Stack<Node> stos;

    public GraphMaker(Maze mazeFull) {
        this.mazeFull = mazeFull;
        przepiszNaChary(mazeFull);
        printMaze(maze);
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

    public static boolean czyToMurT(char[][] t, int r, int c) {
        try {
            return t[r][c] == 'X';
        } catch (ArrayIndexOutOfBoundsException ex) {
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

    public ArrayList<Node> stworzGraf() {

        odwiedzone = new boolean[mazeFull.getXSize()][mazeFull.getYSize()];
        rozwiazanie = new ArrayList<Node>();
        przepiszNaChary(mazeFull);
        graf = new HashMap<Node, Node>(); // <dziecko,rodzic>, dziecko jest kluczem

        // tymczasowe od tad
        for (int i = 0; i < odwiedzone.length; i++) {
            for (int j = 0; j < odwiedzone[i].length; j++) {
                if (maze[i][j] == 'P') {
                    mazeFull.setStart(i, j);
                    poczatek = new Node(i, j);
                }
                if (maze[i][j] == 'K') {
                    mazeFull.setEnd(i, j);
                    koniec = new Node(i, j);
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
        odwiedzone[poczatek.x][poczatek.y] = true;

        stos = new Stack<Node>();
        stos.push(poczatek);
        while (!stos.isEmpty()) {
            Node obecny = stos.pop();
            //System.out.println("Rozwazam punkt " + obecny.x + " " + obecny.y);
            odwiedzone[obecny.x][obecny.y] = true;

            for (int[] strona : kierunki) {
                Node nodzik = nowaPozycja(obecny.x, obecny.y, strona[0], strona[1]);
                if (czyIstnieje(nodzik.x, nodzik.y)) {
                    if (!czyToMur(nodzik.x, nodzik.y) && !czyPrzeszukane(nodzik.x, nodzik.y)) {
                        stos.push(nodzik);
                        graf.put(nodzik, obecny);
                    }
                }
            }
        }

        // wyznacz sciezke od konca do poczatku
        Node nod = this.koniec;
        rozwiazanie.add(nod);
        while (graf.get(nod) != null) {
            nod = graf.get(nod);
            rozwiazanie.add(nod);
            //System.out.println("Dodaje do rozwiazania " + nod);
        }
        Collections.reverse(rozwiazanie);

        return rozwiazanie;
    }

    public void wypiszRozwiazanie() {
        System.out.println("Wypisuje rozwiazanie:");
        for (Node p : this.rozwiazanie) {
            System.out.println(p);
        }
    }

    public void run() {
        try {
            stworzGraf();
            //this.wypiszRozwiazanie();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tymczasowo to nie rozwiazanie
    public ArrayList<Node> getRozwiazanie() {
        return rozwiazanie;
    }
}
