package ca.mcgill.ecse489.record;

import ca.mcgill.ecse489.packet.PacketCompoent;

/**
 * Generic Type for the record data used in Answer section
 * @author Yang Zhou(260401719)
 *
 * @param <T>
 */
public abstract class RData<T> implements PacketCompoent<RData<T>> {

    private int rdLength;

    public int getRdLength() {
        return rdLength;
    }

    public void setRdLength(int rdLength) {
        this.rdLength = rdLength;
    }
}
