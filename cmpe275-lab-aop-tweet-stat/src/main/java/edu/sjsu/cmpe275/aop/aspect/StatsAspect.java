package edu.sjsu.cmpe275.aop.aspect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Declaring the required variables and maps.
     */
	TweetStatsImpl stats;
	
	String user = "";
	String message = "";
	String follower = "";
	String followee = "";
	
	int maxFollowerCount = 0;
	int maxTweetLength = 0;
	int mostBlockedCount = 0;
	int mostPopularMessageCount = 0;
	
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) {
		
		user = (String) joinPoint.getArgs()[0];
		message = (String) joinPoint.getArgs()[1];
		
		/*
		 * Calculating length of the longest tweet.
		 */
		if (message.length() > TweetStatsImpl.lengthOfLongestTweet) {
			TweetStatsImpl.lengthOfLongestTweet = message.length();
		}

		
		if(message.length() > 140)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			/*
			 * Adding the message into the most Popular Message Map
			 */
			if(TweetStatsImpl.mostFollowedUserMap.containsKey(user))
			{
				TweetStatsImpl.mostPopularMessageMap.put(message, TweetStatsImpl.mostFollowedUserMap.get(user).size());
			}

			/*
			 * Adding tweets to most productive user map.
			 */
			if (TweetStatsImpl.mostProductiveUserMap.containsKey(user)) 
			{
				TweetStatsImpl.mostProductiveUserMap.put(user,
						(TweetStatsImpl.mostProductiveUserMap.get(user) + message.length()));
			} else {
				TweetStatsImpl.mostProductiveUserMap.put(user, message.length());
			}

		}
		
	}

	@After("execution(public * edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void followTheUser(JoinPoint joinPoint) {
		
		/*
		 * Adding follower and followee to the most followed user map.
		 */
		follower = (String) joinPoint.getArgs()[0];
		followee = (String) joinPoint.getArgs()[1];
		Set<String> followeeuserset = null;
		if (!follower.equals(followee) || follower != followee) {
			if (!TweetStatsImpl.mostFollowedUserMap.containsKey(followee)) {
				followeeuserset = new HashSet<String>();
			} else {
				followeeuserset = TweetStatsImpl.mostFollowedUserMap.get(followee);
			}
			followeeuserset.add(follower);
			TweetStatsImpl.mostFollowedUserMap.put(followee, followeeuserset);
		} else {
			System.out.println("Follower and followee cannot be the same");
		}

	}
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetService.block(..))")
	public void blockTheFollower(JoinPoint joinPoint) {

		user = (String) joinPoint.getArgs()[0];
		follower = (String) joinPoint.getArgs()[1];

		/*
		 * Adding the blocked follower to the most blocked user map.
		 */
		if (TweetStatsImpl.mostBlockedFollowerMap.containsKey(follower)) {
			TweetStatsImpl.mostBlockedFollowerMap.put(follower,
					(TweetStatsImpl.mostBlockedFollowerMap.get(follower) + 1));
		} else {
			TweetStatsImpl.mostBlockedFollowerMap.put(follower, 1);
		}
		
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostProductiveUser(..))")
	public void getTheMostProductiveUser() {
		/*
		 * Finding the most productive user
		 */

		if (TweetStatsImpl.mostProductiveUserMap.size() > 0)
			maxTweetLength = (Collections.max(TweetStatsImpl.mostProductiveUserMap.values())); 

		if (maxTweetLength == 0)
			TweetStatsImpl.mostProductiveUser = null; 
		for (Entry<String, Integer> entry : TweetStatsImpl.mostProductiveUserMap.entrySet()) {
			if (entry.getValue() == maxTweetLength) {
				TweetStatsImpl.mostProductiveUser = entry.getKey();
				break; 
			}
		}

	}


	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostFollowedUser(..))")
	public void getTheMostFollowedUser() {
		
		/*
		 * Counting the max number for which user is followed.
		 */
		for (Entry<String, Set<String>> entry : TweetStatsImpl.mostFollowedUserMap.entrySet()) {

			if (entry.getValue().size() > maxFollowerCount) {
				maxFollowerCount = entry.getValue().size();  
			}
		}
		
		/*
		 * Finding the first most followed user.
		 */
		for (Entry<String, Set<String>> entry : TweetStatsImpl.mostFollowedUserMap.entrySet()) {

			if (entry.getValue().size() == maxFollowerCount) {
				maxFollowerCount = entry.getValue().size(); 
				TweetStatsImpl.mostFollowedUser = entry.getKey(); 
				break;
			}
		}

	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostBlockedFollower(..))")
	public void getTheMostBlockedFollower() {
		
		/*
		 * Calculating the max number for which any follower is blocked.
		 */
		if (TweetStatsImpl.mostBlockedFollowerMap.size() > 0)
		{
			mostBlockedCount = (Collections.max(TweetStatsImpl.mostBlockedFollowerMap.values())); 
		}

		//Getting the first follower who is blocked for max number of times
		for(Entry<String, Integer> entry : TweetStatsImpl.mostBlockedFollowerMap.entrySet())
		{
			if(entry.getValue() == mostBlockedCount)
			{
				mostBlockedCount = entry.getValue();
				TweetStatsImpl.mostBlockedFollower = entry.getKey();
				break;
			}
		}
		
		
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostPopularMessage(..))")
	public void getTheMostPopularMessage() {
		
		/*
		 * Counting the max number for which message is followed.
		 */
		if (TweetStatsImpl.mostPopularMessageMap.size() > 0)
		{
			mostPopularMessageCount = (Collections.max(TweetStatsImpl.mostPopularMessageMap.values()));
			
			/*
			 * Getting the first popular message
			 */
			for(Entry<String, Integer> entry : TweetStatsImpl.mostPopularMessageMap.entrySet())
			{
				if(entry.getValue() == mostPopularMessageCount)
				{
					mostPopularMessageCount = entry.getValue();
					TweetStatsImpl.mostPopularMessage = entry.getKey();
					break;
				}
			}
		}
		
		
	}
}

