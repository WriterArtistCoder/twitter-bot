import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Auth {

	public static Twitter getTwitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("your consumer key")
				.setOAuthConsumerSecret("your consumer secret")
				.setOAuthAccessToken("your access token")
				.setOAuthAccessTokenSecret("your access token secret");

		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
