package ie.irishrail.server.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static String queryString = "";
    private JdbcTemplate  jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT");
        builder.append("     accounts.id,");
        builder.append("     accounts.code,");
        builder.append("     accounts.first_name,");
        builder.append("     accounts.last_name,");
        builder.append("     accounts.username,");
        builder.append("     accounts.password,");
        builder.append("     accounts.is_active,");
        builder.append("     accounts.is_locked,");
        builder.append("     accounts.login_failure_count,");
        builder.append("     accounts.role_id,");
        builder.append("     accounts.date_last_password,");
        builder.append("     accounts.is_credentials_expired,");
        builder.append("     accounts.date_locked,");
        builder.append("     system_parameters.login_lock_period,");
        builder.append("     system_parameters.login_password_valid_period,");

        builder.append(" FROM accounts");
        builder.append(" INNER JOIN system_parameters ON 1=1");

        builder.append(" WHERE");
        builder.append("    accounts.username = ? ");

        builder.append(" LIMIT 1");

        queryString = builder.toString();

        MyUserDetails myUserDetails = jdbcTemplate.query(queryString, rs -> {

            MyUserDetails myUserDetails1 = null;

            if (rs.next()) {
                myUserDetails1 = new MyUserDetails(rs.getLong("id"), rs.getString("code"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("username"), rs.getString("password"), rs.getBoolean("is_active"), rs.getBoolean("is_locked"), rs.getInt("login_failure_count"),
                    rs.getInt("role_id"), rs.getTimestamp("date_last_password"), rs.getBoolean("is_credentials_expired"), rs.getTimestamp("date_locked"), rs.getInt("login_lock_period"), rs.getInt("login_password_valid_period"));
            }
            return myUserDetails1;
        }, username);

        if (myUserDetails == null)
            throw new UsernameNotFoundException("User not found.");

        return myUserDetails;
    }
}
