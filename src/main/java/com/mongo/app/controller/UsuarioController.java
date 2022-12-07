package com.mongo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.app.interfaceService.IUsuarioService;
import com.mongo.app.models.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping(value="/save")
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
		return usuarioService.save(usuario);
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/getAll")
	public List<Usuario> findAllUsuario(){
		return usuarioService.findAllUsuario();
	}
	
	@PutMapping("/update")
	public ResponseEntity<Usuario> update(@RequestBody Usuario usuario){
		return usuarioService.update(usuario);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id){
		return usuarioService.delete(id);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/findOne/{id}")
	public Usuario findById(@PathVariable("id") String id) {
		return usuarioService.findById(id);
	}
}
