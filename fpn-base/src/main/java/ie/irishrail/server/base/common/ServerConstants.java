package ie.irishrail.server.base.common;

public class ServerConstants {

    public static final String SYSTEM_ENVIRONMENT_LOCAL                   = "LOCAL";
    public static final String SYSTEM_ENVIRONMENT_DEV                     = "DEV";
    public static final String SYSTEM_ENVIRONMENT_PROD                    = "PROD";

    public static final int    DEFAULT_INT                                = -1;
    public static final long   DEFAULT_LONG                               = -1l;
    public static final String DEFAULT_STRING                             = "";
    public static final int    DEFAULT_VALIDATION_LENGTH_16               = 16;
    public static final int    DEFAULT_VALIDATION_LENGTH_64               = 64;

    public static final long   CUSTOM_EMAIL_CONFIG_ID                     = 1l;

    public static final String EXPORT_yyyyMMddHHmmss                      = "yyyyMMddHHmmss";
    public static final String dateFormatddMMyyyy                         = "dd/MM/yyyy";
    public static final String dateFormatyyyyMMddHHmmss                   = "yyyyMMddHHmmss";
    public static final String dateFormatMMddyyyy                         = "MM/dd/yyyy";
    public static final String dateFormatddMMyyyyHHmm                     = "dd/MM/yyyy HH:mm";

    public static final long   DAY_MILLIS                                 = 1000l * 60l * 60l * 24l;
    public static final long   NINE_MINUTES_MILLIS                        = 1000l * 60l * 9l;

    public static final long   SYSTEM_TIMER_TASK_SMS_ID                   = 101l;
    public static final long   SYSTEM_TIMER_TASK_EMAIL_ID                 = 102l;
    public static final long   SYSTEM_TIMER_TASK_DAILY_ID                 = 103l;
    public static final long   SYSTEM_TIMER_TASK_SYSTEM_STATUS_CHECK_ID   = 104l;

    public static final String REGEX_MESSAGE_FORMAT                       = "\\$\\{(?:\\s|\\&nbsp\\;)*([\\w\\_\\-]+)(?:\\s|\\&nbsp\\;)*\\}";
    public static final String REGEXP_EMAIL                               = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}])|(([\\w\\-]+\\.)+[a-zA-Z]{2,}))$";
    public static final String REGEX_PASSWORD                             = "(?=(:?.*[^A-Za-z0-9].*))(?=(:?.*[A-Z].*){1,})(?=(:?.*\\d.*){1,})(:?^[\\w\\&\\?\\!\\$\\#\\*\\+\\=\\%\\^\\@\\-\\.\\,\\_]{8,32}$)";
    public static final String REGEX_REGISTRATION_CODE                    = "[\\d]{5}";
    public static final String REGEX_UNIVERSAL_COUNTRY_CODE               = "[A-Z]{2}";
    public static final String REGEX_SHA256                               = "[A-Fa-f0-9]{64}";
    public static final String REGEXP_BASIC_PERMISSIVE_SAFE_TEXT          = "(?!^.*(\\&lt|\\&gt).*$)(?:^[^\\<\\>\\\\\\#\\`\\§\\±\\~]+$)";
    public static final String REGEXP_BASIC_ID_NUMBER                     = "[A-Za-z0-9]+";
    public static final String REGEX_BANK_ACCOUNT_IBAN                    = "(?:[A-Z]{2})(?:[A-Za-z0-9]){13,39}";
    public static final String REGEX_ACTIONS                              = "^(?:(?:\\d)+(?:\\s)*(?:\\,){0,1}(?:\\s)*)+$";

    public static final String SYSTEM_TOKEN_PREFIX                        = "fpn";
    public static final String SYSTEM_TOKEN_SUFFIX1                       = "";
    public static final String SYSTEM_TOKEN_SUFFIX2                       = "";

    public static final String SYSTEM_FILE_STORE_ALIAS_SALT               = "";
    public static final String SYSTEM_FILE_STORE_PASSWORD_PROTECTION_SALT = "";

    public static final int    KEYSTORE_NUMBER_OF_KEYS_PERSONAL_DATA      = 10000;
    public static final int    KEYSTORE_NUMBER_OF_KEYS_BUSINESS_DATA      = 10000;
    public static final int    KEYSTORE_ID_PERSONAL_DATA                  = 1;
    public static final int    KEYSTORE_ID_BUSINESS_DATA                  = 2;

    public static final int    IMAGE_COMPRESSION_LOWER_BOUND              = 1048576;

    public static final int    CHANNEL_SYSTEM                             = 1024;

    public static final long   SCHEDULER_ID                               = -100l;

    public static final int    SIZE_VERIFICATION_CODE                     = 5;

    public static final int    PROCESS_NOTPROCESSED                       = 0;
    public static final int    PROCESS_PROGRESS                           = 1;
    public static final int    PROCESS_PROCESSED                          = 2;

    public static final long   EMAIL_VERIFICATION_CODE_TEMPLATE_TYPE      = 1;
    public static final long   EMAIL_PASSWORD_FORGOT_TEMPLATE_ID          = 2;

    public static final long   SMS_VERIFICATION_CODE_TEMPLATE_TYPE        = 1;

    public static final String SPRING_SECURITY_ROLE_PREFIX                = "ROLE_";

    public static final int    CAPTCHA_MAX_ATTEMPT                        = 4;

    public static final int    SYSTEM_CHECK_TYPE_QUERY                    = 1;
    public static final int    SYSTEM_CHECK_TYPE_QUERY_REPLICATION        = 2;
    public static final int    SYSTEM_CHECK_TYPE_HTTP_GET                 = 3;

    public static final int    COMP_OP_EQUAL_TO                           = 0;
    public static final int    COMP_OP_LESS_THAN                          = 1;
    public static final int    COMP_OP_GREATER_THAN                       = 2;
    public static final int    COMP_OP_LESS_THAN_EQUAL_TO                 = 11;
    public static final int    COMP_OP_GREATER_THAN_EQUAL_TO              = 12;
    public static final int    COMP_OP_NOT_EQUAL_TO                       = 100;
}
