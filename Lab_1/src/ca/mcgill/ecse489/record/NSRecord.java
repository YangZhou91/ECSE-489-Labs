package ca.mcgill.ecse489.record;

import java.nio.ByteBuffer;

import ca.mcgill.ecse489.structures.Domain;

/**
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class NSRecord extends RData<NSRecord> {

    private final Domain domain;

    public NSRecord() {
        this(new Domain());
    }

    public NSRecord(Domain domain) {
        this.domain = domain;
    }

    @Override
    public RData<NSRecord> toBytes(ByteBuffer buf) {
        domain.toBytes(buf);
        return this;
    }

    @Override
    public RData<NSRecord> fromBytes(ByteBuffer buf) {
        domain.fromBytes(buf);
        return null;
    }

    @Override
    public String toString() {
        return domain.getDomain();
    }
}
