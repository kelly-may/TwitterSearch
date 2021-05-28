package com.test.twitter;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

public class TwitterAdder {
    Twitter connection;
    private ArrayList<StringBuilder> handleList = new ArrayList<>();
    private ArrayList<StringBuilder> tweetList = new ArrayList<>();

    public TwitterAdder() {
        connection = setTwitterConnection();
    }

    /**
     * connect Twitter - uses OAuth to connect and configure to Twitter
     *
     * @return twitter connection
     */
    private Twitter setTwitterConnection() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("dtMTeDPWds1fTW1NYcGea9iBI")
                .setOAuthConsumerSecret("IEMsYzxXkOhXIxiWWPXecCJfGj2QgO5SlxdWPOOrEiLDUaoRKs")
                .setOAuthAccessToken("1396867457101541376-93fHVRncPQxQxHH1igIfNy47a401Ww")
                .setOAuthAccessTokenSecret("B5oZljI22OWu4qtyavf2SAyEXxHBZhYik33vSPx4DHOxf");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

    /**
     * fills the arrayLists with the twitter handles and the tweets
     *
     * @param userEntry - what is entered in the search bar
     * @throws TwitterException for twitter4j
     */
    protected void setTweetLists(StringBuilder userEntry) throws TwitterException {
        Query query = new Query(userEntry.toString());

        QueryResult result = connection.search(query);

        for (Status status : result.getTweets()) {
            handleList.add(new StringBuilder("@" + status.getUser().getScreenName()));
            tweetList.add(new StringBuilder(status.getText()));
        }

        for (int i = 0; i < handleList.size(); i++) {
            System.out.println(handleList.get(i) + "------>" + tweetList.get(i));
        }
    }

    public ArrayList<StringBuilder> getHandleList(){
        return handleList;
    }

    public ArrayList<StringBuilder> getTweetList(){
        return tweetList;
    }


    /**
     * clears the handles and tweets arraylists
     */
    protected void clearTweetLists() {
        handleList.clear();
        tweetList.clear();
    }

    /**
     * checking if lists are empty
     * @return true or false
     */
    protected boolean listsEmpty(){
        System.out.println(handleList.size());
        System.out.println(tweetList.size());
        return handleList.isEmpty() && tweetList.isEmpty();
    }

}
