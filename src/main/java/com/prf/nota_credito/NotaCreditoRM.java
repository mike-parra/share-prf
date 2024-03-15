package com.prf.nota_credito;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotaCreditoRM implements RowMapper<NotaCredito> {
	@Override
	public NotaCredito mapRow(ResultSet rs, int rowNum) throws SQLException {

		NotaCredito notaCredito = new NotaCredito();
		notaCredito.setId_nota_credito(rs.getInt("id_nota_credito"));
		notaCredito.setUuid_factura(rs.getString("uuid_factura"));
		notaCredito.setMoneda(rs.getString("moneda"));
		notaCredito.setTipo_cambio(rs.getString("tipo_cambio"));
		notaCredito.setMonto(rs.getDouble("monto"));
		notaCredito.setFecha_timbrado(rs.getString("fecha_timbrado"));
		notaCredito.setMetodo_pago(rs.getString("metodo_pago"));
		notaCredito.setForma_pago(rs.getString("forma_pago"));
		notaCredito.setFecha_creacion(rs.getString("fecha_creacion"));
		notaCredito.setFecha_modificacion(rs.getString("fecha_modificacion"));
		notaCredito.setEstatus(rs.getString("estatus"));
		notaCredito.setUuid_nota_credito(rs.getString("uuid_nota_credito"));

		return notaCredito;

	}
}
