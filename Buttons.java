import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Buttons extends JPanel implements Observer {
    private Model model;
    private JPanel buttonPanel = new JPanel();
    private JPanel colorPanel = new JPanel();
    private JPanel thickPanel = new JPanel();

    /**
     *  Fields used in Buttons
     *  buttonsTool: a list of buttons (select/rectangle/circle/fill/eraser)
     *  buttonsColor: a list of 6+ colors
     *  buttonsThick: a list of button used to select thickness
     */
    public Vector<JButton> tool=new Vector<>();
    public Vector<JButton> color=new Vector<>();
    public Vector<JButton> thick=new Vector<>();


    /**
     * Create a new Canvas.
     */
    public Buttons(Model model) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(150,600));
        this.setBorder(BorderFactory.createLineBorder(Color.black,3));

        buttonPanel.setPreferredSize(new Dimension(140, 180));
        buttonPanel.setLayout(new FlowLayout());
        colorPanel.setPreferredSize(new Dimension(140, 170));
        colorPanel.setLayout(new FlowLayout());
        thickPanel.setPreferredSize(new Dimension(140,120));
        thickPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        thickPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.add(buttonPanel);
        this.add(colorPanel);
        this.add(thickPanel);

        // initializing tool list
        for(String s: new String[]{"select","eraser","line","circle","rec","fill"}){
            JButton bt = new JButton(new ImageIcon("images/"+s+".png"));
            bt.setText(s);
            if(s.equals("select")) model.updateButton(bt);
            bt.setPreferredSize(new Dimension(60,50));
            bt.addActionListener(new ToolActionListener());
            tool.add(bt);
            buttonPanel.add(bt);
        }
        // initializing color list
        JButton chooser = new JButton("Chooser");
        chooser.setPreferredSize(new Dimension(120,30));
        chooser.addActionListener(new ChooserActionListener());
        for(String s: new String[]{"0000FF","FF0000","00CC00","FFFF00","000000","FFFFFF"}) {
            JButton bt = new JButton(new ImageIcon("images/"+s+".jpg"));
            bt.setText(s);
            bt.setPreferredSize(new Dimension(60, 30));
            bt.addActionListener(new ColorActionListener());
            color.add(bt);
            colorPanel.add(bt);
        }
        color.add(chooser);
        colorPanel.add(chooser);
        model.updateColor(color.elementAt(color.size()-3),color);
        // initializing thick list
        for(String s: new String[]{"1","3","5"}) {
            JButton bt = new JButton(new ImageIcon("images/"+"thick_"+s+".jpg"));
            bt.setText(s);
            if(s.equals("1")) model.updateThick(bt);
            bt.setPreferredSize(new Dimension(120, 30));
            bt.addActionListener(new ThickActionListener());
            thick.add(bt);
            thickPanel.add(bt);
        }

    }

    /**
     * Inner listener class
     */
    public class ColorActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            model.dir = 0;
            model.updateColor(bt,color);
        }
    }
    public class ChooserActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            Color c = Color.black;
            c = JColorChooser.showDialog(null,"pick the color you like",c);
            model.color=c;
            model.dir = 0;
            model.updateColor(bt,color);
        }
    }

    public class ToolActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            model.dir = 0;
            model.updateButton(bt);
        }
    }
    public class ThickActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton bt = (JButton)e.getSource();
            model.dir = 0;
            model.updateThick(bt);
        }
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        if (model.dir==1){
            model.dir = -1;
            String s;
            Color col = model.color;
            for (int i = 0; i < color.size()-1; i++) {
                s = color.elementAt(i).getText();
                Color c = Color.decode("#"+s);
                if (c.toString().equals(col.toString())) {
                    model.updateColor(color.elementAt(i), color);
                    break;
                }
            }
            int t = model.thickness;
            for (int i = 0; i < thick.size(); i++) {
                if (Integer.parseInt(thick.elementAt(i).getText())==t) {
                    model.updateThick(thick.elementAt(i));
                    break;
                }
            }
        }
    }
}
