package com.bleehouse.Cerberus.model.factory;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.bleehouse.Cerberus.domain.entity.User;
import com.bleehouse.Cerberus.model.security.CerberusUser;

public class CerberusUserFactory {

  public static CerberusUser create(User user) {
    Collection<? extends GrantedAuthority> authorities;
    try {
      authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
    } catch (Exception e) {
      authorities = null;
    }
    return new CerberusUser(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getEmail(),
      user.getLastPasswordReset(),
      authorities
    );
  }

}
