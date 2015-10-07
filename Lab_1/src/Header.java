/**
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Header {
	
	// Short is 16-bit signed two's complement integer
	private short id;
	private Header.QR qr;
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
	private short Z;
	private Header.RCODE rcode;
	private short qdcount;
	private short ancount;
	private short nscount;
	private short arcount;
	
	
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public Header.QR getQr() {
		return qr;
	}
	public void setQr(Header.QR qr) {
		this.qr = qr;
	}
	public Header.OPCODE getOpcode() {
		return opcode;
	}
	public void setOpcode(Header.OPCODE opcode) {
		this.opcode = opcode;
	}
	public boolean isAA() {
		return aa;
	}
	public void setAA(boolean aA) {
		aa = aA;
	}
	public boolean isTC() {
		return TC;
	}
	public void setTC(boolean tC) {
		TC = tC;
	}
	public boolean isRD() {
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
	public short getNscount() {
		return nscount;
	}
	public void setNscount(short nscount) {
		this.nscount = nscount;
	}
	public short getArcount() {
		return arcount;
	}
	public void setArcount(short arcount) {
		this.arcount = arcount;
	}
	public enum QR {
		QUERY(0),
		RESPONSE(1);
		private final int code;
		private QR(int code){
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		
	}
	public enum OPCODE {
		QUERY(0);
		private final int code;

		private OPCODE(int code){
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
		// byCode method
	}
	public enum RCODE {
		NO_ERROR(0), FORMAT_ERROR(1), SERVER_FAILURE(2), NAME_ERROR(3),
		NOT_IMPLMENTED(4), REFUSED(5);
		private final int code; 
		
		private RCODE(int code){
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}
}
