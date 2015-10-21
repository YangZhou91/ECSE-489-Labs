package ca.mcgill.ecse489.structures;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ca.mcgill.ecse489.packet.PacketCompoent;

/**
 * A object for domain name, organized by size and labels
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Domain implements PacketCompoent<Domain> {

    private final Collection<String> labels = new ArrayList<>();

    /**
     * Constructor for domain
     */
    public Domain() {

    }

    /**
     * Initialize a Domain object from labels
     * 
     * @param labels, consists zero to numbers of string
     */
    public Domain(String... labels) {
        this.labels.addAll(Arrays.asList(labels));
    }

    /**
     * Contruct a Domain from a String
     * 
     * @param domainName
     * @return a Domain
     */
    public static Domain fromString(String domainName) {
        return new Domain(domainName.split("\\."));
    }

    // Need Util for this method to convert from string to bytebuffer
    @Override
    public Domain toBytes(ByteBuffer buf) {
        for (String singleLabel : labels) {
            byte[] label = singleLabel.getBytes();
            int length = label.length;
            buf.put((byte) length);
            buf.put(label);
        }
        // Domain name ends with a zero
        buf.put((byte) 0);

        return this;
    }

    public String getDomain() {
        StringBuilder builder = new StringBuilder();
        for (String label : labels) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(label);
        }
        return builder.toString();
    }

    @Override
    public Domain fromBytes(ByteBuffer buf) {
        // clear existing domain
        labels.clear();
        // Since the number of label is unknown
        while (true) {
            // 255 octects
            int labelLength = buf.get() & 0xFF;
            if (labelLength == 0) {
                break;
            }

            // Detect the start of the pointer 11
            if ((labelLength & 0b1100_0000) == 0b1100_0000) {
                // figure offset out
                int PointerOffset = (labelLength & 0b0011_1111) << 8 | (buf.get() & 0xFF);
                // pointer
                Domain pointee = new Domain();
                // Recursion parsing
                pointee.fromBytes((ByteBuffer) buf.duplicate().position(PointerOffset));
                labels.addAll(pointee.labels);
                break;
            }

            byte[] labelBytes = new byte[labelLength];
            buf.get(labelBytes);
            labels.add(new String(labelBytes));
        }

        return this;
    }

    @Override
    public String toString() {
        return labels.toString();
    }
}
