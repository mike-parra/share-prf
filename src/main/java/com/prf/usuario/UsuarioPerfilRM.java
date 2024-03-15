package com.prf.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;


public class UsuarioPerfilRM implements RowMapper<UsuarioPerfil>{


	
	@Override
	public UsuarioPerfil mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UsuarioPerfil usr = new UsuarioPerfil();
		usr.setIdusuario(rs.getInt("idusuario"));
		usr.setUsuario(rs.getString("usuario"));
		usr.setNombre(rs.getString("nombre"));
		usr.setEmail(rs.getString("email"));
		usr.setImg_perfil(rs.getString("img_perfil"));
		usr.setEstatus(rs.getString("estatus"));
		usr.setPerfil(rs.getString("descripcion"));

		
		return usr;
	}

}
