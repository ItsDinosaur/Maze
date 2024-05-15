package code;

import java.util.ArrayList;
import java.util.Objects;


public class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    public boolean equals(int x, int y) {
        return (this.x == x && this.y == y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static boolean czyToNode(char[][] t, int i, int j) {
        int wynik = 0;
        int gora = 0;
        int dol = 0;
        int prawo = 0;
        int lewo = 0;
        if (!GraphMaker.czyToMurT(t, i - 1, j)) {
            wynik++;
            gora = 1;
        }
        if (!GraphMaker.czyToMurT(t, i, j + 1)) {
            wynik++;
            prawo = 1;
        }
        if (!GraphMaker.czyToMurT(t, i + 1, j)) {
            wynik++;
            dol = 1;
        }
        if (!GraphMaker.czyToMurT(t, i, j - 1)) {
            wynik++;
            lewo = 1;
        }
        if ((wynik >= 3) || (t[i][j] == 'P') || (t[i][j] == 'K'))
            return true; // to skrzyzowanie
        if (wynik == 2 && !((gora == 1 && dol == 1) || (prawo == 1 && lewo == 1)))
            return true; // to zakret
        return false;
    }
}