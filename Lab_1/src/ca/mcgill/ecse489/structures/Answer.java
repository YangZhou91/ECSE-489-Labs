package ca.mcgill.ecse489.structures;
import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.PacketCompoent;
import ca.mcgill.ecse489.record.RData;

/**
 * @author Yan Liu (260152375)
 *
 */
public class Answer implements PacketCompoent<Answer> {
	private Domain domain;
	private Atype answerType;
	private short answerClass;
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



    public Atype getAnswerType() {
        return answerType;
    }



    public void setAnswerType(Atype answerType) {
        this.answerType = answerType;
    }



    public short getAnswerClass() {
        return answerClass;
    }



    public void setAnswerClass(short answerClass) {
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



    // Need a generic type here
	public enum Atype {
		HOST_ADDRESS(0x0001), NAME_SERVER(0x0002), MAIL_SERVER(0x000f), CNAME(0x0005);
		
		private int code;

		  private Atype(int code) {
		    this.code = code;
		  }

		  public int getCode() {
		    return code;
		  }
	}
	
	@Override
	public Answer toBytes(ByteBuffer buf){
	    domain.toBytes(buf);
	    // type
	    buf.putShort((short)answerType.getCode());
	    buf.putShort((short)answerClass);
	    buf.putInt((int)ttl);
	    
	    // 16 bit
	    ByteBuffer recordDataBuffer = ByteBuffer.allocate(65536);
	    
        return this;
	}



    @Override
    public Answer fromBytes(ByteBuffer buf) {
        // TODO Auto-generated method stub
        return null;
    }
}
