package com.prf.perfil;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PerfilRM implements RowMapper<Perfil>{

	@Override
	public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
		Perfil perfil = new Perfil();
		perfil.setId_perfil(rs.getInt("id_perfil"));
		perfil.setDescripcion(rs.getString("descripcion"));
		perfil.setCodigo(rs.getNString("codigo"));
		perfil.setEstatus(rs.getString("estatus"));
		return perfil;
	}

}
