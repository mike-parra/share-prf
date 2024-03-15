package com.prf.complementos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;


public class ComplementoRM implements RowMapper<Complemento>{

	@Override
	public Complemento mapRow(ResultSet rs, int rowNum) throws SQLException {
		Complemento compl= new Complemento();
		
		compl.setId_complemento(rs.getInt("id_complemento"));
		compl.setUuid(rs.getString("uuid"));
		compl.setFecha_emision(rs.getString("fecha_emision"));
		compl.setFecha_timbrado(rs.getString("fecha_timbrado"));
		compl.setMonto(rs.getDouble("monto"));
		compl.setFecha_pago(rs.getString("fecha_pago"));
		compl.setMoneda(rs.getString("moneda"));
		//compl.setMetodo_pago(rs.getString("metodo_pago"));
		compl.setRazonsocial_emisor(rs.getString("razonsocial_emisor"));
		compl.setRfc_emisor(rs.getString("rfc_emisor"));
		compl.setRazonsocial_receptor(rs.getString("razonsocial_receptor"));
		compl.setRfc_receptor(rs.getString("rfc_receptor"));
		compl.setForma_pago(rs.getString("forma_pago"));
		compl.setTipo_cambio(rs.getDouble("tipo_cambio"));
		compl.setTasa_iva(rs.getDouble("tasa_iva"));
		compl.setEstatus(rs.getString("estatus"));
		
		// Se puede traer info luego de los DR.
		return compl;
	}

}
