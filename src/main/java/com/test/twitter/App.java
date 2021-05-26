package com.test.twitter;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

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
    private JTable tweetTable;
    private ArrayList<String> searchFields = new ArrayList<>();
    private final int CELL_LENGTH = 40;
    private ArrayList<StringBuilder> handleList = new ArrayList<>();
    private ArrayList<StringBuilder> tweetList = new ArrayList<>();

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
                    clearTweetLists(); //clear previous lists
                    Twitter connection = setTwitterConnection(); //connect to twitter
                    setTweetLists(connection, tfSearchBar);
                    fillTable(handleList, tweetList);

                    //System.out.print(setTweetMap(connection, tfSearchBar));

                    //need to figure out how to add results to Jtable and display in GUI
                    //tweetTable.addColumn(setTweetMap(connection, tfSearchBar));

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * takes the twitter connection, and the input from tfSearchBar to fill a sorted HashMap with the username and tweet
     * @param twitter, tfSearchBar
     * @return
     * @throws TwitterException
     */
    /*
    private static LinkedHashMap<String, String> setTweetMap(Twitter twitter, JTextField tfSearchBar) throws TwitterException{

        LinkedHashMap<String,String> tweetList = new LinkedHashMap<>();
        Query query = new Query(tfSearchBar.getText());

        QueryResult result = twitter.search(query);

        for(Status status : result.getTweets()){
            //String tweetString = "@" + status.getUser().getScreenName() + " : " + status.getText();
            tweetList.put("@" + status.getUser().getScreenName(), status.getText());
        }

        return tweetList;
    }
     */


    /**
     * fills the arrayLists with the twitter handles and the tweets
     * @param twitter
     * @param tfSearchBar
     * @throws TwitterException
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
            //System.out.println(tweets.get(i));
            // if length of tweet is greater than 100, create array, to split tweet into multiple lines
            if (tweets.get(i).length() > CELL_LENGTH){
                int splitTimes = tweets.get(i).length() / CELL_LENGTH;

                String[] splitList =
                        Iterables.toArray(Splitter.fixedLength(CELL_LENGTH).split(tweets.get(i)), String.class);

                System.out.println(Arrays.toString(splitList));

                model.addRow(new Object[] {handles.get(i), splitList});
                tweetTable.setRowHeight(i, 30*splitTimes);
            }
            else {
                model.addRow(new Object[]{handles.get(i), tweets.get(i)});
            }
            adjustTable();


        }
    }

    /**
     * https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
     * wrapping text in cells.
     */
    private void adjustTable(){
        MultiLineCellRenderer renderer = new MultiLineCellRenderer();
        //set TableCellRenderer into a specified JTable column
        tweetTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }


    /**
     * connectTwitter - uses OAuth to connect and configure to Twitter
     * @return
     */
    private Twitter setTwitterConnection(){
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

    public static void main(String[] args) {
        //create JFrame
        JFrame frame = new JFrame("Twitter Tweet Finder");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
