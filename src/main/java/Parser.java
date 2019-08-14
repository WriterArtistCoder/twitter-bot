import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Parser {

	public static Twitter getTwitterInstance(String configFilename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(configFilename)));

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
				.setOAuthConsumerKey(br.readLine())
				.setOAuthConsumerSecret(br.readLine())
				.setOAuthAccessToken(br.readLine())
				.setOAuthAccessTokenSecret(br.readLine());

			TwitterFactory tf = new TwitterFactory(cb.build());
			br.close();

			return tf.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
