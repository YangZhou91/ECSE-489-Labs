package ca.mcgill.ecse489.record;

import java.nio.ByteBuffer;

import ca.mcgill.ecse489.structures.Domain;

public class CNameRecord extends RData<CNameRecord> {
    
    private final Domain domain;
    
    public CNameRecord() {
        this(new Domain());
    }
    
    public CNameRecord(Domain domain){
        this.domain = domain;
    }

    
    public Domain getDomain() {
        return domain;
    }

    @Override
    public RData<CNameRecord> toBytes(ByteBuffer buf) {
        domain.toBytes(buf);
        return this;
    }

    @Override
    public RData<CNameRecord> fromBytes(ByteBuffer buf) {
        domain.fromBytes(buf);
        return this;
    }

}
