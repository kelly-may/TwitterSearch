package com.test.twitter;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;

public class TrendAdder extends TwitterAdder {

    /**
     * make a list of trending topics in New York
     *
     * @throws TwitterException for twitter4j
     */
    protected ArrayList<String> setTweetTrends() throws TwitterException {
        ArrayList<String> trends = new ArrayList<>();
        int newYorkWOEID = 2459115;

        Trends dailyTrends = super.connection.getPlaceTrends(newYorkWOEID);
        for (int i = 0; i < dailyTrends.getTrends().length; i++) {
            trends.add(dailyTrends.getTrends()[i].getName());
        }
        return trends;
    }
}
