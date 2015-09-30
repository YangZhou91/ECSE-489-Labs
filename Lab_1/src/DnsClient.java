import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DnsClient {

	public static void main(String[] args) throws IOException {
		// Open a reader to input from the command line
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String sentence;
		
		// Read input from the user:
		System.out.println("Type a message and hit enter.");
		sentence = inFromUser.readLine();
		
		System.out.println(sentence);
	}

}
