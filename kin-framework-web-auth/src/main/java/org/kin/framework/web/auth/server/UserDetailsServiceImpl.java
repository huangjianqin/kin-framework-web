package org.kin.framework.web.auth.server;

import org.kin.framework.web.auth.doamin.AuthMessages;
import org.kin.framework.web.auth.server.domain.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 加载{@link UserDetails}的service
 *
 * @author huangjianqin
 * @date 2021/7/29
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserSupplier userSupplier;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        BaseUserDetails userDetails = userSupplier.get(clientId, username);

        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException(AuthMessages.USERNAME_PASSWORD_ERROR);
        }

        if (!userDetails.isEnabled()) {
            throw new DisabledException(AuthMessages.ACCOUNT_DISABLED);
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(AuthMessages.ACCOUNT_LOCKED);
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(AuthMessages.ACCOUNT_EXPIRED);
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(AuthMessages.CREDENTIALS_EXPIRED);
        }
        return userDetails;
    }
}
