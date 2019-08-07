import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;

public class Retreiver {

	public static void main(String[] args) {

		System.out.println(readRSSFeed("https://tinystripz.blogspot.com/feeds/posts/default"));
	}

	public static String readRSSFeed(String urlAddress) {
		try {
			URL rssUrl = new URL(urlAddress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String sourceCode = "";
			String line;
			
			int next = 0;
			
			while ((line = in.readLine()) != null) {
				while (line.indexOf("<title type='text'>", next) > -1) {
					int start = line.indexOf("<title type='text'>", next);
					int end = line.indexOf("</title>", next);
					String title = line.substring(start+19, end);
					
					System.out.println(title);
					next = end;
				}
			}
			in.close();
			return sourceCode;
		} catch (MalformedURLException ue) {
			System.out.println("Malformed URL");
		} catch (IOException ioe) {
			System.out.println("Something went wrong reading the contents");
		}
		return null;
	}
}