package com.test.twitter;

import org.davidmoten.text.utils.WordWrap;
import twitter4j.TwitterException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Twitter Search Program
 * Description - Java Swing application that searches Twitter for a user's query
 * and displays the results.
 * Author - Kelly May
 * Date Created - 5/24/2021
 */
public class App {
    private JButton btnEnter;
    private JPanel panelMain;
    private JTextField tfSearchBar;
    private JTextPane txtTitle;
    private JTextPane trendingTextPane;
    private JLabel lblBird;
    private JTable tweetTable;
    private JList trendingList;
    private JButton btnRefresh;
    private JLabel lblBird2;
    private final int CELL_LENGTH = 45;
    TrendAdder trendAdder = new TrendAdder();
    TwitterAdder twitterAdder = new TwitterAdder();
    Messages messages = new Messages();
    boolean refreshClicked = false;

    public App() throws TwitterException {
        tfSearchBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfSearchBar.setText("");
            }
        });

        //search tweets
        btnEnter.addActionListener(actionEvent -> {
            twitterAdder.clearTweetLists(); //clear previous lists
            refreshClicked = false;
            StringBuilder searchQuery = new StringBuilder(tfSearchBar.getText());
            if (tfSearchBar.getText().isEmpty()) {
                messages.noSearchQuery();
            }
            else{
                try {
                    twitterAdder.setTweetLists(searchQuery);
                    fillTweetsTable(twitterAdder.getHandleList(), twitterAdder.getTweetList());
                } catch (TwitterException e) {
                    e.printStackTrace();
                    messages.genericIssue();
                }

            }
        });

        btnRefresh.addActionListener(actionEvent ->{
            twitterAdder.clearTweetLists();
            try {
                refreshClicked = true;
                fillTweetsTable(twitterAdder.getHandleList(), twitterAdder.getTweetList());
                trendAdder.clearTrends();
                trendAdder.setTweetTrends();
                tfSearchBar.setText("");
            } catch (TwitterException e) {
                e.printStackTrace();
                messages.genericIssue();
            }
            fillTrendTable();
        });

         fillTrendTable();
    }


    private void fillTweetsTable(ArrayList<StringBuilder> handles, ArrayList<StringBuilder> tweets){
        DefaultTableModel tableModel = new DefaultTableModel();
        tweetTable.setModel(tableModel);

        tableModel.addColumn("Twitter Handle");
        tableModel.addColumn("Tweet");

        try {
            if (twitterAdder.listsEmpty() && !refreshClicked) {
                messages.noResult();
            }

            for (int i = 0; i < handles.size(); i++) {
                // if length of tweet is too long, split the tweet so it can be wrapped in a multiline cell.
                if (tweets.get(i).length() > CELL_LENGTH) {
                    int splitTimes = tweets.get(i).length() / CELL_LENGTH;
                    String splitTweet = splitTheTweet(tweets.get(i));
                    tableModel.addRow(new Object[]{handles.get(i), splitTweet});
                    tweetTable.setRowHeight(i, countTheLines(splitTweet));
                } else {
                    tableModel.addRow(new Object[]{handles.get(i), tweets.get(i)});
                }
            }
        } catch(Exception ex){
            messages.genericIssue();
        }
    }

    private void fillTrendTable() {
        DefaultListModel listModel = new DefaultListModel();
        trendingList.setModel(listModel);
        for (StringBuilder tweetTrend : trendAdder.getTweetTrends()) {
            listModel.addElement(tweetTrend);
        }
    }


   // splitting the tweet into equal parts for hard-wrapping longer messages
    protected String splitTheTweet(StringBuilder tweet) {
        String newLine = "<br>";
        String result = "<html>"+WordWrap.from(tweet.toString()).maxWidth(CELL_LENGTH).newLine(newLine).insertHyphens(true).wrap() + "</html>";
        return result;
    }

    protected int countTheLines(String result){
        int count = 0;
        String breakString = "<br>";
        int lastIndex = 0;

        while(lastIndex != -1){
            lastIndex = result.indexOf(breakString, lastIndex);
            if(lastIndex != -1){
                count++;
                lastIndex += breakString.length();
            }
        }
        System.out.println(count);
        return count * 40 +5; //accounting for spacing of character size
    }

    public static void main(String[] args) throws TwitterException {
        JFrame frame = new JFrame("Twitter Tweet Finder");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
