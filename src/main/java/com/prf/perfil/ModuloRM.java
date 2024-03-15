package com.prf.perfil;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ModuloRM implements RowMapper<Modulo> {
	@Override
	public Modulo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Modulo modulo = new Modulo();
		modulo.setCodigo(rs.getString("codigo"));
		modulo.setId_modulo(rs.getInt("id_modulo"));
		modulo.setDescripcion(rs.getString("descripcion"));
		return modulo;
	}
}
