
import java.util.HashSet;
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
    static OurTUI tui;

    private static String helpText = "Type 'load' to load a file, 'solve' to solve a maze, 'print' to print loaded maze, 'help' for help, 'show'/'hide' to show/hide GUI (if enabled), or 'exit' to quit..";
    private static String broaderHelpText = "- \033[34m'load'\033[0m to load a file -- (Me) the program will nicely ask you to provide the path later, you'll see,\n"
               + "- \033[34m'solve'\033[0m to solve a maze -- just type solve and the magic will happen,\n"
               + "- \033[34m'print'\033[0m to print loaded maze -- if no loaded maze Me will be sad you're trying to break it but Me will not lose this fight,\n"
               + "- \033[34m'exit'\033[0m to quit... -- well if you have to kill me... you're breaking my heart..";

    
    public static void main(String args[]) {   
        MazeAppController mac = new MazeAppController();
        try {
            gui = OurGUI.getInstance();
            tui = OurTUI.getInstance();
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }
        observerSubject.addObserver(gui);
        observerSubject.addObserver(tui);
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
    private boolean doITryToLoadFilename = false;
    private void doThingsSuchAsReadCommands() {
        while (true) {
            String command = scanner.nextLine();
            if(doITryToLoadFilename){
                if(command.isBlank()){
                    System.out.println("You haven't provided a path, not loading any file");
                    doITryToLoadFilename = false;
                    continue;
                }
                try{
                    tui.setFilename(command);
                    gui.setParsedPath(command);
                }catch(Exception e){
                    //nothing, probably a listener is turned off
                }
                observerSubject.setState("load");
                doITryToLoadFilename = false;
            }
            if (command.equalsIgnoreCase("load")) {
                doITryToLoadFilename = true;
                System.out.println("Please provide path to the desired file below:");
                continue;
                
            } else if (command.equalsIgnoreCase("solve")) {
                observerSubject.setState("solve");}
            else if (command.equalsIgnoreCase("print")) {
                observerSubject.setState("print");
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("Bye byeee....");
                observerSubject.setState("exit");
                break;
            }
            else if (command.equalsIgnoreCase("show")) {
                gui.state("showGUI");
            }
            else if (command.equalsIgnoreCase("hide")) {
                gui.state("hideGUI");
            }
            else if (command.equalsIgnoreCase("help")){
                System.out.println(broaderHelpText);
            }
            else {
                System.out.println(helpText);
            }
        }
        scanner.close();
    }
}
