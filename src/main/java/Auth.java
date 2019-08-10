import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Auth {

	public static Twitter getTwitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("KmsRmSPoDFxuYxvCFhU9bZeNY")
				.setOAuthConsumerSecret("t4wR6HqQc2Ig4x7Z6wFZNirACjruy3yDBBqB5JqBgCc0Ro0Ctg")
				.setOAuthAccessToken("1124771899727159296-sJ3UC63uRQyJJS6lrcEpaXWoZUZ5LM")
				.setOAuthAccessTokenSecret("hGl5beNvUxIEvOyYae1gE8dDPwuGzONB2VI7XCvfhUj6m");

		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

}
