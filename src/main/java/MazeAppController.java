
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maciek
 */
public class MazeAppController {

    private static ObserverSubject observerSubject = new ObserverSubject();
    Scanner scanner = new Scanner(System.in);
    static OurGUI gui;

    private static String helpText = "Type 'show' or 'hide' to manage the display of the GUI, or 'exit' to quit..";
    
    public static void main(String args[]) {   
        MazeAppController mac = new MazeAppController();
        try {
            gui = OurGUI.getInstance();
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }
        observerSubject.addObserver(gui);
        System.out.println("Totally the best maze solver out there (;");
        System.out.println(helpText);
        if(args.length > 0){
            gui.state("showGUI");
            gui.setParsedPath(args[0]);
            gui.state("load");
            gui.state("solve");
            
        }
        mac.doThingsSuchAsReadCommands();
    }

    private void doThingsSuchAsReadCommands() {
        while (true) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("show")) {
                gui.state("showGUI");
            } else if (command.equalsIgnoreCase("hide")) {
                gui.state("hideGUI");
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("Bye byeee....");
                gui.state("exit");
                break;
            } else {
                System.out.println(helpText);
            }
        }
        scanner.close();
    }
}
