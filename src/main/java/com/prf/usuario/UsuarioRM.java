package com.prf.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class UsuarioRM implements RowMapper<Usuario> {
	
	

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Usuario usr = new Usuario();
				
		usr.setId_usuario(rs.getInt("id_usuario"));
		usr.setUsuario(rs.getString("usuario"));
		usr.setNombre(rs.getString("nombre"));
		usr.setEmail(rs.getString("email"));
		usr.setPassword(rs.getString("password"));
		usr.setImg_perfil(rs.getString("img_perfil"));
		//usr.setS3_directory(rs.getString("s3_directory"));
		usr.setReset_password(rs.getBoolean("reset_password"));
		usr.setEstatus(rs.getString("estatus"));
		

		
		return usr;
	}

}
