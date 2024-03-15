package com.prf.orden_compra;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrdenCompraRM implements RowMapper<OrdenCompra> {

	@Override
	public OrdenCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrdenCompra oc = new OrdenCompra();
		
		oc.setId_oc(rs.getInt("id_oc"));
		oc.setOrden_compra(rs.getInt("orden_compra"));
		oc.setDescripcion(rs.getString("descripcion"));
		oc.setImporte(rs.getInt("importe"));
		oc.setMoneda(rs.getString("moneda"));
		oc.setFecha_creacion(rs.getString("fecha_creacion"));
		oc.setMaterial_doc(rs.getString("material_doc"));
		oc.setDocumento_contable(rs.getString("documento_contable"));
		
		return oc;
	}

}
