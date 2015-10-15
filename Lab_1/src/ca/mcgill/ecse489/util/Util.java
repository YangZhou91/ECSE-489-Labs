package ca.mcgill.ecse489.util;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * General tools for assisting
 * 
 * @author Gavin
 *
 */
public class Util {

    /**
     * The helper method write domain labels to buf
     * 
     * @param buf
     * @param labels
     */
    public static void writeDomainName(ByteBuffer buf, byte[] labels) {
        buf.put((byte) labels.length);
        buf.put(labels);
    }

    /**
     * An alternative method for getByName method
     * 
     * @param address
     * @return
     */
    public static byte[] getIpAddress(String address) {
        String[] labels = address.split("\\.");
        byte[] ipAddress = new byte[4];
        for (int i = 0; i < ipAddress.length; i++) {
            // TODO need protection for ip octant
            try {
                ipAddress[i] = (byte) Integer.parseInt(labels[i]);
            } catch (NumberFormatException e) {
                System.err.println(
                        "ERROR" + "\t" + "Incorrect input syntax: " + "[" + "Ip address converstion error" + "]");
            }
        }
        return ipAddress;
    }

    public static byte[] getIpAddressinByteArray(InetAddress address) {
        byte[] ipAddress = new byte[4];
        System.out.print(address.toString());
        // address.
        return ipAddress;
    }
}
