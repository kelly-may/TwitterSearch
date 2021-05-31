package com.test.twitter;

import javax.swing.*;

public class Messages {

    public void noSearchQuery(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "No Search Query entered. \nPlease try again.",  "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public void noResult(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "No result.",  "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public void genericIssue(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "There was an issue with your search. \nPlease Try again.",  "Warning",
                JOptionPane.WARNING_MESSAGE);    }
}
