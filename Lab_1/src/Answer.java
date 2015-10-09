/**
 * @author Yan Liu (260152375)
 *
 */
public class Answer {
	private Domain Aname;
	private short Aclass;
	private int Attl;
	private short Ardlength;
	private byte[] Ardata;
	
	
	
	
	
	
	
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
}
