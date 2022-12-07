package com.mongo.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.app.models.Usuario;
import com.mongo.app.security.IJWTService;
import com.mongo.app.security.JWTServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RestController
public class LoginController {
	
	@Autowired
	private IJWTService jwtService;
	
	@Autowired 
	private DaoAuthenticationProvider daoAuthenticationProvider;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario){
		Authentication authenticacion=daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(usuario.getUsername(), usuario.getPassword()));
		String token;
		Map<String, Object> map=new HashMap<>();
		try {
			token = jwtService.create(authenticacion);
			System.out.println(token);
			
			@SuppressWarnings("deprecation")
			Claims claims=Jwts.parser()
					.setSigningKey(JWTServiceImpl.SECRET.getBytes()) //obtenemos la llave con la key firmada
					.parseClaimsJws(token)
					.getBody();
			String username=claims.getSubject();
			Object roles=claims.get("authorities");
			map.put("token", token);
			map.put("usuario", username);
			map.put("roles", roles);
			return new ResponseEntity<Map<String, Object>> (map,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>> (map,HttpStatus.BAD_REQUEST);
		}
		
	}
}
