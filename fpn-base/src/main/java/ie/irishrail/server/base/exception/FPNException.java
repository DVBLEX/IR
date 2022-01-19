package ie.irishrail.server.base.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FPNException extends Exception {

    private static final long serialVersionUID = 1L;

    private int               responseCode;
    private String            responseText;
    private String            responseSource;

    public FPNException() {
        super();
    }

    public FPNException(int responseCode, String responseText, String responseSource) {
        super(responseText);
        this.responseCode = responseCode;
        this.responseText = responseText;
        this.responseSource = responseSource;
    }
}
