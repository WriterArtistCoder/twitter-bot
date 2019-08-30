import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.JSONObject;

public class Runner {

	
	public static void main(String[] args) {
		if (args.length == 1) {
			run(args[0]);
		} else {
			run("src/main/resources/config.txt");
		}
	}
	
	public static void run(String configFile) {
		try {
			JSONObject comic = new APIReader(configFile).getComic();
			BufferedReader br = new BufferedReader(new FileReader(new File(configFile)));
			for (int i = 0; i < 4; i++) { // Gets to the line with the filename of the file we want
				br.readLine();
			}

			String tweetFile = br.readLine();
			BufferedReader br1 = new BufferedReader(new FileReader(new File(tweetFile)));
			br.close();

			String comicNum = comic.getString("title").substring(1, comic.getString("title").indexOf(":"));
			String fileNum = br1.readLine();
			
			System.out.println(fileNum);
			if (fileNum.equals(comicNum)) {
				System.out.println("We're up to date, homie!");
			} else {
				System.out.println("Bro, we gotta tweet, quick!");
				new Tweeter().run(configFile, tweetFile);
			}

			br1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
