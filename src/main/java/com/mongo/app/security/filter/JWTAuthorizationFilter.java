package com.mongo.app.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.mongo.app.security.IJWTService;
import com.mongo.app.security.JWTServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	@Autowired
	private IJWTService jwtService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, IJWTService jwtService) {
		super(authenticationManager);
		this.jwtService=jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header=request.getHeader(JWTServiceImpl.HEADER_STRING);
		
		if(!requiresAuthentication(header)) {
			chain.doFilter(request,response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication=null;

		if(jwtService.validate(header)) {
			authentication=new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header)); //null corresponde al campo de credenciales que no se esta utilizando
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request,response);
	}

	protected boolean requiresAuthentication(String header) {
		if(header==null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}
}
