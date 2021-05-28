package com.test.twitter;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
    private JTextPane txtTitle;
    private JTextPane txtSubtitle;
    private JLabel lblBird;
    private JTable tweetTable;
    private JList trendingList;
    private JTextPane trendingTextPane;
    private ArrayList<String> searchFields = new ArrayList<>();
    private final int CELL_LENGTH = 70;
    private ArrayList<StringBuilder> handleList = new ArrayList<>();
    private ArrayList<StringBuilder> tweetList = new ArrayList<>();

    /**
     * App - holds listeners for the Enter button on search.
     */
    public App() throws TwitterException {

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    clearTweetLists(); //clear previous lists
                    Twitter connection = setTwitterConnection(); //connect to twitter
                    setTweetLists(connection, tfSearchBar);
                    fillTable(handleList, tweetList);

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });

            // add trends to Jlist
            trendingList.setModel(listModel);
            ArrayList<String> tweetTrends = setTweetTrends(setTwitterConnection());
        for (String tweetTrend : tweetTrends) {
            listModel.addElement(tweetTrend);
        }

    }

    /**
     * fills the arrayLists with the twitter handles and the tweets
     * @param twitter for connection
     * @param tfSearchBar for pulling string for query
     * @throws TwitterException for twitter4j
     */
    private void setTweetLists(Twitter twitter, JTextField tfSearchBar) throws TwitterException{
        Query query = new Query(tfSearchBar.getText());

        QueryResult result = twitter.search(query);

        for(Status status : result.getTweets()){
            handleList.add(new StringBuilder("@" + status.getUser().getScreenName()));
            tweetList.add(new StringBuilder(status.getText()));
        }
    }

    /**
     * make a list of trending topics in New York
     * @param twitter for  connection
     * @throws TwitterException for twitter4j
     */
    private ArrayList<String> setTweetTrends(Twitter twitter) throws TwitterException{
        ArrayList<String> trends = new ArrayList<>();
        int newYorkWOEID= 2459115;

        Trends dailyTrends = twitter.getPlaceTrends(newYorkWOEID);
        for(int i = 0; i < dailyTrends.getTrends().length; i++){
            trends.add(dailyTrends.getTrends()[i].getName());
        }
        return trends;
    }

    /**
     * clears the handles and tweets arraylists
     */
    private void clearTweetLists(){
        handleList.clear();
        tweetList.clear();
    }

    /**
     * fills the table with the values from Linked HashMap to view in GUI
     * @param handles, tweets
     */
    private void fillTable( ArrayList<StringBuilder> handles, ArrayList<StringBuilder> tweets){
        DefaultTableModel model = new DefaultTableModel();
        tweetTable.setModel(model);

        model.addColumn("Twitter Handle");
        model.addColumn("Tweet");

        for(int i = 0; i < handles.size(); i++) {
            // if length of tweet is too long, split the tweet so it can be wrapped in a multiline cell.
            if (tweets.get(i).length() > CELL_LENGTH){
                int splitTimes = tweets.get(i).length() / CELL_LENGTH;

                model.addRow(new Object[] {handles.get(i), splitTheList(tweets.get(i))});
                tweetTable.setRowHeight(i, 30*(splitTimes+1)); //adjust row height to account for more lines
            }
            else {
                model.addRow(new Object[]{handles.get(i), tweets.get(i)});
            }
            wrapCellText();


        }
    }

    /**
     * splits a list into equal parts for hard-wrapping text in table cells
     * @param tweet stringbuilder to split
     * @return returns array
     */
    private String[] splitTheList(StringBuilder tweet){

        return Iterables.toArray(Splitter.fixedLength(CELL_LENGTH).split(tweet), String.class);
    }

    /**
     * https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
     * wrapping text in cells.
     */
    private void wrapCellText(){

        MultiLineCellRenderer renderer = new MultiLineCellRenderer();
        //set TableCellRenderer into a specified JTable column
        tweetTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }


    /**
     * connect Twitter - uses OAuth to connect and configure to Twitter
     * @return twitter connection
     */
    private Twitter setTwitterConnection(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("dtMTeDPWds1fTW1NYcGea9iBI")
                .setOAuthConsumerSecret("IEMsYzxXkOhXIxiWWPXecCJfGj2QgO5SlxdWPOOrEiLDUaoRKs")
                .setOAuthAccessToken("1396867457101541376-93fHVRncPQxQxHH1igIfNy47a401Ww")
                .setOAuthAccessTokenSecret("B5oZljI22OWu4qtyavf2SAyEXxHBZhYik33vSPx4DHOxf");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
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
