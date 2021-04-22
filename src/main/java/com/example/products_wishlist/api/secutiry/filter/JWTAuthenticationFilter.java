package com.example.products_wishlist.api.secutiry.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.example.products_wishlist.api.secutiry.service.TokenAuthenticationService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {		
		SecurityContextHolder.getContext().setAuthentication(TokenAuthenticationService.getAuthentication((HttpServletRequest) request));
		filterChain.doFilter(request, response);
	}
}