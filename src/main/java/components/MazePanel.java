/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import code.Maze;
import code.Settings;
import code.Node;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

import org.w3c.dom.events.MouseEvent;

/**
 *
 * @author maciek
 */
public class MazePanel extends javax.swing.JPanel {
    
    private int rows;
    private int cols;
    private ArrayList<String> maze;
    private ArrayList<String> solvedMaze;
    public int tileSize;
    private Maze mainMaze;
    private int animationSpeed = 100;
    public boolean doAnimation;
    private BufferedImage image;
    private int cellToChange;//0 - start, 1 - end
    
    public void updateSettings(Settings settings) {
        tileSize = settings.getTileSize();
        doAnimation = settings.isDoAnimation();
        setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        System.out.println("I will reload SolvedMaze");
        reloadMazeOnGUI(solvedMaze);
        for (String s : solvedMaze){
            System.out.println(s);
        }
    }
    
    public void reloadMazeOnGUI(ArrayList<String> mazeToDraw) {
        image = createImage(mazeToDraw);
        repaint();
    }
    
    public void clearMaze() {
        try{
            timer.stop();
        }catch(Exception e){
            //Perfectly normal
        }
        solvedMaze = new ArrayList<>();
        for (String s : maze) {
            solvedMaze.add(s);
        }
        System.out.println("I've cleared solvedMaze, now it should be normal maze");
        reloadMazeOnGUI(maze);
    }
    
    public BufferedImage getScaledImage(int w, int h) {
        int original_width = image.getWidth();
        int original_height = image.getHeight();
        int bound_width = w;
        int bound_height = h;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }
        
        BufferedImage resizedImg = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, new_width, new_height);
        g2.drawImage(image, 0, 0, new_width, new_height, null);
        g2.dispose();
        return resizedImg;
    }
    
    public MazePanel(String path, Settings settings) {
        tileSize = settings.getTileSize();
        doAnimation = settings.isDoAnimation();
        mainMaze = new Maze(path);
        maze = mainMaze.getMazeFromTXT();
        rows = mainMaze.getXSize();
        cols = mainMaze.getYSize();
        //Deep copy of solved maze
        solvedMaze = new ArrayList<>();
        for (String s : maze) {
            solvedMaze.add(s);
        }
        
        setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        initComponents();
        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(mouseListenerIsActive){
                    int y = e.getX() / tileSize;
                    int x = e.getY() / tileSize;
                    if(cellToChange == 0){
                        mainMaze.setStart(x, y);
                        clearMaze();
                    } else if(cellToChange == 1){
                        mainMaze.setEnd(x, y);
                        clearMaze();
                    }
                    cellToChange = -10;
                    stopMouseListener();
                }   
            }
        });
    }
    
    private BufferedImage createImage(ArrayList<String> mazeToDraw) {
        BufferedImage image = new BufferedImage(cols * tileSize, rows * tileSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int i = 0;
        int j = 0;
        for (String s : mazeToDraw) {
            char[] temp = s.toCharArray();
            for (j = 0; j < cols; j++) {
                switch (temp[j]) {
                    case 'P':
                        g2.setPaint(Color.yellow);
                        break;
                    case ' ':
                        g2.setPaint(Color.white);
                        break;
                    case 'X':
                        g2.setPaint(Color.black);
                        break;
                    case 'K':
                        g2.setPaint(Color.blue);
                        break;
                    default:
                        g2.setPaint(Color.red);
                        break;
                }
                g2.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
            i++;
        }
        g2.dispose();
        return image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = createImage(maze);
        }
        g.drawImage(image, 0, 0, null);
    }
    
    public void setAnimationSpeed(int speed) {
        animationSpeed = speed;
    }
    private Timer timer;
    public void showSolvedMaze() {        
        
        if (doAnimation) {
            ArrayList<Node> temp = mainMaze.solveMyself();
            timer = new Timer(1000 / animationSpeed, new ActionListener() {
                int index = 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (index < temp.size() - 1) {
                        
                        Node point = temp.get(index);
                        BufferedImage updatedImage = new BufferedImage(cols * tileSize, rows * tileSize, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2 = updatedImage.createGraphics();
                        g2.drawImage(image, 0, 0, null);
                        g2.setPaint(Color.red);
                        
                        g2.fillRect(point.getY() * tileSize, point.getX() * tileSize, tileSize, tileSize);
                        g2.dispose();
                        solvedMaze.set(point.getX(), solvedMaze.get(point.getX()).substring(0, point.getY()) + "#" + solvedMaze.get(point.getX()).substring(point.getY() + 1));
                        image = updatedImage;
                        repaint();
                        index++;
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        } else {
            showCompletedMazeWithoutAnimation();
        }
        
    }
    
    public void showCompletedMazeWithoutAnimation() {
        ArrayList<Node> temp = mainMaze.solveMyself();
        BufferedImage updatedImage = new BufferedImage(cols * tileSize, rows * tileSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = updatedImage.createGraphics();
        for (int i = 1; i < temp.size() - 1; i++) {
            Node point = temp.get(i);
            g2.drawImage(image, 0, 0, null);
            g2.setPaint(Color.red);
            solvedMaze.set(point.getX(), solvedMaze.get(point.getX()).substring(0, point.getY()) + "#" + solvedMaze.get(point.getX()).substring(point.getY() + 1));
            
            g2.fillRect(point.getY() * tileSize, point.getX() * tileSize, tileSize, tileSize);
            image = updatedImage;
        }        
        g2.dispose();
        
        repaint();        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(20000, 200000));
        setMinimumSize(new java.awt.Dimension(2, 2));
        setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private boolean mouseListenerIsActive;

    public void startMouseListener (int option) {
        cellToChange = option;
        mouseListenerIsActive = true;
    }

    public void stopMouseListener () {
        mouseListenerIsActive = false;
    }



}
