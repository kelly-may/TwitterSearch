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
    private DefaultListModel listModel = new DefaultListModel();
    private JList list1 = new JList(listModel);
    private JTextPane txtTitle;
    private JTextPane txtSubtitle;
    private JLabel lblBird;
    private ArrayList<String> searchFields = new ArrayList<>();

    /**
     * App - holds listeners for the Enter button on search.
     */
    public App() {
        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //write code to show message
                //JOptionPane.showMessageDialog(null, "Hello World!");
                try {
                    Twitter connection = connectTwitter(); //connect to twitter
                    ArrayList<String> results = new ArrayList<>();
                    results.addAll(searchTweets(connection, tfSearchBar));

                    //need to figure out how to add results to Jlist and display in GUI
                    for(int i = 0; i < results.size(); i++){
                        listModel.addElement(results.get(i));
                        list1.setVisible(true);
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * searchTweets - takes the twitter connection, and the input from tfSearchBar to fill a list with search results
     * @param twitter, tfSearchBar
     * @return
     * @throws TwitterException
     */
    public static ArrayList<String> searchTweets(Twitter twitter, JTextField tfSearchBar) throws TwitterException{

        ArrayList<String> tweetList = new ArrayList();
        //TwitterFactory twitter = (TwitterFactory) new TwitterFactory().getInstance();
        Query query = new Query(tfSearchBar.getText().toString());

        QueryResult result = twitter.search(query);

        tweetList.addAll(result.getTweets().stream().map(item-> item.getText()).collect(Collectors.toList()));

        System.out.print(tweetList.toString());
        return tweetList;
    }

    /**
     * connectTwitter - uses OAuth to connect and configure to Twitter
     * @return
     */
    public Twitter connectTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("dtMTeDPWds1fTW1NYcGea9iBI")
                .setOAuthConsumerSecret("IEMsYzxXkOhXIxiWWPXecCJfGj2QgO5SlxdWPOOrEiLDUaoRKs")
                .setOAuthAccessToken("1396867457101541376-93fHVRncPQxQxHH1igIfNy47a401Ww")
                .setOAuthAccessTokenSecret("B5oZljI22OWu4qtyavf2SAyEXxHBZhYik33vSPx4DHOxf");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
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
