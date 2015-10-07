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
	private boolean AA;
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
