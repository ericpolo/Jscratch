
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class Canvas extends JPanel implements Observer {
    private Model model;
    public Vector<polyinterface> itemList = new Vector<>();
    private Point start = new Point(); //start point of mouse click
    private Point end = new Point(); //end point of mouse click
    private Point M = new Point();
    private Point C = new Point();
    private Color c = Color.black;
    private int clickNum = 0;
    private String tool = "";
    private int thick = 1;
    private Color selectc = Color.decode("#00FFFF");
    private boolean selected = false;
    private polyinterface selectItem = null;
    private class polygon{
        public Point start;
        public Point end;
        public boolean isFilled;
        public int thickness;
        public Color bolder;
        public Color fillColor;
        public boolean selected;
        public int shape;
    }

    private interface polyinterface{
        void printMe(Graphics g);
        boolean amIinPoly(Point p);
        void fillMe();
        void changeSelect();
        void changeColor();
        void changeThick();
        int getShape();
        Point getStart();
        Point getEnd();
        boolean getisFilled();
        int getThickness();
        Color getbolder();
        Color getFillColor();
    }
    private class Line extends polygon implements polyinterface{
        Line(Point start, Point end, boolean isFilled,int thickness,Color bolder,Color fillColor){
            this.bolder = bolder;
            this.fillColor = fillColor;
            this.start = new Point(start.x,start.y);
            this.end = new Point(end.x,end.y);
            this.thickness = thickness;
            this.isFilled = isFilled;
            this.selected=false;
            this.shape = 0;
        }
        public void printMe(Graphics g){
            float[] arr = {4.0f,2.0f};
            BasicStroke stroke = new BasicStroke(1,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    1.0f,arr,0);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(bolder);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawLine(start.x,start.y,end.x,end.y);
            if(selected){
                g2.setColor(selectc);
                g2.setStroke(stroke);
                g2.drawLine(start.x,start.y,end.x,end.y);
            }
        }
        public boolean amIinPoly(Point p) {
            // i am a line
            Line2D l2 = new Line2D.Double(start.x,start.y,end.x,end.y);
            double dis = l2.ptLineDist(p.x,p.y);
            if(dis<=5){
                return true;
            }
            return false;
        }
        public void fillMe(){
            // do nothing
        }
        public void changeSelect(){
            if(selected){
                selected = false;
            } else {
                selected = true;
            }
            if(selected){
                Color c = bolder;
                model.dir = 1;
                model.update(c,thickness);
            }
        }
        public void changeColor(){
            bolder = c;
        }
        public void changeThick(){
            this.thickness = thick;
        }
        public int getShape(){return shape;}
        public Point getStart(){return start;}
        public Point getEnd(){return end;}
        public boolean getisFilled(){return isFilled;}
        public int getThickness(){return thickness;}
        public Color getbolder(){return bolder;}
        public Color getFillColor(){return fillColor;}
    }

    private class Circle extends polygon implements polyinterface{
        Circle(Point start, Point end, boolean isFilled,int thickness,Color bolder,Color fillColor){
            this.bolder = bolder;
            this.fillColor = fillColor;
            this.start = new Point(start.x,start.y);
            this.end = new Point(end.x,end.y);
            this.thickness = thickness;
            this.isFilled = isFilled;
            this.selected = false;
            this.shape = 1;
        }
        public void printMe(Graphics g){
            float[] arr = {4.0f,2.0f};
            BasicStroke stroke = new BasicStroke(1,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    1.0f,arr,0);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(bolder);
            g2.setStroke(new BasicStroke(thickness));
            if(start.x<end.x) {
                if (start.y < end.y) {
                    g2.drawOval(start.x, start.y, end.x - start.x, end.y - start.y);
                    if(isFilled) {
                        g2.setColor(fillColor);
                        g2.fillOval(start.x, start.y, end.x - start.x, end.y - start.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawOval(start.x, start.y, end.x - start.x, end.y - start.y);
                    }
                } else {
                        g2.drawOval(start.x, end.y, end.x - start.x, start.y - end.y);
                    if(isFilled) {
                        g2.setColor(fillColor);
                        g2.fillOval(start.x, end.y, end.x - start.x, start.y - end.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawOval(start.x, end.y, end.x - start.x, start.y - end.y);
                    }
                }
            } else {
                if (start.y<end.y){
                    g2.drawOval(end.x,start.y,start.x-end.x,end.y-start.y);
                    if(isFilled) {
                        g2.setColor(fillColor);
                        g2.fillOval(end.x, start.y, start.x - end.x, end.y - start.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawOval(end.x, start.y, start.x - end.x, end.y - start.y);
                    }
                } else {
                    g2.drawOval(end.x,end.y,start.x-end.x,start.y-end.y);
                    if(isFilled){
                        g2.setColor(fillColor);
                        g2.fillOval(end.x,end.y,start.x-end.x,start.y-end.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawOval(end.x,end.y,start.x-end.x,start.y-end.y);
                    }
                }
            }

        }
        public boolean amIinPoly(Point p){
            // i am a circle
            Ellipse2D ell = new Ellipse2D.Double(0, 0, 0, 0);
            if (end.x > start.x && end.y > start.y)
                ell = new Ellipse2D.Double(start.x, start.y, end.x - start.x, end.y - start.y);
            if (end.x > start.x && end.y < start.y)
                ell = new Ellipse2D.Double(start.x, end.y, end.x - start.x, start.y - end.y);
            if (end.x < start.x && end.y > start.y)
                ell = new Ellipse2D.Double(end.x, start.y, start.x - end.x, end.y - start.y);
            if (end.x < start.x && end.y < start.y)
                ell = new Ellipse2D.Double(end.x, end.y, start.x - end.x, start.y - end.y);
            if (ell.contains(p.x, p.y)) {
                if(tool.equals("fill") && isFilled && fillColor==c) return false;
                return true;
            }
            return false;
        }
        public void fillMe(){
            fillColor=c;
            isFilled = true;
        }
        public void changeSelect(){
            if(selected){
                selected = false;
            } else {
                selected = true;
            }
            if(selected){
                Color c = this.bolder;
                model.dir = 1;
                model.update(c,thickness);
            }
        }
        public void changeColor(){
            bolder = c;
        }
        public void changeThick(){
            this.thickness = thick;
        };
        public int getShape(){return shape;}
        public Point getStart(){return start;}
        public Point getEnd(){return end;}
        public boolean getisFilled(){return isFilled;}
        public int getThickness(){return thickness;}
        public Color getbolder(){return bolder;}
        public Color getFillColor(){return fillColor;}

    }
    private class Rec extends polygon implements polyinterface{
        Rec(Point start, Point end, boolean isFilled,int thickness,Color bolder,Color fillColor){
            this.bolder = bolder;
            this.fillColor = fillColor;
            this.start = new Point(start.x,start.y);
            this.end = new Point(end.x,end.y);
            this.thickness = thickness;
            this.isFilled = isFilled;
            this.selected = false;
            this.shape = 2;
        }
        public void printMe(Graphics g){
            float[] arr = {4.0f,2.0f};
            BasicStroke stroke = new BasicStroke(1,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    1.0f,arr,0);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(bolder);
            g2.setStroke(new BasicStroke(thickness));
            if(start.x<end.x) {
                if (start.y < end.y) {
                    g2.drawRect(start.x, start.y, end.x - start.x, end.y - start.y);
                    if(isFilled){
                        g2.setColor(fillColor);
                        g2.fillRect(start.x, start.y, end.x - start.x, end.y - start.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawRect(start.x, start.y, end.x - start.x, end.y - start.y);
                    }
                } else {
                    g2.drawRect(start.x, end.y, end.x - start.x, start.y - end.y);
                    if(isFilled) {
                        g2.setColor(fillColor);
                        g2.fillRect(start.x, end.y, end.x - start.x, start.y - end.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawRect(start.x, end.y, end.x - start.x, start.y - end.y);
                    }
                }
            } else {
                if (start.y<end.y){
                    g2.drawRect(end.x,start.y,start.x-end.x,end.y-start.y);
                    if(isFilled){
                        g2.setColor(fillColor);
                        g2.fillRect(end.x,start.y,start.x-end.x,end.y-start.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawRect(end.x,start.y,start.x-end.x,end.y-start.y);
                    }
                } else {
                    g2.drawRect(end.x,end.y,start.x-end.x,start.y-end.y);
                    if(isFilled){
                        g2.setColor(fillColor);
                        g2.fillRect(end.x,end.y,start.x-end.x,start.y-end.y);
                    }
                    if(selected){
                        g2.setColor(selectc);
                        g2.setStroke(stroke);
                        g2.drawRect(end.x,end.y,start.x-end.x,start.y-end.y);
                    }
                }
            }
        }
        public boolean amIinPoly(Point p){
            // i am a rec
            Polygon poly = new Polygon();
            poly.addPoint(start.x,start.y);
            poly.addPoint(start.x,end.y);
            poly.addPoint(end.x,end.y);
            poly.addPoint(end.x,start.y);
            if(poly.contains(p.x,p.y)) {
                if(tool.equals("fill") && isFilled && fillColor==c) return false;
                return true;
            } else {
                return false;
            }
        }
        public void fillMe(){
            fillColor=c;
            isFilled = true;
        }
        public void changeSelect(){
            if(selected){
                selected = false;
            } else {
                selected = true;
            }
            if(selected){
                Color c = bolder;
                model.dir = 1;
                model.update(c,thickness);
            }
        }
        public void changeColor(){
            bolder = c;
        }
        public void changeThick(){
            this.thickness = thick;
        };
        public int getShape(){return shape;}
        public Point getStart(){return start;}
        public Point getEnd(){return end;}
        public boolean getisFilled(){return isFilled;}
        public int getThickness(){return thickness;}
        public Color getbolder(){return bolder;}
        public Color getFillColor(){return fillColor;}
    }

    /**
     * Create a new Canvas.
     */
    public Canvas(Model model) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        super();
        this.model = model;
        this.setPreferredSize(new Dimension(600,600));
        this.setBorder(BorderFactory.createLineBorder(Color.black,3));
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                if(tool.equals("line") || tool.equals("circle") || tool.equals("rec")) {
                    selected=false;
                    if(selectItem!= null){
                        selectItem.changeSelect();
                        selectItem = null;
                        repaint();
                    }
                    if(clickNum==0) {
                        start.x = e.getX();
                        start.y = e.getY();
                        end.x = e.getX();
                        end.y = e.getY();
                    } else if(clickNum==1){
                        // it is the second click;
                        end.x = e.getX();
                        end.y = e.getY();
                        repaint();
                    }
                    M.x = e.getX();
                    M.y = e.getY();
                    clickNum++;
                } else if (tool.equals("eraser")){
                    selected=false;
                    if(selectItem!= null){
                        selectItem.changeSelect();
                        selectItem = null;
                        repaint();
                    }
                    Point eraser = new Point(e.getX(),e.getY());
                    for(int i = itemList.size()-1;i>=0;i--){
                        polyinterface pl = itemList.elementAt(i);
                        boolean in = pl.amIinPoly(eraser);
                        if(in) {
                            itemList.remove(i);
                            repaint();
                            break;
                        }
                    }
                } else if (tool.equals("fill")){
                    selected=false;
                    if(selectItem!= null){
                        selectItem.changeSelect();
                        selectItem = null;
                        repaint();
                    }
                    Point fill = new Point(e.getX(),e.getY());
                    for(int i = itemList.size()-1;i>=0;i--){
                        polyinterface pl = itemList.elementAt(i);
                        boolean in = pl.amIinPoly(fill);
                        if(in) {
                            itemList.elementAt(i).fillMe();
                            repaint();
                            break;
                        }
                    }
                } else if (tool.equals("select")) {
                    Point select = new Point(e.getX(), e.getY());
                    if(selectItem!=null){
                        selected = false;
                        selectItem.changeSelect();
                        selectItem=null;
                        repaint();
                    }
                    for (int i = itemList.size()-1; i >= 0; i--) {
                        polyinterface pl = itemList.elementAt(i);
                        boolean in = pl.amIinPoly(select);
                        if (in) {
                            selected = true;
                            selectItem = itemList.elementAt(i);
                            model.dir = 1;
                            itemList.elementAt(i).changeSelect();
                            itemList.remove(i);
                            repaint();
                            break;
                        }
                    }
                    if(selectItem!=null) itemList.add(selectItem);
                }
            }});

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                if(clickNum==1) {
                    C.x = e.getX();
                    C.y = e.getY();
                    repaint();
                }
            }
        });
        model.addObserver(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(tool.equals("line")){
                g2.setColor(c);
                g2.setStroke(new BasicStroke(thick));
                g2.drawLine(M.x, M.y, C.x, C.y);
            if(clickNum>=2) {
                Line l = new Line(start,end,false, thick,c,null);
                itemList.add(l);
                clickNum = 0;
            }
        } else if (tool.equals("circle")){
            g2.setColor(c);
            g2.setStroke(new BasicStroke(thick));
            if(M.x<C.x) {
                if (M.y < C.y) {
                    g2.drawOval(M.x, M.y, C.x - M.x, C.y - M.y);
                } else {
                    g2.drawOval(M.x, C.y, C.x - M.x, M.y - C.y);
                }
            } else {
                if (M.y<C.y){
                    g2.drawOval(C.x,M.y,M.x-C.x,C.y-M.y);
                } else {
                    g2.drawOval(C.x,C.y,M.x-C.x,M.y-C.y);
                }
            }
            if(clickNum>=2){
                Circle cir = new Circle(start,end,false, thick,c,null);
                itemList.add(cir);
                clickNum=0;
            }
        } else if (tool.equals("rec")){
            g2.setColor(c);
            g2.setStroke(new BasicStroke(thick));
            if(M.x<C.x) {
                if (M.y < C.y) {
                    g2.drawRect(M.x, M.y, C.x - M.x, C.y - M.y);
                } else {
                    g2.drawRect(M.x, C.y, C.x - M.x, M.y - C.y);
                }
            } else {
                if (M.y<C.y){
                    g2.drawRect(C.x,M.y,M.x-C.x,C.y-M.y);
                } else {
                    g2.drawRect(C.x,C.y,M.x-C.x,M.y-C.y);
                }
            }
            if(clickNum>=2){
                Rec rec = new Rec(start,end,false, thick,c,null);
                itemList.add(rec);
                clickNum=0;
            }
        }
        for(polyinterface pi: this.itemList){
            pi.printMe(g);
        }
    }

    public void saveFile(BufferedWriter bufw){
        try{
            bufw.write(itemList.size());
            for(polyinterface polyi:itemList){
                bufw.write(polyi.getShape());
                bufw.write(polyi.getStart().x);
                bufw.write(polyi.getStart().y);
                bufw.write(polyi.getEnd().x);
                bufw.write(polyi.getEnd().y);
                bufw.write(polyi.getThickness());
                bufw.write(polyi.getbolder().getRed());
                bufw.write(polyi.getbolder().getBlue());
                bufw.write(polyi.getbolder().getGreen());
                if(!polyi.getisFilled()) {
                    bufw.write(0);
                } else {
                    bufw.write(1);
                    bufw.write(polyi.getFillColor().getRed());
                    bufw.write(polyi.getFillColor().getBlue());
                    bufw.write(polyi.getFillColor().getGreen());
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Save File Failed");
        }
    }

    public void loadFile(BufferedReader bufr){
        try {
            Vector<polyinterface> newItemList = new Vector<>();
            int size = bufr.read();

            for(int i = 0;i<size;i++) {
                int shape = bufr.read();
                int startx = bufr.read();
                int starty = bufr.read();
                Point start = new Point(startx,starty);
                int endx = bufr.read();
                int endy = bufr.read();
                Point end = new Point(endx,endy);
                int thick = bufr.read();
                int r = bufr.read();
                int b = bufr.read();
                int g = bufr.read();
                Color col = new Color(r, g, b);
                Color fillColor = null;
                int isFilled = bufr.read();
                boolean fill = false;
                if (isFilled == 1) {
                    r = bufr.read();
                    g = bufr.read();
                    b = bufr.read();
                    fill = true;
                    fillColor = new Color(r, g, b);
                }
                if(shape == 0){
                    // i am a line
                    newItemList.add(new Line(start,end,fill,thick,col,fillColor));
                } else if (shape == 1) {
                    // i am a circle
                    newItemList.add(new Circle(start, end, fill, thick, col, fillColor));
                } else if (shape == 2){
                    // i am a rectangle
                    newItemList.add(new Rec(start, end, fill, thick, col, fillColor));
                }
                itemList = newItemList;
                repaint();
            }
        }  catch (IOException ex) {
            throw new RuntimeException("Load File Failed");
        }
    }

    public void cancelSelect(){
        selected = false;
        selectItem.changeSelect();
        selectItem=null;
        repaint();
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        if (model.dir == 0) {
            c = model.color;
            tool = model.tool;
            thick = model.thickness;
            if (selected) {
                selectItem.changeColor();
                selectItem.changeThick();
                repaint();
            }
            model.dir = -1;
        }
    }
}
