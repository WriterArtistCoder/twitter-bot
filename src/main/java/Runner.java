import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.JSONObject;

/**
 * Run the bot to tweet the comic.
 * 
 * @author https://tinystripz.com
 *
 */
public class Runner {

	// Config file containing API keys and latest comic posted
	public static File configFile = new File("src/main/resources/config.txt");
	// Image file to write comic to
	public static File imageFile = new File("src/main/resources/comic.jpg");

	public static void main(String[] args) {
		// If a config file is passed as an argument, use that
		if (args.length == 1) {
			run(new File(args[0]));
		} // Otherwise use the default config file
		else {
			run(configFile);
		}
	}

	/**
	 * Checks if there's a new comic that needs to be tweeted.
	 * 
	 * @param configFile The file containing the latest comic tweeted
	 */
	public static void run(File configFile) {
		try {
			// Get the comic as a JSONObject
			JSONObject comic = new APIReader(configFile).getComic();

			// Get the latest comic tweeted (messy code, fix later)
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			for (int i = 0; i < 4; i++) {
				br.readLine();
			}
			File tweetFile = new File(br.readLine());

			// Get the latest comic tweeted and close the BufferedReader
			BufferedReader br1 = new BufferedReader(new FileReader(tweetFile));
			br.close();

			// Get the comic number and number of the latest comic tweeted
			String comicNum = comic.getString("title").substring(1, comic.getString("title").indexOf(":"));
			String fileNum = br1.readLine();

			// Check if there's a new comic needing posting
			System.out.println(fileNum); // Print the number of the latest comic tweeted
			if (fileNum.equals(comicNum)) { // If not, alert the user and stop
				System.out.println("We're up to date, homie!");
			} else { // If so, alert the user and tweet
				System.out.println("Bro, we gotta tweet, quick!");
				new Tweeter().run(configFile, imageFile, tweetFile);
			}

			br1.close(); // Close the BufferedReader
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
