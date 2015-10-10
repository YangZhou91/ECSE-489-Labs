package ca.mcgill.ecse489.structures;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Object for DNS Question
 * @author Yang Zhou(260401719)
 *
 */
public class Question {
	private Domain Qname;
	private Qtype Qtype;
	private short Qclass = 0x0001;
	
	public Domain getQname() {
		return Qname;
	}

	public void setQname(Domain qname) {
		Qname = qname;
	}

	public Qtype getQtype() {
		return Qtype;
	}

	public void setQtype(Qtype qtype) {
		Qtype = qtype;
	}

	public short getQclass() {
		return Qclass;
	}
	public void setQclass(short qclass) {
		Qclass = qclass;
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
	
	
	public Question toBytes(ByteBuffer buf){
	    // This is domain
	    Qname.toBytes(buf);
	    // 16bits QTYPE 
	    buf.putShort((short)Qtype.getCode());
	    buf.putShort(Qclass);
	    
	    return this;
	}
	
}
