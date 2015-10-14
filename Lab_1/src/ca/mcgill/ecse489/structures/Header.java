package ca.mcgill.ecse489.structures;

import java.io.IOException;
import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.PacketCompoent;

/**
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Header implements PacketCompoent<Header> {

    // Short is 16-bit signed two's complement integer
    private short id;
    private boolean qr;
    private Header.OPCODE opcode;
    private boolean aa;
    /**
     * is Truncated
     */
    private boolean TC;
    /**
     * is Desired Recursion
     */
    private boolean RD;
    private boolean RA;
    /**
     * Response Code
     */
    private Header.RCODE rcode;
    private short Z;

    /**
     * Number of of entries in the question section
     */
    private short qdcount;
    /**
     * Number of answer entries
     */
    private short ancount;
    // private short nscount;

    /**
     * Number of Additional Entries
     */
    private short arcount;
    /**
     * Number of A
     */
    private short authorityRecords;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public boolean getQr() {
        return qr;
    }

    public void setQr(boolean qr) {
        this.qr = qr;
    }

    public Header.OPCODE getOpcode() {
        return opcode;
    }

    public void setOpcode(Header.OPCODE opcode) {
        this.opcode = opcode;
    }

    public boolean isAa() {
        return aa;
    }

    public void setAa(boolean aa) {
        this.aa = aa;
    }

    public boolean isTC() {
        return TC;
    }

    public void setTC(boolean tC) {
        TC = tC;
    }

    public boolean getRD() {
        return RD;
    }

    public void setRD(boolean rD) {
        RD = rD;
    }

    public boolean isRA() {
        return RA;
    }

    public void setRA(boolean rA) {
        RA = rA;
    }

    public short getZ() {
        return Z;
    }

    public void setZ(short z) {
        Z = z;
    }

    public Header.RCODE getRcode() {
        return rcode;
    }

    public void setRcode(Header.RCODE rcode) {
        this.rcode = rcode;
    }

    public short getQdcount() {
        return qdcount;
    }

    public void setQdcount(short qdcount) {
        this.qdcount = qdcount;
    }

    public short getAncount() {
        return ancount;
    }

    public void setAncount(short ancount) {
        this.ancount = ancount;
    }

    // public short getNscount() {
    // return nscount;
    // }

    // public void setNscount(short nscount) {
    // this.nscount = nscount;
    // }

    public short getArcount() {
        return arcount;
    }

    public void setArcount(short arcount) {
        this.arcount = arcount;
    }

    public short getAuthorityRecords() {
        return authorityRecords;
    }

    public void setAuthorityRecords(short authorityRecords) {
        this.authorityRecords = authorityRecords;
    }

    public enum QR {
        QUERY(0), RESPONSE(1);
        private int code;

        private QR(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

    public enum OPCODE {
        QUERY(0);
        private final int code;

        private OPCODE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Header.OPCODE byCode(int code) {
            for (Header.OPCODE o : values()) {
                if (o.code == code) {
                    return o;
                }
            }
            throw new IllegalArgumentException("No opcode for code " + code + "exists");
        }
    }

    public enum RCODE {
        NO_ERROR(0), FORMAT_ERROR(1), SERVER_FAILURE(2), NAME_ERROR(3), NOT_IMPLMENTED(4), REFUSED(5);
        private final int code;

        private RCODE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Header.RCODE byCode(int code) {
            for (Header.RCODE r : values()) {
                if (r.code == code) {
                    return r;
                }
            }
            throw new IllegalArgumentException("No RCODE for code " + code + "exists");
        }
    }

    @Override
    public Header toBytes(ByteBuffer buf) throws IOException {
        buf.putShort(id);
        short flags = (short) ((qr ? 0 : 1) << 15);
        flags |= (opcode.getCode() & 0b1111) << 11;
        flags |= (RD ? 1 : 0) << 8;

        buf.putShort(flags);
        buf.putShort(qdcount);
        buf.putShort(ancount);
        buf.putShort(arcount);
        buf.putShort(authorityRecords);
        return this;
    }

    @Override
    public Header fromBytes(ByteBuffer buf) throws IOException {
        id = buf.getShort();
        int flags = buf.getShort();
        qr = ((flags >> 15) & 1) == 0;
        opcode = OPCODE.byCode((flags >> 11) & 0b1111);
        aa = ((flags >> 10) & 1) == 1;
        TC = ((flags >> 9) & 1) == 1;
        RD = ((flags >> 8) & 1) == 1;
        RA = (flags >> 7 & 1) == 1;
        rcode = RCODE.byCode(flags & 0b1111);

        qdcount = buf.getShort();
        ancount = buf.getShort();
        arcount = buf.getShort();
        authorityRecords = buf.getShort();

        return this;
    }

    @Override
    public String toString() {
        return "Header [id = " + id + ", QR =" + qr + ",opcode =" + opcode +
                ", AA =" + aa + ",TC =" + TC + ",RD =" + RD + ",RA =" + RA +
                ",Z =" + Z + ",RCODE = " + rcode + ",QDCOUNT = " + qdcount + ",ANCOUNT = "
                + ancount + ",ARCOUNT = " + arcount + ",AuthorityRecord" + authorityRecords + "]";
    }
}
