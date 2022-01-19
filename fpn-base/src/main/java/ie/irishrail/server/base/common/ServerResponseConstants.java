package ie.irishrail.server.base.common;

public class ServerResponseConstants {

    // [0] Generic Success Code
    public static final int    SUCCESS_CODE                               = 0;
    public static final String SUCCESS_TEXT                               = "Success.";

    // [1] Generic Failure Code
    public static final int    FAILURE_CODE                               = 1;
    public static final String FAILURE_TEXT                               = "Failure.";

    // [1201 - 1299] API Failure - Internal
    public static final int    API_AUTHENTICATION_FAILURE_CODE            = 1201;
    public static final String API_AUTHENTICATION_FAILURE_TEXT            = "Authentication Failure.";

    // [1000] API Validation - Invalid Request Format
    public static final int    INVALID_REQUEST_FORMAT_CODE                = 1000;
    public static final String INVALID_REQUEST_FORMAT_TEXT                = "Invalid Request Format.";

    // [1001 - 1040] API Validation - Missing Fields
    // example
    //    public static final int    MISSING_BANK_TRANSFER_REF_CODE                  = 1006;
    //    public static final String MISSING_BANK_TRANSFER_REF_TEXT                  = "Missing Bank Transfer Reference.";

    // [1041 - 1100] API Validation - Invalid Data Type / Range
    public static final int    INVALID_EMAIL_CODE                         = 1041;
    public static final String INVALID_EMAIL_TEXT                         = "Invalid Email.";

    public static final int    INVALID_MSISDN_CODE                        = 1042;
    public static final String INVALID_MSISDN_TEXT                        = "Invalid Mobile Number.";

    public static final int    INVALID_AGE_CODE                           = 1043;
    public static final String INVALID_AGE_TEXT                           = "Invalid age, must be at least 18 years";

    public static final int    INVALID_TOO_WEAK_PASSWORD_CODE             = 1100;
    public static final String INVALID_TOO_WEAK_PASSWORD_TEXT             = "The given new password is too weak. Use at least 1 capital letter, 1 number and 1 special character. The password length has to be between 8 and 32 characters.";

    public static final int    MISMATCH_PASSWORD_CODE                     = 1101;
    public static final String MISMATCH_PASSWORD_TEXT                     = "Confirm Password does not match Password.";

    public static final int    INVALID_CURRENT_PASSWORD_CODE              = 1102;
    public static final String INVALID_CURRENT_PASSWORD_TEXT              = "The Current Password does not match";

    public static final int    LIMIT_EXCEEDED_VERIFICATION_CODE_SENT_CODE = 1103;
    public static final String LIMIT_EXCEEDED_VERIFICATION_CODE_SENT_TEXT = "You have exceeded the number of attempts allowed.";

    public static final int    LIMIT_EXCEEDED_EMAIL_FORGOT_PASSWORD_CODE  = 1104;
    public static final String LIMIT_EXCEEDED_EMAIL_FORGOT_PASSWORD_TEXT  = "You have exceeded the number of attempts allowed.";

    public static final int    INVALID_VERIFICATION_CODE_CODE             = 1105;
    public static final String INVALID_VERIFICATION_CODE_TEXT             = "Invalid Verification Code.";

    public static final int    LIMIT_VERIFICATION_EXCEEDED_CODE           = 1106;
    public static final String LIMIT_VERIFICATION_EXCEEDED_TEXT           = "You have exceeded the number of attempts allowed.";

    public static final int    EXPIRED_VERIFICATION_CODE_CODE             = 1107;
    public static final String EXPIRED_VERIFICATION_CODE_TEXT             = "The Verification Code is expired.";

    public static final int    VERIFICATION_EXPIRED_CODE                  = 1108;
    public static final String VERIFICATION_EXPIRED_TEXT                  = "The time for completing the registration is up. Please start the registration process again.";

    public static final int    INVALID_OLD_PASSWORD_CODE                  = 1109;
    public static final String INVALID_OLD_PASSWORD_TEXT                  = "Invalid old password.";

    public static final int    INVALID_NEW_PASSWORD_CODE                  = 1110;
    public static final String INVALID_NEW_PASSWORD_TEXT                  = "Invalid New Password. New Password should not be the same as Current Password.";

    // [1186 - 1199] Miscellaneous Validation errors
    public static final int    FILE_NOT_FOUND_CODE                        = 1197;
    public static final String FILE_NOT_FOUND_TEXT                        = "File not found";

    public static final int    MISSING_FILE_ON_FILEUPLOAD_CODE            = 1198;
    public static final String MISSING_FILE_ON_FILEUPLOAD_TEXT            = "Missing File.";

    public static final int    URL_EXPIRED_CODE                           = 1199;
    public static final String URL_EXPIRED_TEXT                           = "The URL is expired.";

    // [1200] API Failure - Internal Generic
    public static final int    API_FAILURE_CODE                           = 1200;
    public static final String API_FAILURE_TEXT                           = "Failure.";

    // [1301-1399] API Validation - Limit Exceeded (Security)
    public static final int    LIMIT_EXCEEDED_SMARTPHONE_UPLOAD_LINK_CODE = 1306;
    public static final String LIMIT_EXCEEDED_SMARTPHONE_UPLOAD_LINK_TEXT = "You have exceeded the number of attempts allowed.";

    // [1400 - 1499] API Failure - External
    public static final int    EXTERNAL_API_SOCKET_TIMEOUT_CODE           = 1400;
    public static final String EXTERNAL_API_SOCKET_TIMEOUT_TEXT           = "Connection Timeout.";

    public static final int    EXTERNAL_API_CONNECTION_FAILURE_CODE       = 1401;
    public static final String EXTERNAL_API_CONNECTION_FAILURE_TEXT       = "Connection Failure.";

    public static final int    SUCCESS_TEST_CODE                          = 3000;

}
