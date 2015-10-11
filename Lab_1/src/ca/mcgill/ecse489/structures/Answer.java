package ca.mcgill.ecse489.structures;

import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.PacketCompoent;
import ca.mcgill.ecse489.record.ARecord;
import ca.mcgill.ecse489.record.CNameRecord;
import ca.mcgill.ecse489.record.MXRecord;
import ca.mcgill.ecse489.record.NSRecord;
import ca.mcgill.ecse489.record.RData;
import ca.mcgill.ecse489.type.Class;
import ca.mcgill.ecse489.type.Type;

/**
 * @author Yan Liu (260152375)
 *
 */
public class Answer implements PacketCompoent<Answer> {
    private Domain domain;
    private Type answerType;
    private Class answerClass;
    private int ttl;
    private short rdLength;
    // any type
    private RData<?> rData;

    public Domain getAnswerName() {
        return domain;
    }

    public void setAnswerName(Domain answerName) {
        this.domain = answerName;
    }

    public Type getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Type answerType) {
        this.answerType = answerType;
    }

    public Class getAnswerClass() {
        return answerClass;
    }

    public void setAnswerClass(Class answerClass) {
        this.answerClass = answerClass;
    }

    public int getAnswerTTL() {
        return ttl;
    }

    public void setAnswerTTL(int answerTTL) {
        this.ttl = answerTTL;
    }

    public short getRdLength() {
        return rdLength;
    }

    public void setRdLength(short rdLength) {
        this.rdLength = rdLength;
    }

    public RData<?> getrData() {
        return rData;
    }

    public void setrData(RData<?> rData) {
        this.rData = rData;
    }

    @Override
    public Answer toBytes(ByteBuffer buf) {
        domain.toBytes(buf);
        // type
        buf.putShort((short) answerType.getCode());
        buf.putShort((short) answerClass.getCode());
        buf.putInt((int) ttl);

        // 16 bit
        ByteBuffer recordDataBuffer = ByteBuffer.allocate(65536);
        rData.toBytes(recordDataBuffer);
        recordDataBuffer.flip();
        buf.putShort((short) recordDataBuffer.limit());
        buf.put(recordDataBuffer);

        return this;
    }

    @Override
    public Answer fromBytes(ByteBuffer buf) {
        // sequential read from buffer
        domain = new Domain().fromBytes(buf);
        answerType = Type.byCode(buf.getShort());
        answerClass = Class.byCode(buf.getShort());
        // 32 bits
        ttl = buf.getInt() & 0xFFFFFFFF;
        int rdLength = buf.getShort() & 0xFFFF;

        switch (answerType) {
            case CNAME:
                rData = new CNameRecord();
                break;

            case NS:
                rData = new NSRecord();
                break;

            case MX:
                rData = new MXRecord();
                break;
            default:
                break;

        }

        if (answerClass == Class.IN) {
            switch (answerType) {
                case A:
                    rData = new ARecord();
                    break;
                default:
                    break;

            }
        }

        if (rData != null) {
            rData.setRdLength(rdLength);
            rData.fromBytes(buf);
        } else {
            System.out.println("unknonw type:" + answerType);
            buf.get(new byte[rdLength]);
        }

        return this;
    }

    @Override
    public String toString() {
        return "Answer [domain=]" + domain + ",answerType=" + answerType +
                ",answerClass = " + answerClass + ", ttl =" + ttl + ", rData" + rData + "]";
    }
}
