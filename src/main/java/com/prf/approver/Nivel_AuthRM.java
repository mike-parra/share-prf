package com.prf.approver;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Nivel_AuthRM implements RowMapper<Nivel_Auth>{

	@Override
	public Nivel_Auth mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Nivel_Auth nv_auth = new Nivel_Auth();
		nv_auth.setIdnivelaprobacion(rs.getInt("idnivel_aprobacion"));
		nv_auth.setDescripcion(rs.getString("descripcion"));
		nv_auth.setJerarquia(rs.getInt("jerarquia"));
		
		return nv_auth;
	}

}
