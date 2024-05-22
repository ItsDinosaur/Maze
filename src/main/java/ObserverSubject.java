
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maciek
 */
public class ObserverSubject {
    private ArrayList<ConsoleObserver> observers = new ArrayList<>();
    private String state;

    public void addObserver(ConsoleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ConsoleObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ConsoleObserver observer : observers) {
            observer.state(state);
        }
    }

    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }
}
