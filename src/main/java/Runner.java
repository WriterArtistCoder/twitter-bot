import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Runner {
	
	static String twitterFileName = "C:\\Users\\sodal\\twitter-bot.txt";
	
	public static void main(String[] args) {
		try {
			FeedReader fr = new FeedReader();
			BufferedReader br = new BufferedReader(new FileReader(new File(twitterFileName)));
			
			String comicNum = fr.titles.get(0).substring(1, fr.titles.get(0).indexOf(":"));
			String fileNum = br.readLine();
			System.out.println(fileNum);
			
			if (fileNum.equals(comicNum)) {
				System.out.println("We're up to date, homie!");
			} else {
				System.out.println("Bro, we gotta tweet, quick!");
				new Tweeter().run();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
