
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import code.Settings;
import components.MazePanel;
import components.SettingsCustomFrame;
import components.WaitForFilePanel;
import events.MenuEventHandler;
import events.SettingsHandler;
import events.SliderHandler;

import java.awt.Color;
import static java.awt.EventQueue.invokeAndWait;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author maciek
 */
public class OurGUI extends JFrame implements ConsoleObserver {

    /**
     * Creates new form OurGUI
     */
    private MazePanel mazeHolder;
    private SettingsCustomFrame settingsFrame;
    private static Settings sets;
    private boolean isSolving = false;
    private String parsedPath;

    private static OurGUI instance;

    public static OurGUI getInstance() throws Exception {
        if (instance == null) {
            FlatMacDarkLaf.setup();
            invokeAndWait(new Runnable() {
                public void run() {
                    instance = new OurGUI();
                    System.err.println("I created new GUI");
                }
            });
        }
        return instance;
    }

    @Override
    public void state(String currentState) {
        switch (currentState) {
            case "showGUI":
                setVisible(true);
                break;
            case "hideGUI":
                setVisible(false);
                break;
            case "exit":
                exitApp();
                break;
            case "solve":
                mazeHolder.clearMaze();
                addLogEvent("Rozwiazywanie labiryntu...");
                isSolving = true;
                mazeHolder.showCompletedMazeWithoutAnimation();
                break;
            case "load":
                menu1.selectFile(parsedPath);
                break;
            default:
            // Well, totally nothing heeereeee
        }
    }

    public void setParsedPath(String path){
        this.parsedPath = path;
    }

    private void exitApp() {
        try {
            settingsFrame.dispose();
        } catch (Exception e) {
            // Nothin heeeeeere
        }
        System.exit(0);
    }



    private void fileChosenLoadMaze(String parsedPath) {
        menu1.DisplayPath(menu1.getFileNameFromPath(parsedPath));
        mazeHolder = new MazePanel(parsedPath, sets);
        addLogEvent("Wczytano plik: " + menu1.getFileNameFromPath(parsedPath));
        isSolving = false;
        ReloadForm(mazeHolder);
    }

    private void solveButtonClicked() {
        mazeHolder.clearMaze();
        addLogEvent("Rozwiazywanie labiryntu...");
        isSolving = true;
        mazeHolder.showSolvedMaze();
    }

    private OurGUI() {
        sets = new Settings();
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        rootPanel1.initMoving(OurGUI.this);
        int szer = Toolkit.getDefaultToolkit().getScreenSize().width;
        int wys = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((szer - getWidth()) / 2, (wys - getHeight()) / 2);
        menu1.addMenuEvent(new MenuEventHandler() {
            @Override
            public void selected(int option) {
                switch (option) {
                    //File was chosen, reload scroll panel
                    case -1:
                        fileChosenLoadMaze(menu1.getFilePath());
                        break;
                    //Solve button was clicked, lock buttons, solve maze
                    case 0:
                        solveButtonClicked();
                        break;
                    // Clear button was clicked, clear maze from solved
                    case 1:
                        addLogEvent("Czyszczenie labiryntu...");
                        mazeHolder.clearMaze();
                        isSolving=false;
                        break;
                    // Set Start button was clicked, set start
                    case 2:
                        mazeHolder.startMouseListener(0);
                        break;
                    // Set End button was clicked, set end
                    case 3:
                        mazeHolder.startMouseListener(1);
                        break;
                    // Export button was clicked, export maze
                    case 4:
                        addLogEvent("Eksportowanie labiryntu...");
                        try {
                            File file = new File(menu1.getPathToExport());
                            ImageIO.write(mazeHolder.getScaledImage(2048, 2048), "png", file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    //Settings button was clicked, open settings
                    case -10:
                        settingsFrame = new SettingsCustomFrame(sets);
                        settingsFrame.setVisible(true);
                        settingsFrame.setAlwaysOnTop(true);
                        settingsFrame.addSettingEvent(new SettingsHandler() {
                            @Override
                            public void settingsSelected(int settingID, boolean value) {
                                switch (settingID) {
                                    case 0:
                                        OurGUI.this.addLogEvent("Zmieniono ustawienie animacji na " + (value ? "wyłącz" : "wlącz"));
                                        menu1.enableSlider(!value);
                                        sets.setDoAnimation(!value);
                                        mazeHolder.updateSettings(sets);
                                        if (isSolving) {
                                            mazeHolder.showSolvedMaze();
                                        }
                                        break;
                                    default:
                                        // Nothin heeeeeere
                                        break;
                                }
                            }

                            @Override
                            public void settingsSetValue(int settingID, int value) {
                                switch (settingID) {
                                    case 0:
                                        OurGUI.this.addLogEvent("Zmieniono rozmiar kafelka na " + value);
                                        sets.setTileSize(value);
                                        mazeHolder.updateSettings(sets);
                                        break;
                                    default:
                                        // Nothin heeeeeere
                                        break;
                                }
                            }
                        });
                        break;
                    default:
                        // Nothin heeeeeere
                        break;
                }
            }
        });
        menu1.addSliderEvent(new SliderHandler() {
            @Override
            public void setValue(int value) {
                mazeHolder.setAnimationSpeed(value);
            }
        });

        ReloadForm(new WaitForFilePanel());

    }

    public void addLogEvent(String event) {
        LogHolder.setText("<html>" + event + "<br/>" + LogHolder.getText() + "</html>");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel1 = new components.RootPanel();
        ExitButton = new javax.swing.JButton();
        menu1 = new components.Menu();
        jScrollPane1 = new javax.swing.JScrollPane();
        LogHolderScrollPane = new javax.swing.JScrollPane();
        LogHolder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BestLookinMazeSolver");
        setUndecorated(true);

        rootPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));

        ExitButton.setBackground(new java.awt.Color(102, 0, 51));
        ExitButton.setFont(new java.awt.Font("AnonymicePro Nerd Font", 1, 24)); // NOI18N
        ExitButton.setText("X");
        ExitButton.setFont(new Font("Comic Sans", Font.BOLD, 20));
        ExitButton.setBorderPainted(false);
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setMaximumSize(new java.awt.Dimension(520, 520));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(513, 513));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(513, 513));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        jScrollPane1.getHorizontalScrollBar().setUnitIncrement(10);

        LogHolderScrollPane.setViewportBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));

        LogHolder.setFont(new java.awt.Font("AnonymicePro Nerd Font", 0, 14)); // NOI18N
        LogHolder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogHolderScrollPane.setViewportView(LogHolder);

        javax.swing.GroupLayout rootPanel1Layout = new javax.swing.GroupLayout(rootPanel1);
        rootPanel1.setLayout(rootPanel1Layout);
        rootPanel1Layout.setHorizontalGroup(
            rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanel1Layout.createSequentialGroup()
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(rootPanel1Layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(LogHolderScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(244, Short.MAX_VALUE))))
        );
        rootPanel1Layout.setVerticalGroup(
            rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(rootPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(LogHolderScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        exitApp();
    }//GEN-LAST:event_ExitButtonActionPerformed

    public void ReloadForm(JComponent comp) {
        jScrollPane1.setViewportView(comp);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExitButton;
    private javax.swing.JLabel LogHolder;
    private javax.swing.JScrollPane LogHolderScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private components.Menu menu1;
    private components.RootPanel rootPanel1;
    // End of variables declaration//GEN-END:variables
}
