package ca.mcgill.ecse489.type;

public enum Class {
    IN(1);
    
    private final int code;
    
    private Class(int code){
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    
    public static Class byCode(int code){
        for (Class c : values()) {
            if (c.code == code) {
                return c;
            }
        }
        throw new IllegalArgumentException("No class with code " + code + " exists .");
    }
}
