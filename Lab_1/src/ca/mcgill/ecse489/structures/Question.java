package ca.mcgill.ecse489.structures;

import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.PacketCompoent;
import ca.mcgill.ecse489.type.Class;
import ca.mcgill.ecse489.type.Type;

/**
 * Object for DNS Question
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Question implements PacketCompoent<Question> {
    /**
     * domain name
     */
    private Domain Qname;
    private Type Qtype;
    private Class Qclass;

    public Domain getQname() {
        return Qname;
    }

    public void setQname(Domain qname) {
        this.Qname = qname;
    }

    public Type getQtype() {
        return Qtype;
    }

    public void setQtype(Type qtype) {
        this.Qtype = qtype;
    }

    public Class getQclass() {
        return Qclass;
    }

    public void setQclass(Class qclass) {
        this.Qclass = qclass;
    }

    public enum Qtype {
        HOST_ADDRESS(0x0001), NAME_SERVER(0x0002), MAIL_SERVER(0x000f);

        private int code;

        private Qtype(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @Override
    public Question toBytes(ByteBuffer buf) {
        // This is domain
        Qname.toBytes(buf);
        // 16bits QTYPE
        buf.putShort((short) Qtype.getCode());
        buf.putShort((short) Qclass.getCode());

        return this;
    }

    @Override
    public Question fromBytes(ByteBuffer buf) {
        Qname = new Domain().fromBytes(buf);
        Qtype = Type.byCode(buf.getShort());
        Qclass = Class.byCode(buf.getShort());
        return this;
    }

    @Override
    public String toString() {
        return "Question [domain=" + Qname +
                ", QTYPE = " + Qtype + ", QCLASS =" + Qclass + "]";
    }

}
