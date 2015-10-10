package ca.mcgill.ecse489.record;

import ca.mcgill.ecse489.packet.PacketCompoent;

public abstract class RData<T> implements PacketCompoent<RData<T>> {

    private int rdLength;

    public int getRdLength() {
        return rdLength;
    }

    public void setRdLength(int rdLength) {
        this.rdLength = rdLength;
    }
}
