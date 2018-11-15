package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {
            tweeter.tweet("foo", "barbar");
            //tweeter.tweet("Nams", "Second tweet");
            //tweeter.tweet("Nams", "first tweet");
            
            tweeter.follow("alice", "bob");
            //tweeter.follow("carl", "bob");
            tweeter.follow("bob", "alice");
            
            tweeter.tweet("alice", "namrata kasar rocks");
            
            /*tweeter.follow("bob", "alex");
            tweeter.follow("nams", "alex");
            tweeter.follow("Bob", "Nams");
            tweeter.follow("alex", "Nams");
            tweeter.follow("bob", "alex");
            tweeter.tweet("alex", "first tweet");
        
            
            /*tweeter.block("alex", "bob");
            tweeter.block("Nams", "alex");
            tweeter.block("Krutika", "alex");
            tweeter.block("Krutika", "cagh");*/
            //tweeter.block("has", "cagh");
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        System.out.println("Most popular message " + stats.getMostPopularMessage());
        
        stats.resetStatsAndSystem();
        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        System.out.println("Most popular message " + stats.getMostPopularMessage());
        
        ctx.close();
    }
}
