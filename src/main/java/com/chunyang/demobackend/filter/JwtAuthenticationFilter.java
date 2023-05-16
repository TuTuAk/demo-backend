package com.chunyang.demobackend.filter;

import com.alibaba.fastjson.JSONObject;
import com.chunyang.demobackend.common.AuthConstant;
import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.shiro.jwt.JwtToken;
import com.chunyang.demobackend.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.chunyang.demobackend.common.AuthConstant.AUTH_TOKEN_TIMEOUT;
import static com.chunyang.demobackend.common.AuthConstant.AUTH_USER_UNAUTHORIZED;


public class JwtAuthenticationFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (getSubject(request, response) != null
                && getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return isLoginRequest(request, response);

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(AuthConstant.DEFAULT_TOKEN_NAME);
        boolean tokenIsExpired = TokenUtil.isTokenExpired(token);

        if (tokenIsExpired) {
            onLoginFail(response, new AuthenticationException(AUTH_TOKEN_TIMEOUT));
            return false;
        }

        if (token != null) {

            UserDTO user = TokenUtil.getUserFromToken(token);
            JwtToken jwtToken = new JwtToken(user.getName(), token);

            try {
                super.getSubject(request, response).login(jwtToken);
                UserContext.setUser(user);
                return true;
            } catch (Exception e) {
                onLoginFail(response, new AuthenticationException(AUTH_USER_UNAUTHORIZED));
                return false;
            }
        }
        onLoginFail(response, new AuthenticationException(AUTH_USER_UNAUTHORIZED));
        return false;
    }

    private void onLoginFail(ServletResponse response, Exception e) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setCharacterEncoding("utf-8");

        try (PrintWriter out = httpResponse.getWriter()) {

            ResponseEntity<?> rsp = new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
            String data = JSONObject.toJSONString(rsp);

            out.append(data);

        } catch (IOException ie) {
            logger.error("login failed : {}", ie.getMessage());
        }
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}