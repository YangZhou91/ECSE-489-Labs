package ca.mcgill.ecse489.record;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * A (IP address) record
 * 
 * @author Yang Zhou
 *
 */
public class ARecord extends RData<ARecord> {

    private InetAddress ipAddress;

    public ARecord() {
        // TODO Auto-generated constructor stub
    };

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public RData<ARecord> toBytes(ByteBuffer buf) {
        // TODO check if getAddress is allowed
        buf.put(ipAddress.getAddress());

        return this;
    }

    @Override
    public RData<ARecord> fromBytes(ByteBuffer buf) {
        byte[] bytes = new byte[4];
        buf.get(bytes);
        try {
            ipAddress = InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String toString() {
        return "A [address=" + ipAddress + "]";
    }
}
