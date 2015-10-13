package ca.mcgill.ecse489.packet;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * An interface that every compoent has to implement
 * 
 * @author Yang Zhou
 *
 */
public interface PacketCompoent<E extends PacketCompoent<E>> {
    E toBytes(ByteBuffer buf) throws IOException;

    E fromBytes(ByteBuffer buf) throws IOException;
}
