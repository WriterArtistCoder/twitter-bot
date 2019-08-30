import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import twitter4j.JSONObject;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Tweeter {
	
	// This file should store the latest tweet the twitter bot has made.
	
	public void run(String configFile, String twitterFile) {
		JSONObject comic = new APIReader(configFile).getComic();
		
		try {
			String content = comic.getString("content");
			URL url = new URL(content.substring(content.indexOf("https://1.bp.blogspot.com/"), content.indexOf("\"", content.indexOf("https://1.bp.blogspot.com/"))-1));
			
			BufferedImage img = ImageIO.read(url);
			File file = new File("src/main/resources/comic.jpg");
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String tweet = comic.getString("title")+"\n\nThis #TinyStripz is under a CC-BY-SA 3.0 license.\nView it at "+comic.getString("url")+".";
		String comicNum = comic.getString("title").substring(1, comic.getString("title").indexOf(":"));
		
		try {
			createTweet(tweet, configFile);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(twitterFile)));
			bw.write(comicNum);
			
			bw.close();
		} catch (TwitterException e) {
			if (tweet.length() > 280) {
				try {
					createTweet("Sorry, the tweet for today's comic was too long.\nIt is still available at tinystripz.blogspot.com.", configFile);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTweet(String tweet, String configFile) throws TwitterException {
	    Twitter twitter = Parser.getTwitterInstance(configFile);
		
	    StatusUpdate status = new StatusUpdate(tweet);
	    status.setMedia(new File("src/main/resources/comic.jpg"));
	    
		twitter.updateStatus(status);
	}
}
