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
			  .setOAuthConsumerKey("1pbhZTMDBBhv3QxXqjuyYFKsz")
			  .setOAuthConsumerSecret("3cvrBjUlrMhCTQIlIakUafgdMLYuJaWloBvc6pDovzMjo9c76d")
			  .setOAuthAccessToken("1124771899727159296-25DdZJmEX1Kar9A3nzX71FTOImwsMg")
			  .setOAuthAccessTokenSecret("7bXbXZcMmQWZtAjGBX6MaLDd2VZlvepyX16NCdqbcUuQt");
			
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
