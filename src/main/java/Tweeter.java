import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Tweeter {
	
	// This file should store the latest tweet the twitter bot has made.
	
	public void run() {
		FeedReader fr = new FeedReader();
		
		try {
			URL url = new URL(fr.images.get(0));
			BufferedImage img = ImageIO.read(url);
			File file = new File("src/main/resources/comic.jpg");
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String tweet = fr.titles.get(0)+"\n\nThis comic is under a CC-BY-SA 3.0 license.\nView the comic at "+fr.links.get(0)+".";
		String comicNum = fr.titles.get(0).substring(1, fr.titles.get(0).indexOf(":"));
		
		try {
			createTweet(tweet);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Runner.twitterFileName)));
			bw.write(comicNum);
			
			bw.close();
		} catch (TwitterException e) {
			if (tweet.length() > 280) {
				try {
					createTweet("Sorry, the tweet for today's comic was too long.\nIt is still available at tinystripz.blogspot.com.");
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createTweet(String tweet) throws TwitterException {
	    Twitter twitter = Auth.getTwitterInstance();
		
	    StatusUpdate status = new StatusUpdate(tweet);
	    status.setMedia(new File("src/main/resources/comic.jpg"));
	    
		twitter.updateStatus(status);
	}
}
