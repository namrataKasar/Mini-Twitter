package edu.sjsu.cmpe275.aop;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TweetStatsImpl implements TweetStats {
    /***
     * Declaring maps and variables for storing the data
     */

	public static int lengthOfLongestTweet = 0;
	public static String mostFollowedUser = null;
	public static String mostProductiveUser = null;
	public static String mostPopularMessage = null;
	public static String mostBlockedFollower = null;
	
	public static Map<String, Integer> mostProductiveUserMap = new TreeMap<String, Integer>();
	public static Map<String, Set<String>> mostFollowedUserMap = new TreeMap<String, Set<String>>();
	public static Map<String, Integer> mostBlockedFollowerMap = new TreeMap<String, Integer>();
	public static Map<String, Integer> mostPopularMessageMap = new TreeMap<String, Integer>();


	@Override
	public void resetStatsAndSystem() {
		System.out.println("Resetting all the user stats");
		
		//Resetting all the variable values and maps.
		
		lengthOfLongestTweet = 0; 
		mostFollowedUser = null;
		mostProductiveUser = null; 
		mostBlockedFollower = null; 
		mostPopularMessage = null;
		
		if (mostFollowedUserMap.size() != 0)
		{
			mostFollowedUserMap.clear();
		}
		
		if (mostProductiveUserMap.size() != 0)
		{
			mostProductiveUserMap.clear();
		}
		
		if (mostBlockedFollowerMap.size() != 0)
		{
			mostBlockedFollowerMap.clear();
		}
		
		if (mostPopularMessageMap.size() != 0)
		{
			mostPopularMessageMap.clear();
		}
		
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		return mostFollowedUser;
	}

	@Override
	public String getMostPopularMessage() {
		return mostPopularMessage;
	}
	
	@Override
	public String getMostProductiveUser() {
		return mostProductiveUser;
	}

	@Override
	public String getMostBlockedFollower() {
		return mostBlockedFollower;
	}
}
