package com.prf.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {

	Optional<Usuario> findByUsername(String username);

	int crearUsuario(Usuario usuario);
	
	List<Usuario> listarUsuario();
	
	List<UsuarioPerfil> listarUsuarioByPerfil(String perfil);

	Usuario obtenerUsuarioById (int id);
	
	void editarUsuario(Usuario usuario);

	void desactivarUsuario(int id);
}
