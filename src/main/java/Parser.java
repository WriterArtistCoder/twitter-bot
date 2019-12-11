import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Parse a file containing API keys and returns a Twitter instance.
 * 
 * @author https://tinystripz.com
 *
 */
public class Parser {

	/**
	 * Parse a file containing API keys and returns a Twitter instance.
	 * 
	 * @param configFile The file to parse
	 * @return A Twitter instance
	 */
	public static Twitter getTwitterInstance(File configFile) {
		try {
			// Create a BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(configFile));

			// Create a ConfigurationBuilder from the API keys
			// I'll be honest, I have no idea what this actually does.
			// It's coding magic. Whatever.
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(br.readLine()).setOAuthConsumerSecret(br.readLine())
					.setOAuthAccessToken(br.readLine()).setOAuthAccessTokenSecret(br.readLine());

			// Create a TwitterFactory from the ConfigurationBuilder
			TwitterFactory tf = new TwitterFactory(cb.build());

			// Close the BufferedReader
			br.close();

			return tf.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
