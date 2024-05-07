/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import code.Maze;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author maciek
 */
public class MazePanel extends javax.swing.JPanel{

    private int rows;
    private int cols;
    private ArrayList<String> maze;
    private int tileSize = 10;


    public MazePanel(String path) {
        Maze mainMaze = new Maze(path);
        maze = mainMaze.getMazeFromTXT();
        rows = mainMaze.getXSize();
        cols = mainMaze.getYSize();
        setPreferredSize(new Dimension(rows*tileSize,cols*tileSize));
        System.out.println(getPreferredSize());
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int i = 0;
        int j=0;
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
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(20000, 200000));
        setMinimumSize(new java.awt.Dimension(2, 2));
        setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
