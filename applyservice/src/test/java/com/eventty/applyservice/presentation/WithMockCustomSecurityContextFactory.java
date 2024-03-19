//package com.eventty.applyservice.presentation;
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
//public class WithMockCustomSecurityContextFactory implements WithSecurityContextFactory<WithMockCustom> {
//    @Override
//    public SecurityContext createSecurityContext(WithMockCustom customUser) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        for(String auth : customUser.authorities()){
//            authorities.add(new SimpleGrantedAuthority(auth));
//        }
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(customUser.principal(), customUser.credentials(), authorities);
//        context.setAuthentication(auth);
//        return context;
//    }
//}
