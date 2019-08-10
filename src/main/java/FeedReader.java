import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;

public class FeedReader {

	ArrayList<String> titles;
	ArrayList<String> images;
	ArrayList<String> links;
	
	public FeedReader() {
		titles = new ArrayList<String>();
		images = new ArrayList<String>();
		links = new ArrayList<String>();
		readRSSFeed("https://tinystripz.blogspot.com/feeds/posts/default");
	}

	public void readRSSFeed(String urlAddress) {
		try {
			URL rssUrl = new URL(urlAddress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
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
				
					// This part is starting from index 45 instead of the index of
					// <link rel="alternate" + 45! Maybe it thinks the index is 0?
					int comicURLStart = entry.indexOf("<link rel='alternate'")+45;
					int comicURLEnd = entry.indexOf("' title='", comicURLStart);
					
					titles.add(title);
					images.add(imageURL);
					links.add(entry.substring(comicURLStart, comicURLEnd));
					
					next = end+8;
				}
			}
			in.close();
		} catch (MalformedURLException ue) {
			System.out.println("Malformed URL");
		} catch (IOException ioe) {
			System.out.println("Something went wrong reading the contents");
		}
	}
}