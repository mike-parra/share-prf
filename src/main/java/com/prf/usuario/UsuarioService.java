package com.prf.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;


public interface UsuarioService {
	// mismos metodos del DAO, en el cual los nombramos, aqui los hacemos como un servicio

	Optional<Usuario> findByUsername(String username);

	int crearUsuario(Usuario usuario) throws DuplicateKeyException;

	Integer crearUsuarioProveedor(Usuario usuario) throws DuplicateKeyException;

	List<UsuarioPerfil> listarUsuarioByPerfil(String perfil);
	
	List<Usuario> listarUsuario();
	
	Usuario obtenerUsuarioById (int id);
	
	void editarUsuario(Usuario usuario);

	void desactivarUsuarioById(int id);

	String generatePassayPassword(Integer id);
	
	String generateUsername(String nombre, String apellido, String razon_social);

}
