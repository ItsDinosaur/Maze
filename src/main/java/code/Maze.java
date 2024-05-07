/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author maciek
 */
public class Maze {

    private ArrayList<String> maze;
    private int rows;
    private int cols;
    private String fileName;
    
    private int[] startVertex;
    private int[] endVertex;

    public Maze(String filePath){
        startVertex = new int[2];
        endVertex = new int[2];
        fileName = filePath;
        maze = new ArrayList<>();
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                maze.add(data);
                rows++;
                cols = data.length();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public int[] getStart(){
        return startVertex;
    }
    public int[] getEnd(){
        return endVertex;
    }

    public ArrayList<String> getMazeFromTXT(){
        return maze;
    }
    public int getXSize(){
        return rows;
    }
    public int getYSize(){
        return cols;
    }

}
