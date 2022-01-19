package ie.irishrail.server.base.exception;

import lombok.Data;

@Data
public class FPNNotFoundException extends RuntimeException {

    private int    code;
    private String message;
    private String source;

    public FPNNotFoundException(int code, String message, String source) {
        this.code = code;
        this.message = message;
        this.source = source;
    }
}
