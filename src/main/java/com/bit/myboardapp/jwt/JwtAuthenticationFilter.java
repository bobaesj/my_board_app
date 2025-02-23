package com.bit.myboardapp.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    private String parseBearerToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);
            log.info("Parse JWT Token: {}", token);

            if(token != null && !token.equalsIgnoreCase("null")) {
                String email = jwtProvider.validatedAndGetSubject(token);
                log.info("email from JWT Token: {}", email);

                request.setAttribute("userEmail", email);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                log.info("UserDetails: {}", userDetails);

                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
                log.info("Authentication token set in SecurityContext for user: {}", email);
            }
        } catch (ExpiredJwtException e){
            log.error("Expired JWT Token: {}", e.getMessage());
        } catch (UsernameNotFoundException e) {
            log.error("Username not found: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        }

        chain.doFilter(request, response);
    }
}
