import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;

public class FeedReader {

	ArrayList<String> titles;
	ArrayList<String> images;
	
	public FeedReader() {
		titles = new ArrayList<String>();
		images = new ArrayList<String>();
		readRSSFeed("https://tinystripz.blogspot.com/feeds/posts/default");
	}

	public ArrayList<ArrayList<String>> readRSSFeed(String urlAddress) {
		try {
			URL rssUrl = new URL(urlAddress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			ArrayList<ArrayList<String>> rss = new ArrayList<ArrayList<String>>();
			String line;
			
			int next = 0;
			
			while ((line = in.readLine()) != null) {
				while (line.indexOf("<entry>", next) > -1) {
					int start = line.indexOf("<entry>", next);
					int end = line.indexOf("</entry>", next);
					String entry = line.substring(start+7, end);
					
					String title = entry.substring(entry.indexOf("<title type='text'>")+19, entry.indexOf("</title>"));

					int imageURLEnd = entry.indexOf("&quot;", entry.indexOf("https://1.bp.blogspot.com/"))-6;	
					String imageURL = entry.substring(entry.indexOf("https://1.bp.blogspot.com/"), imageURLEnd);
					
					ArrayList<String> properties = new ArrayList<String>();
					titles.add(title);
					images.add(imageURL);
					rss.add(properties);
					
					next = end+8;
				}
			}
			in.close();
			
			return rss;
		} catch (MalformedURLException ue) {
			System.out.println("Malformed URL");
		} catch (IOException ioe) {
			System.out.println("Something went wrong reading the contents");
		}
		
		return null;
	}
}