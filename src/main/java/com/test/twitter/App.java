package com.test.twitter;

import twitter4j.*;
import twitter4j.auth.Authorization;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
                try {
                    searchTweets(tfSearchBar);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static ArrayList<String> searchTweets(JTextField tfSearchBar) throws TwitterException{
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("dtMTeDPWds1fTW1NYcGea9iBI")
        .setOAuthConsumerSecret("IEMsYzxXkOhXIxiWWPXecCJfGj2QgO5SlxdWPOOrEiLDUaoRKs")
        .setOAuthAccessToken("1396867457101541376-93fHVRncPQxQxHH1igIfNy47a401Ww")
        .setOAuthAccessTokenSecret("B5oZljI22OWu4qtyavf2SAyEXxHBZhYik33vSPx4DHOxf");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();


        ArrayList<String> tweetList = new ArrayList();
        //TwitterFactory twitter = (TwitterFactory) new TwitterFactory().getInstance();
        Query query = new Query(tfSearchBar.getText().toString());

        QueryResult result = twitter.search(query);

        ArrayList<String> list = new ArrayList<>();
        tweetList.addAll(result.getTweets().stream().map(item-> item.getText()).collect(Collectors.toList()));

        System.out.print(tweetList.toString());
        return tweetList;
    }

    /**
     * createList - method to fill the list1 values with top 5 tweets.
     */
    public void createList(){
        searchFields.add("Testing This");
        list1.setListData(searchFields.toArray());
    }

    public static void main(String[] args) {
        //create JFrame
        JFrame frame = new JFrame("Twitter Tweet Finder");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }


}
