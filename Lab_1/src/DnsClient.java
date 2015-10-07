import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DnsClient {

	public static void main(String[] args) {
		
		System.out.println("Fuck");
		/**
		 * Argument Parser
		 */
		// Create Options object
		Options options = new Options();
		
		// add t option, false indicates optional
		options.addOption("t", false, "timeout");
		options.addOption("r", false, "max-retries");
		options.addOption("p", false, "port");
		options.addOption("mx", false, "Mail server");
		options.addOption("ns", false, "Name server");
		
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("t")) {
				System.out.print("timeout");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Domain domain = new Domain().fromString("www.mcgill.ca");
		System.out.println(domain.toString());
		
	}
}
