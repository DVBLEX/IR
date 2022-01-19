package ie.irishrail.server.base.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FPNValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    private int               responseCode;
    private String            responseText;
    private String            responseSource;

    public FPNValidationException() {
        super();
    }

    public FPNValidationException(int responseCode, String responseText, String responseSource) {
        super();
        this.responseCode = responseCode;
        this.responseText = responseText;
        this.responseSource = responseSource;
    }
}
