package com.prf.usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.prf.perfil.Perfil;
import com.prf.perfil.PerfilService;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	private UsuarioDAO usuarioDAO;

	private PerfilService perfilService;

	@Autowired
	public UsuarioServiceImpl(UsuarioDAO usuarioDAO, PerfilService perfilservice) {
		this.usuarioDAO = usuarioDAO;
		this.perfilService = perfilservice;
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {

		return usuarioDAO.findByUsername(username);
	}

	@Override
	public int crearUsuario(Usuario usuario) throws DuplicateKeyException {
		usuario.setPassword(generatePassayPassword(10));
		log.info("Contraseña : {} Usuario: {}", usuario.getPassword(), usuario.getEmail());

		int idusuario = usuarioDAO.crearUsuario(usuario);
		perfilService.insertarPerfilesUsuario(idusuario, usuario.getPerfiles());
		return idusuario;
	}

	@Override
	public Integer crearUsuarioProveedor(Usuario usuario) {

		log.info("Contraseña : {} Usuario: {}", usuario.getPassword(), usuario.getEmail());
		int idusuario = usuarioDAO.crearUsuario(usuario);
		perfilService.insertarPerfilesUsuario(idusuario, usuario.getPerfiles());
		return idusuario;
		
	}

	@Override
	public List<Usuario> listarUsuario() {
		return usuarioDAO.listarUsuario();
	}

	@Override
	public Usuario obtenerUsuarioById(int id) {

		Usuario usr = usuarioDAO.obtenerUsuarioById(id);
		List<Perfil> perfiles = perfilService.getPerfiles(id);
		usr.setPerfiles(perfiles);

		return usr;
	}

	@Override
	public void editarUsuario(Usuario usuario) {
		usuarioDAO.editarUsuario(usuario);
		perfilService.resetPerfilesUsuario(usuario.getId_usuario());
		perfilService.insertarPerfilesUsuario(usuario.getId_usuario(), usuario.getPerfiles());

	}

	@Override
	public List<UsuarioPerfil> listarUsuarioByPerfil(String perfil) {

		List<UsuarioPerfil> usuarios = usuarioDAO.listarUsuarioByPerfil(perfil);

		return usuarios;
	}

	@Override
	public void desactivarUsuarioById(int id) {
		usuarioDAO.desactivarUsuario(id);
	}

	@Override
	public String generatePassayPassword(Integer length) {

		PasswordGenerator gen = new PasswordGenerator();
		CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
		CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
		lowerCaseRule.setNumberOfCharacters(2);

		CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
		CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
		upperCaseRule.setNumberOfCharacters(2);

		CharacterData digitChars = EnglishCharacterData.Digit;
		CharacterRule digitRule = new CharacterRule(digitChars);
		digitRule.setNumberOfCharacters(2);

		CharacterData specialChars = new CharacterData() {
			public String getErrorCode() {
				return "Error";
			}

			public String getCharacters() {
				return "!@#$%^&*()_+";
			}
		};

		CharacterRule splCharRule = new CharacterRule(specialChars);
		splCharRule.setNumberOfCharacters(2);

		String password = gen.generatePassword(length, splCharRule, lowerCaseRule, upperCaseRule, digitRule);

		return password;
	}

	@Override
	public String generateUsername(String nombre, String apellido, String razon_social) {
		
		String username = "";
		
		username+= nombre.split("")[0];
		username+= apellido;
		
		if (razon_social == null) 
			return username.toLowerCase();

		username+= "_";
		username+= Arrays.stream(razon_social.split(" ")).map(element -> String.valueOf(element.charAt(0)) ).collect(Collectors.joining(""));
		
		return username.toLowerCase();
	}

}
