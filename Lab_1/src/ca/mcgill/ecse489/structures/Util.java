package ca.mcgill.ecse489.structures;
import java.nio.ByteBuffer;

/**
 * General tools for assisting
 * @author Gavin
 *
 */
public class Util {
    
    /**
     * The helper method write domain labels to buf
     * @param buf
     * @param labels
     */
    public static void writeDomainName(ByteBuffer buf, byte[] labels){
        buf.put((byte)labels.length);
        buf.put(labels);
    }
    
    public static byte[] getIpAddress(String domain){
        String[] labels = domain.split("\\.");
        byte[] ipAddress = new byte[4];
        for (int i = 0; i < ipAddress.length; i++) {
            ipAddress[i] = (byte)Integer.parseInt(labels[i]);
        }
        return ipAddress;
    }
}
