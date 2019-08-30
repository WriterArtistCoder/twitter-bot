import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import twitter4j.JSONObject;

public class APIReader {

	private String apiKey;
	
	public APIReader(String configFilename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(configFilename)));
			for (int i = 0; i < 5; i++) {
				br.readLine();
			}
			
			apiKey = br.readLine();
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject getComic() {
		try {
			String request = "https://www.googleapis.com/blogger/v3/blogs/1411039632421936126/posts?key="+apiKey;
			URL obj = new URL(request);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			int responseCode = con.getResponseCode();

			System.out.println("Sending GET request to URL: " + request);
			System.out.println("Response Code: " + responseCode);
			System.out.println("");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JSONObject getComic = new JSONObject(response.toString()).getJSONArray("items").getJSONObject(0);
			return getComic;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
