package com.prf.proveedor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProveedorRM implements RowMapper<Proveedor> {

	@Override
	public Proveedor mapRow(ResultSet rs, int rowNum) throws SQLException {

		Proveedor proveedor = Proveedor.builder()
					.id_proveedor(rs.getInt("id_proveedor"))
					.id_usuario(rs.getInt("id_usuario"))
					.tratamiento(rs.getString("tratamiento"))
					.nombre_razon_social(rs.getString("nombre_razon_social"))
					.rfc(rs.getString("rfc"))
					.ramo_industrial_clave(rs.getString("ramo_industrial_clave"))
					.ramo_industrial_denominacion(rs.getString("ramo_industrial_denominacion"))
					.calle(rs.getString("calle"))
					.num_exterior(rs.getInt("num_exterior"))
					.num_interior(rs.getInt("num_interior"))
					.colonia(rs.getString("colonia"))
					.codigo_postal(rs.getInt("codigo_postal"))
					.ciudad_poblacion(rs.getString("ciudad_poblacion"))
					.estado_region_clave(rs.getString("estado_region_clave"))
					.estado_region_denominacion(rs.getString("estado_region_denominacion"))
					.pais_clave(rs.getString("pais_clave"))
					.pais_denominacion(rs.getString("pais_denominacion"))
					.nombre_contacto(rs.getString("nombre_contacto"))
					.apellido_contacto(rs.getString("apellido_contacto"))
					.email_contacto(rs.getString("email_contacto"))
					.telefono_contacto(rs.getInt("telefono_contacto"))
					.estatus(rs.getString("estatus"))
					.fecha_creacion(rs.getString("fecha_creacion"))
					.fecha_modificacion(rs.getString("fecha_modificacion"))
					.build();
		
		return proveedor;
	}

}
