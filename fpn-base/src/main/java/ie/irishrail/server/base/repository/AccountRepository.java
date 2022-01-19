package ie.irishrail.server.base.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ie.irishrail.server.base.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByEmail(String userName);

    Account findByUsername(String username);

    Account findByCode(String code);

    @Query("SELECT COUNT(1) FROM accounts WHERE email = :email AND count_passwd_forgot_requests >= :passwordForgotEmailLimit AND is_deleted = 0")
    long countEmailForgotPasswordUnderLimit(@Param("email") String email, @Param("passwordForgotEmailLimit") int passwordForgotEmailLimit);

    @Query("SELECT COUNT(1) FROM accounts WHERE email = :email AND is_deleted = 0")
    long countEmailRegisteredAlready(@Param("email") String email);

    @Query("SELECT COUNT(1) FROM accounts WHERE msisdn = :msisdn AND is_deleted = 0")
    long countMsisdnRegisteredAlready(@Param("msisdn") String msisdn);

    @Query("SELECT email FROM accounts")
    Set<String> getRegisteredEmailSet();

    Page<Account> findAllByRoleIdAndIsDeletedIsFalse(int role, Pageable pageable);
}
