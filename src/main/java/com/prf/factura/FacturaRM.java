package com.prf.factura;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaRM implements RowMapper<Factura> {
    @Override
    public Factura mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Factura factura= new Factura();

		factura.setIdfactura(rs.getInt("id_factura"));
		factura.setUuid(rs.getString("uuid"));
		factura.setFolio(rs.getInt("folio"));
		factura.setMoneda(rs.getString("moneda"));
		factura.setSerie(rs.getString("serie"));
		factura.setMonto(rs.getDouble("monto"));
		factura.setFecha_expedicion(rs.getString("fecha_expedicion"));
		factura.setFecha_timbrado(rs.getString("fecha_timbrado"));
		factura.setRazonsocial_emisor(rs.getString("razonsocial_emisor"));
		factura.setRfc_emisor(rs.getString("rfc_emisor"));
		factura.setRazonsocial_receptor(rs.getString("razonsocial_receptor"));
		factura.setRfc_receptor(rs.getString("rfc_receptor"));
		factura.setDomicilio_fiscal(rs.getString("domicilio_fiscal"));
		factura.setClave_regimenfiscal(rs.getString("clave_regimenfiscal"));
		factura.setMetodo_pago(rs.getString("metodo_pago"));
		factura.setForma_pago(rs.getString("forma_pago"));
		factura.setEstatus(rs.getString("estatus"));
		
	
		return factura;
    }
}
