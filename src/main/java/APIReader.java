import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import twitter4j.JSONObject;

/**
 * Get the latest comic from the Blogger API.
 * 
 * @author https://tinystripz.com
 *
 */
public class APIReader {

	private String apiKey; // The API key

	/**
	 * Create an APIReader instance from a config file containing the API keys.
	 * 
	 * @param configFile The config file containing the API keys
	 */
	public APIReader(File configFile) {
		try {
			// Get the API key from the config file
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			for (int i = 0; i < 5; i++) {
				br.readLine();
			}
			apiKey = br.readLine();

			// Close the BufferedReader
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the latest comic as a JSONObject.
	 * 
	 * @return The latest comic as a JSONObject
	 */
	public JSONObject getComic() {
		try {
			// The request URL for the Blogger API
			String request = "https://www.googleapis.com/blogger/v3/blogs/1411039632421936126/posts?key=" + apiKey;
			URL obj = new URL(request);

			// Make the request and get the response code
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			int responseCode = con.getResponseCode();

			// Print the request URL and response code
			System.out.println("Sending GET request to URL: " + request);
			System.out.println("Response Code: " + responseCode);
			System.out.println();

			// Ok, dunno what this does
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			// Or this
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Get the comic from the response
			JSONObject getComic = new JSONObject(response.toString()).getJSONArray("items").getJSONObject(0);
			return getComic;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
