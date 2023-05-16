package com.chunyang.demobackend.shiro;

import com.chunyang.demobackend.entity.UserEntity;
import com.chunyang.demobackend.service.UserService;
import com.chunyang.demobackend.shiro.jwt.JwtToken;
import com.chunyang.demobackend.util.TokenUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.chunyang.demobackend.common.AuthConstant.AUTH_USERNAME_CANNOT_BE_NULL;
import static com.chunyang.demobackend.common.AuthConstant.AUTH_USER_NOT_EXIST;


public class JwtRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizingRealm.class);

    @Autowired
    private UserService userService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO
        return new SimpleAuthorizationInfo();
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String token = jwtToken.getToken();

        String username = TokenUtil.getUsernameFromToken(token);
        if (username == null) {
            throw new UnknownAccountException(AUTH_USERNAME_CANNOT_BE_NULL);
        }

        UserEntity user = userService.queryUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException(AUTH_USER_NOT_EXIST);
        }

        try {
            return new SimpleAuthenticationInfo(username, token, getName());
        } catch (Exception e) {
            LOGGER.error("the user with username【{}】authenticate failed，error is：{}", username, e.getStackTrace());
            throw new AuthenticationException(e);
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
