import java.nio.ByteBuffer;

/**
 * @author Yan Liu (260152375)
 *
 */
public class Answer {
	private Domain domain;
	private Atype answerType;
	private short answerClass;
	private int ttl;
	private short rdLength;
	private byte[] rData;
	
	
	
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



    public byte[] getrData() {
        return rData;
    }



    public void setrData(byte[] rData) {
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
}
