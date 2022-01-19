package ie.irishrail.server.rest.security;

public final class SecurityConstants {

    // 24 hours
    public static final long EXPIRATION_TIME = 86_400_000;
    static final String      SECRET          = "ju18qKHNEo8RhGDJhf629hRczmZ9eQORWZ4tvaxPyZoh2JJ4xOFNF843x4mTtj4";
    static final String      TOKEN_PREFIX    = "Bearer ";
    static final String      HEADER_STRING   = "Authorization";
    static final String      FORGOT_URL      = "/password-recovery";
    static final String      LOGIN_URL       = "/login";
    static final String      LOGIN_PAGE_URL  = "/login-page";
    static final String      RESOURCES_URL   = "/resources/**";

    private SecurityConstants() {
    }
}
