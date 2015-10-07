import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * 
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
		for (String string : labels) {
			buf = ByteBuffer.wrap(string.getBytes());
		}
		
		return this;
	}
	
	@Override
	public String toString(){
		return "Domain [labels"+ labels +"]";
	}
}
