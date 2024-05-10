/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import code.Maze;
import code.MazeSolver;
import code.MazeSolver.Punkt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author maciek
 */
public class MazePanel extends javax.swing.JPanel {

    private int rows;
    private int cols;
    private ArrayList<String> maze;
    private int tileSize = 7;
    private Maze mainMaze;
    int animationSpeed = 100;

    public MazePanel(String path) {
        mainMaze = new Maze(path);
        maze = mainMaze.getMazeFromTXT();
        rows = mainMaze.getXSize();
        cols = mainMaze.getYSize();

        setPreferredSize(new Dimension(rows * tileSize, cols * tileSize));

        initComponents();
    }
    
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(cols * tileSize, rows * tileSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int i = 0;
        int j = 0;
        for (String s : maze) {
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
    
    private BufferedImage image;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = createImage();
        }
        g.drawImage(image, 0, 0, null);
    }
    
    public void setAnimationDelay(int delay) {
        animationSpeed = delay;
    }

    public void solveMaze(){
        MazeSolver mz = new MazeSolver(mainMaze);
        Thread solver = new Thread(mz);
        solver.start();
        try {
            solver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Punkt> temp = mz.getRozwiazanie();
        Timer timer = new Timer(1000/animationSpeed, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
            if (index < temp.size()) {
                Punkt point = temp.get(index);
                BufferedImage updatedImage = new BufferedImage(cols * tileSize, rows * tileSize, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = updatedImage.createGraphics();
                g2.drawImage(image, 0, 0, null);
                g2.setPaint(Color.red);

                g2.fillRect(point.y * tileSize, point.x * tileSize, tileSize, tileSize);
                g2.dispose();
                image = updatedImage;
                repaint();
                index++;
            } else {
                ((Timer) e.getSource()).stop();
            }
            }
        });
        timer.start();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(20000, 200000));
        setMinimumSize(new java.awt.Dimension(2, 2));
        setName(""); // NOI18N

    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
