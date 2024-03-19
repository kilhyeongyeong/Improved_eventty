//package com.eventty.authservice.config;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
//
//    @Override
//    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        for (String auth: customUser.authorities()) {
//            authorities.add(new SimpleGrantedAuthority(auth));
//        }
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(customUser.principal(), customUser.credentials(), authorities);
//        securityContext.setAuthentication(authentication);
//        return securityContext;
//    }
//}
