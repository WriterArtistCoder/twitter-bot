import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Auth {
	
	public static Twitter getTwitterInstance() {
		try {
			BufferedReader b = new BufferedReader(new FileReader("src/main/resources/auth.txt"));
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey("")
			  .setOAuthConsumerSecret("")
			  .setOAuthAccessToken("")
			  .setOAuthAccessTokenSecret("");
			
			TwitterFactory tf = new TwitterFactory(cb.build());
			b.close();
			
			return tf.getInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
