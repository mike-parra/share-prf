package com.prf.security;

import java.util.List;

import com.prf.perfil.Modulo;
import com.prf.perfil.Perfil;
import com.prf.perfil.PerfilService;
import com.prf.usuario.Usuario;
import com.prf.usuario.UsuarioDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioDAO usuarioDAO;

	@Autowired
	PerfilService perfilService;

	@Override
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		 Usuario user = usuarioDAO.findByUsername(username)
			        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		 
		// List<String> authoritis= perfilService.getAutoridadesPerfil(user.getIdusuario());
		 
		 List<Modulo> modulos = perfilService.getModulos(user.getId_usuario());
		 
		 List<Perfil> perfiles = perfilService.getPerfiles(user.getId_usuario());
		 
		 
		// user.setPermisos(authoritis);
		 user.setModulos(modulos);
		 user.setPerfiles(perfiles);
			    return UserDetailsImpl.build(user);
	}

}
