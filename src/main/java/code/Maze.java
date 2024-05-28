package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    private ArrayList<String> maze;
    private int rows;
    private int cols;
    private String fileName;
    
    private int[] startVertex;
    private int[] endVertex;

    public char replacerChar = 'X';
    
    

    public Maze(String filePath){

        File file = new File(filePath);
        String extension = "";

        if (file.exists()) {
            fileName = file.getName();
            System.out.println("Najpierw filename to: " + fileName);

            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                System.out.println("Extension: " + extension);
            }
        }
        else {
            System.err.println("Ten plik nie istnieje!");
            System.exit(0);
        }

        if ("bin".equals(extension)){
            BinaryMazeConverter bmc = new BinaryMazeConverter();
            try {
                bmc.binToTxt(filePath);
                filePath = "tmp.txt";
            } catch (IOException e){
                e.printStackTrace();
            }
        }




        startVertex = new int[2];
        endVertex = new int[2];
        fileName = filePath;
        maze = new ArrayList<>();
        cols = 0;
        rows = 0;
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

    public void changeTile(int x, int y, char tile){
        String row = maze.get(x);
        char[] rowArray = row.toCharArray();
        rowArray[y] = tile;
        row = new String(rowArray);
        maze.set(x, row);
    }  
    
    public void setStart(int x, int y){
        // Remove previous one
        changeTile(startVertex[0], startVertex[1], replacerChar);

        // Set up new one
        changeTile(x, y, 'P');
        this.startVertex[0] = x;
        this.startVertex[1] = y;
    }

    public void setEnd(int x, int y){
        // Remove previous one
        changeTile(endVertex[0], endVertex[1], replacerChar);

        // Set up new one
        changeTile(x, y, 'K');
        this.endVertex[0] = x;
        this.endVertex[1] = y;
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

    public void printMaze(){
        for(String s : maze){
            System.out.println(s);
        }
    }
    
    public ArrayList<Node> solveMyself(){
        GraphHandler mz = new GraphHandler(this);
        Thread solver = new Thread(mz);
        solver.start();
        try {
            solver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mz.getSolution();
    }

}
