package ca.mcgill.ecse489.type;

/**
 * Commonly used type for Question and Answer
 * @author Yang Zhou(260401719)
 *
 */
public enum Type {
    
    A(1), NS(2), CNAME(5), MX(15),
    
    UNKNOWN(-1);
    
    private final int code;

    private Type(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    public static Type byCode(int code){
        for (Type t : values()) {
            if (t.code == code) {
                return t;
            }
        }
        System.out.println("No type with code: " + code + "exists");
        return UNKNOWN;
    }
}
