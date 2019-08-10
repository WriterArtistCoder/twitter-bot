import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Tweeter {
	
	public static void main(String[] args) {
		FeedReader data = new FeedReader();
		
		try {
			URL url = new URL(data.images.get(0));
			BufferedImage img = ImageIO.read(url);
			File file = new File("src/main/resources/comic.jpg");
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String tweet = data.titles.get(0)+"\n\nThis comic is under a CC-BY-SA 3.0 license.\nView the comic at "+data.links.get(0)+".";
		try {
			createTweet(tweet);
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
		}
	}
	
	public static void createTweet(String tweet) throws TwitterException {
	    Twitter twitter = Auth.getTwitterInstance();
		
	    StatusUpdate status = new StatusUpdate(tweet);
	    status.setMedia(new File("src/main/resources/comic.jpg"));
	    
		twitter.updateStatus(status);
	}
	
	public static String emojify(String s) {
		String r = "";
		for (char c : s.toCharArray()) {
			r+=("⃣"+c).toUpperCase();
		}
		
		return r;
	}
}
