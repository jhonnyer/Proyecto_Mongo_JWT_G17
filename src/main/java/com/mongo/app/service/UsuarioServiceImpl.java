package com.mongo.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongo.app.interfaceService.IUsuarioService;
import com.mongo.app.models.Usuario;
import com.mongo.app.repository.IUsuarioRepository;
//import com.mongo.app.util.SequenceUsuario;
@Service
public class UsuarioServiceImpl implements IUsuarioService{
//	@Autowired
//	private SequenceUsuario sequenceUser;
	
	@Autowired
	private IUsuarioRepository repository;
	
	@Override
	public ResponseEntity<Usuario> save(Usuario usuario) {
		try {
//			usuario.setId(sequenceUser.generatedSequence((Usuario.SEQUENCE_NAME)));
			Usuario userSave=repository.save(usuario);
			return new ResponseEntity<Usuario> (userSave, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Usuario> (new Usuario(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Usuario> findAllUsuario() {
		try {
			return repository.findAll();
		}catch(Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public ResponseEntity<Usuario> update(Usuario usuario) {
		try {
//			usuario.setId(sequenceUser.generatedSequence((Usuario.SEQUENCE_NAME)));
			Usuario userSave=repository.save(usuario);
			return new ResponseEntity<Usuario> (userSave, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Usuario> (new Usuario(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> delete(String id) {
		try {
			Optional<Usuario> user=repository.findById(id);
			repository.deleteById(user.get().getId());
			return new ResponseEntity<String> ("El usuario "+user.get().getUsername()+ " fue eliminado con éxito", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String> (e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Usuario findById(String id) {
		return repository.findById(id).orElse(new Usuario());
	}

}
