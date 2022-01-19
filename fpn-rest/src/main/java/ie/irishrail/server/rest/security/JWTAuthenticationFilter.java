package ie.irishrail.server.rest.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ie.irishrail.server.base.common.SecurityUtil;
import ie.irishrail.server.base.jsonentity.AccountJson;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ie.irishrail.server.base.entity.Account;
import ie.irishrail.server.base.repository.AccountRepository;
import ie.irishrail.server.base.service.SystemService;
import ie.irishrail.server.rest.exception.AuthenticateException;
import ie.irishrail.server.rest.exception.BadRequestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository     accountRepository;
    private final SystemService         systemService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = ctx.getBean(AccountRepository.class);
        this.systemService = ctx.getBean(SystemService.class);
        this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public final Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        if (!req.getMethod().equals("POST")) {
            throw new MethodNotAllowedException(req.getMethod(), Collections.singleton(HttpMethod.POST));
        }
        req.getHeaderNames();
        String contentType = req.getHeader("Content-Type");
        if (!contentType.contains("application/json")) {
            throw new UnsupportedMediaTypeStatusException("Not supported media type");
        }
        try {
            AccountJson credentials = new ObjectMapper().readValue(req.getInputStream(), AccountJson.class);
            req.setAttribute("username", credentials.getUsername());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>()));
        } catch (JsonMappingException | JsonParseException e) {
            throw new BadRequestException(e.getMessage(), e);
        } catch (IOException e) {
            throw new AuthenticateException(e);
        }
    }

    @Override
    protected final void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String username = ((MyUserDetails) auth.getPrincipal()).getUsername();
        String token = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)).signWith(
            SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        saveLoginData(auth, username);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String username = (String) request.getAttribute("username");
        StringBuilder builder = new StringBuilder();
        builder.append("LoginFailure#username=").append(username);

        try {
            Account account = accountRepository.findByUsername(username);

            if (account != null) {

                if (failed instanceof CredentialsExpiredException) {
                    account.setIsCredentialsExpired(true);

                    String token1 = SecurityUtil.generateDateBasedToken1(username, account.getDateLastPassword());
                    String token2 = SecurityUtil.generateDateBasedToken2(username, account.getDateLastPassword());

                    builder.append("[credentials expired]");

                } else if (failed instanceof DisabledException) {
                    account.setLoginFailureCount(account.getLoginFailureCount() + 1);

                    builder.append("[disabled]");

                } else if (account.getIsDeleted() && !account.getIsActive() && account.getIsLocked()) {
                    account.setLoginFailureCount(account.getLoginFailureCount() + 1);

                    builder.append("[deleted]");

                } else if (account.getIsLocked()) {
                    account.setLoginFailureCount(account.getLoginFailureCount() + 1);

                    builder.append("[locked]");

                } else if (account.getLoginFailureCount() >= systemService.getSystemParameter().getLoginLockCountFailed()) {
                    account.setIsLocked(true);
                    account.setLoginFailureCount(account.getLoginFailureCount() + 1);
                    account.setDateLocked(new Date());

                    builder.append("[lock]");

                } else {
                    account.setLoginFailureCount(account.getLoginFailureCount() + 1);

                    builder.append("[bad credentials]");
                }

                accountRepository.save(account);
            }

            log.warn(builder.toString());

        } catch (Exception e) {

            log.error("onAuthenticationFailure#username=" + username + "###Exception: " + e.getMessage());
        }
        throw new AuthenticateException(builder.toString());
    }

    private void saveLoginData(Authentication auth, String username) {
        try {
            Account account = accountRepository.findByUsername(((MyUserDetails) auth.getPrincipal()).getUsername());
            account.setLoginFailureCount(0);
            account.setDateLastLogin(new Date());
            account.setDateLastAttempt(new Date());
            account.setIsLocked(false);
            account.setIsCredentialsExpired(false);

            accountRepository.save(account);

            log.info("onAuthenticationSuccess#username=" + username);

        } catch (Exception e) {
            log.error("onAuthenticationSuccess#username=" + username + "###Exception: " + e.getMessage());
        }
    }
}
