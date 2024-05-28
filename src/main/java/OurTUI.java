
import code.Maze;
import code.Node;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author kai
 */
public class OurTUI implements Observer{

    private static OurTUI instance;
    
    
    public static OurTUI getInstance(){
        if(instance == null){
            instance = new OurTUI();
        }
        return instance;
    }
    
    @Override
    public void state(String currentState) {
        
        switch(currentState){
            case "load":
                isSolved = false;
                maze = new Maze(filename);
                notSolvedMaze = maze.getMazeFromTXT();
                System.out.println("Maze was loaded");
                break;
            case "solve":
                setSolvedMaze(maze.solveMyself());
                isSolved = true;
                System.out.println("Maze was solved!  Hurray!");
                break;
            case "print":
                if(notSolvedMaze == null){
                    System.out.println("You haven't loaded any maze yet O.o");
                    break;
                }
                printMaze(isSolved ? solvedMaze : notSolvedMaze);
                break;
            default:
                //nope
        }
        
    }

    private String filename;
    Maze maze;
    ArrayList<String> solvedMaze;
    ArrayList<String> notSolvedMaze;
    private boolean isSolved = false;
    public void setFilename(String s){
        filename = s;
    }
    private void printMaze(ArrayList<String> maze){
        System.out.println("Maze looks somewhat like this:");
        for (String s : maze) {
            System.out.println(s);
        }
    }
    private void setSolvedMaze(ArrayList<Node> solutionArray){
        solvedMaze = new ArrayList<>();
        for (String s : notSolvedMaze) {
            solvedMaze.add(s);
        }
        for(int i=1; i<solutionArray.size()-1; i++){
            Node point = solutionArray.get(i);
            solvedMaze.set(point.getX(), solvedMaze.get(point.getX()).substring(0, point.getY()) + "#" + solvedMaze.get(point.getX()).substring(point.getY() + 1));
        }
    }
    
}
