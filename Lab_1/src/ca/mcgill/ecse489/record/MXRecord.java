package ca.mcgill.ecse489.record;

import java.nio.ByteBuffer;

import ca.mcgill.ecse489.structures.Domain;

public class MXRecord extends RData<MXRecord> {

    private int prefrence;
    private final Domain exchange;
    
    public MXRecord() {
        this(0, new Domain());
    }
    
    public MXRecord(int preference, Domain exchange) {
        this.prefrence = preference;
        this.exchange = exchange;
    }

    @Override
    public RData<MXRecord> toBytes(ByteBuffer buf) {
        buf.putShort((short)prefrence);
        exchange.toBytes(buf);
        return this;
    }

    @Override
    public RData<MXRecord> fromBytes(ByteBuffer buf) {
        // 16 bit integer 
        prefrence = buf.getShort() & 0xFFFF;
        exchange.fromBytes(buf);
        return this;
    }
    
}
