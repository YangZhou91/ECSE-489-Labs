package ca.mcgill.ecse489.structures;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import ca.mcgill.ecse489.packet.PacketCompoent;

/**
 * The object for DNS packet
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Packet implements PacketCompoent<Packet> {

    // one header
    private Header header;
    // a number of questions
    private Collection<Question> questions = new ArrayList<>();
    // a number of answers
    private Collection<Answer> answers = new ArrayList<>();

    @Override
    public Packet toBytes(ByteBuffer buf) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Packet fromBytes(ByteBuffer buf) {
        // TODO Auto-generated method stub
        return null;
    }
}
