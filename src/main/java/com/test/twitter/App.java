package com.test.twitter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Twitter Search Program
 * Description - Java Swing application that searches Twitter for a user's query and displays the results.
 * Author - Kelly May
 * Date Created - 5/24/2021
 */
public class App {
    private JButton btnEnter;
    private JPanel panelMain;
    private JTextField tfSearchBar;
    private JList list1;
    private JTextPane txtTitle;
    private JTextPane txtSubtitle;
    private JLabel lblBird;
    private ArrayList<String> searchFields = new ArrayList<>();

    /**
     * App - holds listeners
     */
    public App() {
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //write code to show message
                JOptionPane.showMessageDialog(null, "Hello World!");
                createList();
            }
        });
    }

    /**
     * createList - method to fill the list1 values with top 5 tweets.
     */
    public void createList(){
        searchFields.add("Testing This");
        list1.setListData(searchFields.toArray());
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
