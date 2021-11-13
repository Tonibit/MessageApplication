package com.verbitskiy.springbootmessage.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class AuthenticationJwtFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private static final int JWT_TOKEN_ACTION_TIME = 30 * 60 * 1000;

    private final JwtKey jwtSecret;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UsernameAndPasswordRequestForm authenticationRequest;
        String username;
        String password;
        //Проверят что в теле запроса, если послали в формате JSON, то парсит его, если в формате
        //x-www-form-urlencoded, то берет параметры по пользователю и паролю
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            try {
                authenticationRequest = new ObjectMapper()
                        .readValue(request.getInputStream(), UsernameAndPasswordRequestForm.class);
                username = authenticationRequest.getUsername();
                password = authenticationRequest.getPassword();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(userToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getJwtSecret().getBytes());
        String jwt = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_ACTION_TIME))
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_jwt", jwt);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
