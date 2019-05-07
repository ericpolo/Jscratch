
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    public JButton toolSelected = null;
    public JButton colorSelected = null;
    public JButton thickSelected = null;
    public String tool = "";
    public Color  color = Color.black;
    public int thickness = 0;
    public int dir = 0;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList<Observer>();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void updateButton(JButton bt){
        if(toolSelected!=null){
            toolSelected.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        }
        toolSelected = bt;
        tool = bt.getText();
        bt.setBorder(BorderFactory.createLineBorder(Color.black,2));
        notifyObservers();
    }

    /**
     * blue:0000FF
     * red: FF0000
     * green:00cc00
     * yellow: ffff00
     * black: 000000
     * white: FFFFFF
     */
    public void updateColor(JButton bt, Vector<JButton> lb){
        JButton lastItem = lb.get(lb.size()-1);
        if(bt!=lastItem) {
            Color c = Color.decode("#" + bt.getText());
            if (colorSelected != null) {
                colorSelected.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
            }
            colorSelected = bt;
            color = c;
            bt.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        }
        lastItem.setBorder(BorderFactory.createLineBorder(color,4));
        notifyObservers();
    }


    public void updateThick(JButton bt){
        if(thickSelected!=null){
            thickSelected.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        }
        thickSelected = bt;
        thickness = Integer.parseInt(bt.getText());
        bt.setBorder(BorderFactory.createLineBorder(Color.black,2));
        notifyObservers();
    }

    public void update(Color col, int thick){
        color = col;
        thickness = thick;
        notifyObservers();
    }
    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}
