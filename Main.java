import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Vector;

public class Main {
    static FileDialog openDia,saveDia;
    static File file = null;

    public static void main(String[] args) {
        Model model = new Model();
        Canvas canvas = new Canvas(model);
        Buttons buttons = new Buttons(model);
        MenuBar menu = new MenuBar();

        JFrame win = new JFrame("Jscratch");
        win.setLayout(new BorderLayout());
        // Set up the window.
        win.setMinimumSize(new Dimension(150, 700));
        win.setSize(800, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup menubar
        Menu fileList = new Menu("File");
        MenuItem nw = new MenuItem("New..");
        fileList.add(nw);
        MenuItem save = new MenuItem("Save..");
        fileList.add(save);
        MenuItem load = new MenuItem("load..");
        fileList.add(load);
        menu.add(fileList);
        // add menu listener
        openDia = new FileDialog(win, "Load", FileDialog.LOAD);
        saveDia = new FileDialog(win, "Save", FileDialog.SAVE);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file == null) {
                    saveDia.setVisible(true);
                    String dirPath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

                    if(dirPath == null || fileName == null) return ;
                    file = new File(dirPath,fileName);
                }

                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                    canvas.saveFile(bufw);
                    bufw.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Save File Failed");
                }
            }
        });
        load.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                openDia.setVisible(true);
                String dirPath = openDia.getDirectory();
                String fileName = openDia.getFile();

                if (dirPath == null || fileName == null) return;
                file = new File(dirPath, fileName);
                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));
                    canvas.loadFile(bufr);
                    bufr.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Load File Failed");
                }
            }});
        nw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                canvas.itemList = new Vector<>();
                canvas.repaint();
            }
        });
        KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        win.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeStroke, "deselect");
        win.getRootPane().getRootPane().getActionMap().put("deselect", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.cancelSelect();
            }
        });


        fileList.add(nw);
        fileList.add(save);
        fileList.add(load);
        win.add(canvas, BorderLayout.CENTER);
        win.add(buttons, BorderLayout.LINE_START);
        JScrollPane scrollPane = new JScrollPane(canvas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        win.add(scrollPane);
        win.setMenuBar(menu);
        win.setVisible(true);
    }
}
