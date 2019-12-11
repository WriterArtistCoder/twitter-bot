import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import twitter4j.JSONObject;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Tweet the comic.
 * 
 * @author https://tinystripz.com
 *
 */
public class Tweeter {

	/**
	 * Run the bot and do the tweeting. **WARNING:** This method will break with any
	 * other than a `.jpg` file.
	 * 
	 * @param configFile  The configuration file containing API keys and other
	 *                    information.
	 * @param imageFile   The file to save the latest comic to.
	 * @param twitterFile The file in which to write the number of the latest comic
	 *                    tweeted.
	 */
	public void run(File configFile, File imageFile, File twitterFile) {
		// Get the comic as a JSONObject
		JSONObject comic = new APIReader(configFile).getComic();

		try {
			String content = comic.getString("content"); // Get the HTML content of the comic
			URL url = new URL(content.substring(content.indexOf("https://1.bp.blogspot.com/"),
					content.indexOf("\"", content.indexOf("https://1.bp.blogspot.com/")) - 1)); // Extract the image
																								// from the content
			BufferedImage img = ImageIO.read(url); // Read a BufferedImage from the image URL

			// Write the image to a file
			File file = imageFile;
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// The tweet to make
		String tweet = comic.getString("title") + "\n\nThis #TinyStripz is under a CC-BY-SA 3.0 license.\nView it at "
				+ comic.getString("url") + ".";
		// Extract the comic number from its title
		String comicNum = comic.getString("title").substring(1, comic.getString("title").indexOf(":"));

		try {
			// Tweet the comic
			createTweet(tweet, configFile, imageFile);

			// Record the number of the latest comic tweeted
			BufferedWriter bw = new BufferedWriter(new FileWriter(twitterFile));
			bw.write(comicNum);

			// Close the writer
			bw.close();
		} catch (TwitterException e) {
			// If the tweet is too long
			if (tweet.length() > 280) {
				try {
					// Tweet an apology instead
					createTweet(
							"Sorry, we ran into a problem. The latest comic is still available at https://tinystripz.com!",
							configFile);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tweets text.
	 * 
	 * @param tweet      The text that goes with the image.
	 * @param configFile The configuration file containing API keys and other
	 *                   information.
	 * @throws TwitterException
	 */
	public static void createTweet(String tweet, File configFile) throws TwitterException {
		// Get a Twitter instance from the API keys
		Twitter twitter = Parser.getTwitterInstance(configFile);

		// Create a StatusUpdate containing the tweet and post it
		StatusUpdate status = new StatusUpdate(tweet);
		twitter.updateStatus(status);
	}

	/**
	 * Tweets an image and text.
	 * 
	 * @param tweet      The text that goes with the image.
	 * @param configFile The configuration file containing the API keys.
	 * @param imageFile  The file containing the image.
	 * @throws TwitterException
	 */
	public static void createTweet(String tweet, File configFile, File imageFile) throws TwitterException {
		// Get a Twitter instance from the API keys
		Twitter twitter = Parser.getTwitterInstance(configFile);

		// Create a StatusUpdate containing the tweet and image file
		StatusUpdate status = new StatusUpdate(tweet);
		status.setMedia(imageFile);

		// Post the StatusUpdate as a tweet
		twitter.updateStatus(status);
	}
}
