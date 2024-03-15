package com.prf.factura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.prf.complementos.Complemento;
import com.prf.complementos.ComplementoRM;
import com.prf.proveedor.ProveedorController;

@Repository
public class FacturaJDBC implements FacturaDAO {
	
	final String selectQuery = "SELECT * FROM factura f ";
	
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public FacturaJDBC (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = npJdbcTemplate;
	}
	

	@Override
	public Integer crearFactura(Factura factura) {
		SimpleJdbcInsert simplejdbcinsert = new SimpleJdbcInsert(this.jdbcTemplate);
		
		List<String> columns = new ArrayList<>();

		 //  columns.add("id_complemento;"); Servicio de mis complementos, a traves del rfc se hace la relación. (primer rfc, Segundo parametro para busqueda)
		// Traer complemento por id 
	        columns.add("uuid");
	        columns.add("folio");
	        columns.add("moneda");
	        columns.add("serie");
	        columns.add("monto");
	        columns.add("fecha_expedicion");
	        columns.add("fecha_timbrado");
	        columns.add("razonsocial_emisor");
	        columns.add("rfc_emisor");
	        columns.add("razonsocial_receptor");
	        columns.add("rfc_receptor"); 
	        columns.add("clave_regimenfiscal");
	        columns.add("domicilio_fiscal");
	        columns.add("metodo_pago");
	        columns.add("forma_pago");
	        
	  
        Map<String, Object> parametros = new HashMap<>();

       
		
        //parametros.put("id_complemento", "");
        parametros.put("uuid", factura.getUuid());
        parametros.put("folio", factura.getFolio());
        parametros.put("moneda", factura.getMoneda());
        parametros.put("serie", factura.getSerie());
        parametros.put("monto", factura.getMonto());
        parametros.put("fecha_expedicion", factura.getFecha_expedicion());
        parametros.put("fecha_timbrado", factura.getFecha_timbrado());
        parametros.put("razonsocial_emisor", factura.getRazonsocial_emisor());
        parametros.put("rfc_emisor", factura.getRfc_emisor());
        parametros.put("razonsocial_receptor", factura.getRazonsocial_receptor());
        parametros.put("rfc_receptor", factura.getRfc_receptor());
        parametros.put("clave_regimenfiscal", factura.getClave_regimenfiscal());
        parametros.put("domicilio_fiscal", factura.getDomicilio_fiscal());
        parametros.put("metodo_pago", factura.getMetodo_pago());
        parametros.put("forma_pago", factura.getForma_pago());
        
       
        simplejdbcinsert.setTableName("factura");
        simplejdbcinsert.setColumnNames(columns);
        simplejdbcinsert.setGeneratedKeyName("id_factura");
        
        log.info(parametros.toString());
        Number id_complemento = simplejdbcinsert.executeAndReturnKey(parametros);
		
		return id_complemento.intValue();
	}


	@Override
	public List<Factura> listarFacturas(String rfc) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rfc", rfc);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE rfc_emisor = :rfc");
	
		return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new FacturaRM());
	}


	@Override
	public Factura obtenerFacturaById(Integer id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE id_factura = :id");
		
		List<Factura> factura = namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new FacturaRM());
		return factura.get(0);
	}


	@Override
	public Factura obtenerFacturaByUuid(String uuid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("uuid", uuid);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE uuid = :uuid");
		
		List<Factura> factura = namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new FacturaRM());
		return factura.get(0);
		
	}


	@Override
	public List<Factura> obtenerDocumentosRelacionados(List<String> uuids) {
		//List<String> lista_uuids = Arrays.asList(uuids);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("uuids", uuids);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE f.uuid IN (:uuids) ");
		
		return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new FacturaRM());
	}

}
