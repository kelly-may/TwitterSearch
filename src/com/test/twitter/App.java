package com.test.twitter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class App {
    private JButton btnEnter;
    private JPanel panelMain;
    private JTextField tfSearchBar;
    private JList list1;
    private JTextPane txtTitle;
    private JTextPane txtSubtitle;

    public App() {
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //write code to show message
                JOptionPane.showMessageDialog(null, "Hello World!");
            }
        });
    }

    public static void main(String[] args){
        //create JFrame
        JFrame frame = new JFrame("Twitter Finder");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
