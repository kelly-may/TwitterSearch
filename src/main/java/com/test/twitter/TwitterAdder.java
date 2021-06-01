package com.test.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

/**
 * class for handling the twitter4j actions related to searching a query and finding tweets
 */
public class TwitterAdder {
    Twitter connection;
    private ArrayList<StringBuilder> handleList = new ArrayList<>();
    private ArrayList<StringBuilder> tweetList = new ArrayList<>();

    public TwitterAdder() {
        connection = setTwitterConnection();
    }


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


    protected void setTweetLists(StringBuilder userEntry) throws TwitterException {
        Query query = new Query(userEntry.toString());

        QueryResult result = connection.search(query);

        for (Status status : result.getTweets()) {
            handleList.add(new StringBuilder("@" + status.getUser().getScreenName()));
            tweetList.add(new StringBuilder(status.getText()));
        }

    }

    public ArrayList<StringBuilder> getHandleList(){
        return handleList;
    }


    public ArrayList<StringBuilder> getTweetList(){
        return tweetList;
    }


    protected void clearTweetLists() {
        handleList.clear();
        tweetList.clear();
    }


    protected boolean listsEmpty(){
        return handleList.isEmpty() && tweetList.isEmpty();
    }

}
