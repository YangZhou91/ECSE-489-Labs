package ca.mcgill.ecse489.structures;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * A object for domain name, organized by size and labels
 * @author Yang Zhou(260401719)
 *
 */
public class Domain {

	private final Collection<String> labels = new ArrayList<>();
	
	/**
	 * Constructor for domain
	 */
	public Domain(){
		
	}
	public Domain(String...labels){
		this.labels.addAll(Arrays.asList(labels));
	}
	
	/**
	 * Contruct a Domain from a String
	 * @param domainName
	 * @return
	 */
	public static Domain fromString(String domainName){
		return new Domain(domainName.split("\\."));
	}
	
	// Need Util for this method to convert from string to bytebuffer
	public Domain toBytes(ByteBuffer buf){
		for (String singleLabel : labels) {
			byte[] label = singleLabel.getBytes();
			int length = label.length;
			buf.put((byte) length);
			buf.put(label);
		}
		// Domain name ends with a zero
		buf.put((byte)0);
		
		return this;
	}
	
	public Domain fromBytes(ByteBuffer buf){
		// clear existing domain
		labels.clear();
		
		return this;
	}
	
	@Override
	public String toString(){
		return "Domain [labels"+ labels +"]";
	}
}
