package ca.mcgill.ecse489.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.PacketCompoent;
import ca.mcgill.ecse489.structures.Domain;
import ca.mcgill.ecse489.type.Class;
import ca.mcgill.ecse489.type.Type;

public class Record implements PacketCompoent<Record> {

    private Domain domain;
    private Type recordType;
    private Class recordClass;
    private long ttl;
    private RData<?> recordData;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Type getRecordType() {
        return recordType;
    }

    public void setRecordType(Type recordType) {
        this.recordType = recordType;
    }

    public Class getRecordClass() {
        return recordClass;
    }

    public void setRecordClass(Class recordClass) {
        this.recordClass = recordClass;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public RData<?> getRecordData() {
        return recordData;
    }

    public void setRecordData(RData<?> recordData) {
        this.recordData = recordData;
    }

    @Override
    public Record toBytes(ByteBuffer buf) throws IOException {
        domain.toBytes(buf);
        buf.putShort((short) recordType.getCode());
        buf.putShort((short) recordClass.getCode());
        buf.putInt((int) ttl);

        ByteBuffer recordDataBuffer = ByteBuffer.allocate(65536);
        recordData.toBytes(recordDataBuffer);
        recordDataBuffer.flip();
        buf.putShort((short) recordDataBuffer.limit());
        buf.put(recordDataBuffer);
        return this;
    }

    @Override
    public Record fromBytes(ByteBuffer buf) throws IOException {
        domain = new Domain().fromBytes(buf);
        recordType = Type.byCode(buf.getShort());
        recordClass = Class.byCode(buf.getShort());
        ttl = buf.getInt() & 0xFFFFFFFF;
        int recordDataLength = buf.getShort() & 0xFFFF;

        switch (recordType) {
            case CNAME:
                recordData = new CNameRecord();
                break;

            case MX:
                recordData = new MXRecord();
                break;

            default:
                System.err.println("Unknow recordType");
                break;
        }

        if (recordData != null) {
            recordData.setRdLength(recordDataLength);
            recordData.fromBytes(buf);
        } else {
            System.out.println("unknow type " + recordType);
            buf.get(new byte[recordDataLength]);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Record [domain=" + domain + ",recordType=" + recordType + "recordClass=" + ",ttl=" + ttl
                + ",recordData=" + recordData + "]";
    }

}
