/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet; 
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

public class Extract {
     /* @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if(tweets.isEmpty()) {
        	throw new IllegalArgumentException("Tweets List is empty!");
        }
        Instant earliest = tweets.get(0).getTimestamp();
        Instant latest = earliest;
        
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(earliest)) {
                earliest = tweet.getTimestamp();
            }
            if (tweet.getTimestamp().isAfter(latest)) {
                latest = tweet.getTimestamp();
            }
        }
        
        return new Timespan(earliest, latest);    	
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	 Set<String> mentionedUsers = new HashSet<>();
         Pattern patternForMentionedUserNames = Pattern.compile("(?<=^|\\s)@(\\w+)(?=\\s|$)");
         
         for (Tweet tweet : tweets) {
             Matcher matcher = patternForMentionedUserNames.matcher(tweet.getText());
             while (matcher.find()) {
                 mentionedUsers.add(matcher.group(1).toLowerCase());
             }
         }
         
         return mentionedUsers;    
    }

}