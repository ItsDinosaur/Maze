/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import events.MenuEventHandler;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 *
 * @author maciek
 */
public class Menu extends javax.swing.JPanel {

    /**
     * Creates new form Menu
     */

    private String pathToFile;
    private MenuEventHandler event;
    
    public void addMenuEvent(MenuEventHandler event){
        this.event = event;
    }
    
    public String getFilePath(){
        return pathToFile;
    }
    
    public void DisplayPath(String path){
        FileNameDisplay.setText(path);
    }
    
    public String getFileNameFromPath(String fullPath){
        return Paths.get(fullPath).getFileName().toString();
    }
     
    public Menu() {
        initComponents();
        setOpaque(false);
        initMenuItems();
    }
    
    void setupItem(MenuItem item){
        item.addMouseListener(new MouseAdapter(){
            /*@Override
            public void mouseEntered(MouseEvent e){
                item.paintBackround(new Color(200,200,200,40));
            }
            @Override
            public void mouseExited(MouseEvent e){
                item.paintBackround(UIManager.getColor("control"));
            }*/
            @Override
            public void mouseClicked(MouseEvent e){
                event.selected(item.getLabelText());
                
            }
        });
        OptionPanel.add(item);
    }
    
    void initMenuItems(){
        setupItem(new MenuItem("SOLVE"));
        setupItem(new MenuItem("SET START"));
        setupItem(new MenuItem("SET FINISH"));
        
    }

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.cyan, 0, getHeight(), Color.darkGray);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptionPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        SetFileButton = new javax.swing.JButton();
        FileNameDisplay = new javax.swing.JLabel();

        OptionPanel.setOpaque(false);
        OptionPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        SetFileButton.setText("File");
        SetFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetFileButtonActionPerformed(evt);
            }
        });

        FileNameDisplay.setForeground(new java.awt.Color(240, 240, 240));
        FileNameDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FileNameDisplay.setText("Filename");
        FileNameDisplay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FileNameDisplay.setMaximumSize(new java.awt.Dimension(55, 120));
        FileNameDisplay.setMinimumSize(new java.awt.Dimension(55, 120));
        FileNameDisplay.setPreferredSize(new java.awt.Dimension(55, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SetFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(FileNameDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SetFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FileNameDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(382, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SetFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetFileButtonActionPerformed
        String dir = System.getProperty("user.dir");
        
        JFileChooser j = new JFileChooser(dir);
 
            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);
 
            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION)
 
            {
                // set the label to the path of the selected file
                pathToFile = (j.getSelectedFile().getPath());
                event.selected("NewFile");
            }
            // if the user cancelled the operation
            else
                ;
                //do literally nothing
        
    }//GEN-LAST:event_SetFileButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FileNameDisplay;
    private javax.swing.JPanel OptionPanel;
    private javax.swing.JButton SetFileButton;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
