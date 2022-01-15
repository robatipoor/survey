package com.snap.survey.service.impl;

import com.snap.survey.mapper.UserPrincipalMapper;
import com.snap.survey.repository.UserRepository;
import com.snap.survey.util.AppExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsServiceImpl")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final AppExceptionUtil appExceptionUtil;
  private final UserPrincipalMapper userPrincipalMapper;

  @Autowired
  public UserDetailsServiceImpl(
      UserRepository userRepository,
      AppExceptionUtil appExceptionUtil,
      UserPrincipalMapper userPrincipalMapper) {
    this.userRepository = userRepository;
    this.appExceptionUtil = appExceptionUtil;
    this.userPrincipalMapper = userPrincipalMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    return userRepository
        .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .map(userPrincipalMapper::toUserPrincipal)
        .orElseThrow(
            () ->
                appExceptionUtil.getAppException(
                    "user.not.exist.error.message", "user.not.exist.error.code"));
  }
}
