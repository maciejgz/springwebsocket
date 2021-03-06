package pl.mg.springwebsocket.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "oauthAuthenticationEntryPoint")
public class OauthAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ACCESS_DENIED = "Access Denied";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ACCESS_DENIED);
    }

}
