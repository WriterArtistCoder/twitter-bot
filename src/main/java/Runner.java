import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Runner {

	
	public static void main(String[] args) {
		if (args.length == 1) {
			run(args[0]);
		} else {
			run("src/main/resources/config.txt");
		}
	}
	
	public static void run(String configFilename) {
		try {
			FeedReader fr = new FeedReader();
			BufferedReader br = new BufferedReader(new FileReader(new File(configFilename)));
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			// Gets to the line with the filename of the file we want

			String tweetFilename = br.readLine();
			BufferedReader br1 = new BufferedReader(new FileReader(new File(tweetFilename)));
			br.close();

			String comicNum = fr.titles.get(0).substring(1, fr.titles.get(0).indexOf(":"));
			String fileNum = br1.readLine();
			
			System.out.println(fileNum);
			if (fileNum.equals(comicNum)) {
				System.out.println("We're up to date, homie!");
			} else {
				System.out.println("Bro, we gotta tweet, quick!");
				new Tweeter().run(configFilename, tweetFilename);
			}

			br1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
