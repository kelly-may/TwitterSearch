package com.test.twitter;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.ArrayList;

/**
 * class for adding trends from twitter4j
 */
public class TrendAdder extends TwitterAdder {
    private ArrayList<StringBuilder> tweetTrends = new ArrayList<>();

    public TrendAdder() throws TwitterException { setTweetTrends(); }


    /**
     * make a list of trending topics in New York (for USA trends)
     *
     * @throws TwitterException for twitter4j
     */
    protected void setTweetTrends() throws TwitterException {
        int newYorkWOEID = 2459115;
        tweetTrends.clear();

        Trends dailyTrends = super.connection.getPlaceTrends(newYorkWOEID);
        for (int i = 0; i < dailyTrends.getTrends().length; i++) {
            tweetTrends.add(new StringBuilder(dailyTrends.getTrends()[i].getName()));
        }
    }


    protected ArrayList<StringBuilder> getTweetTrends(){
        return tweetTrends;
    }


    protected void clearTrends(){
        tweetTrends.clear();
    }
}
