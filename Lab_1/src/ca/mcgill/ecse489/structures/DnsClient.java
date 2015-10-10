package ca.mcgill.ecse489.structures;
import java.nio.ByteBuffer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class DnsClient {
    
    public static final String DNS_HOST = "132.206.85.18";

	public static void main(String[] args) {
		
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
		Domain domain2 = new Domain().fromString("www.google.com");
		ByteBuffer buf =  ByteBuffer.allocate(1024);
		
		domain.toBytes(buf);
		System.out.println(buf);
		domain2.toBytes(buf);
		System.out.println(buf);
//		System.out.println(domain.toString());
		
	}
}
