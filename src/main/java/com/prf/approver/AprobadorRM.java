package com.prf.approver;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AprobadorRM implements RowMapper<UsuarioAprobador> {

	@Override
	public UsuarioAprobador mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UsuarioAprobador appr = new UsuarioAprobador();
		appr.setIdusuario(rs.getInt("idusuario"));
		appr.setUsuario(rs.getString("usuario"));
		appr.setNivel_auth(rs.getString("nivel_auth"));
		appr.setIdNivel_auth(rs.getInt("idnivel_aprobacion"));
		return appr;
	}

}
