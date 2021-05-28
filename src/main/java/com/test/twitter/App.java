package com.test.twitter;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import twitter4j.*;

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
    private JTextPane txtSubtitle;
    private final int CELL_LENGTH = 45;
    TrendAdder trendAdder = new TrendAdder();
    TwitterAdder twitterAdder = new TwitterAdder();

    /**
     * App - holds listeners for GUI
     */
    public App() throws TwitterException {
        tfSearchBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfSearchBar.setText("");
            }
        });

        //search tweets
        btnEnter.addActionListener(actionEvent -> {
            try {
                twitterAdder.clearTweetLists(); //clear previous lists
                StringBuilder searchQuery = new StringBuilder(tfSearchBar.getText());
                if (!tfSearchBar.getText().equals("")) {
                    twitterAdder.setTweetLists(searchQuery);
                    fillTweetsTable(twitterAdder.getHandleList(), twitterAdder.getTweetList());
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        });

        btnRefresh.addActionListener(actionEvent ->{
            twitterAdder.clearTweetLists();
            fillTweetsTable(twitterAdder.getHandleList(), twitterAdder.getTweetList());
            fillTrendTable();
        });

         fillTrendTable();
    }


    /**
     * fills the table with the values from arrays to view in GUI
     * @param handles, tweets
     */
    private void fillTweetsTable(ArrayList<StringBuilder> handles, ArrayList<StringBuilder> tweets){
        DefaultTableModel tableModel = new DefaultTableModel();
        tweetTable.setModel(tableModel);

        tableModel.addColumn("Twitter Handle");
        tableModel.addColumn("Tweet");

        if(twitterAdder.listsEmpty()){
            tableModel.addRow(new Object[] {"No Result", "No Result"});
        }

        for(int i = 0; i < handles.size(); i++) {
             // if length of tweet is too long, split the tweet so it can be wrapped in a multiline cell.
          if (tweets.get(i).length() > CELL_LENGTH){
                int splitTimes = tweets.get(i).length() / CELL_LENGTH;
                tableModel.addRow(new Object[] {handles.get(i), splitTheTweet(tweets.get(i))});
                tweetTable.setRowHeight(i, 30*(splitTimes+1)); //adjust row height to account for more lines
            }
            else {
                tableModel.addRow(new Object[]{handles.get(i), tweets.get(i)});
            }
        }
        wrapCellText();
    }


    /**
     * fills the Jlist with values from trends to view in GUI
     * @throws TwitterException for twitter4j object
     */
    private void fillTrendTable() {
        DefaultListModel listModel = new DefaultListModel();
        trendingList.setModel(listModel);
        for (StringBuilder tweetTrend : trendAdder.getTweetTrends()) {
            listModel.addElement(tweetTrend);
        }
    }

    /**
     * splits a tweet stringbuilder into equal parts for hard-wrapping text in table cells
     *
     * @param tweet stringbuilder to split
     * @return returns array
     */
    protected String[] splitTheTweet(StringBuilder tweet) {
        return Iterables.toArray(Splitter.fixedLength(CELL_LENGTH).split(tweet), String.class);
    }

    /**
     * https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
     * wrapping text in cells.
     */
    private void wrapCellText(){
        MultiLineCellRenderer renderer = new MultiLineCellRenderer();
        tweetTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }


    public static void main(String[] args) throws TwitterException {
        //create JFrame
        JFrame frame = new JFrame("Twitter Tweet Finder");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
