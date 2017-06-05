package model;

import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
public class GridBagLayoutDemo {
    public void gui(){
        JFrame frame=new JFrame("Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button1=new JButton("Button 1");
        button1.setSize(400, 20);
        panel.add(button1);
        JButton button2=new JButton("Button 2");
        panel.add(button2);
        JButton button3=new JButton("Button 3");
        panel.add(button3);
        JButton button4=new JButton("Button 4");
        panel.add(button4);
        panel.setSize(400, 400);
        JScrollPane pane=new JScrollPane(panel);
        frame.add(pane);
        frame.setSize(400,400);
        frame.setVisible(true);
    }
    public static void main(String args[]){
        new GridBagLayoutDemo().gui();
    }
    
}