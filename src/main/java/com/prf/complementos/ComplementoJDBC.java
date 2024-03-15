package com.prf.complementos;

import java.util.ArrayList;
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




@Repository
public class ComplementoJDBC  implements ComplementoDAO{
	 final String selectQuery = "SELECT *  FROM  complemento_pago cp ";
		
		private JdbcTemplate jdbcTemplate;
		
		
		private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		@Autowired
		public ComplementoJDBC (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npJdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
			this.namedParameterJdbcTemplate = npJdbcTemplate;
		}

		
		
		@Override
		public Integer crearComplemento(Complemento complemento) {
			
			SimpleJdbcInsert simplejdbcinsert = new SimpleJdbcInsert(this.jdbcTemplate);
			
			List<String> columns = new ArrayList<>();

			 //  columns.add("id_complemento;");
		        columns.add("uuid");
		        columns.add("fecha_emision");
		        columns.add("fecha_timbrado");
		        columns.add("monto");
		        columns.add("fecha_pago");
		        columns.add("moneda");
		       // columns.add("metodo_pago");
		        columns.add("razonsocial_emisor");
		        columns.add("rfc_emisor");
		        columns.add("razonsocial_receptor");
		        columns.add("rfc_receptor");
		        columns.add("forma_pago");
		       // columns.add("numero_parcialidad");
		        columns.add("tipo_cambio");
		        columns.add("tasa_iva");
		        columns.add("estatus");
		       // columns.add("uuid_documentos_relacionados");
	        Map<String, Object> parametros = new HashMap<>();
	       // Gson gson = new Gson(); 
	        
	        //parametros.put("id_complemento", "");
	        parametros.put("uuid", complemento.getUuid());
	        parametros.put("fecha_emision", complemento.getFecha_emision());
	        parametros.put("fecha_timbrado", complemento.getFecha_timbrado());
	        parametros.put("monto", complemento.getMonto());
	        parametros.put("fecha_pago", complemento.getFecha_pago());
	        parametros.put("moneda", complemento.getMoneda());
	        //parametros.put("metodo_pago", complemento.getMetodo_pago());
	        parametros.put("razonsocial_emisor", complemento.getRazonsocial_emisor());
	        parametros.put("rfc_emisor", complemento.getRfc_emisor());
	        parametros.put("razonsocial_receptor", complemento.getRazonsocial_receptor());
	        parametros.put("rfc_receptor", complemento.getRfc_receptor());
	        parametros.put("forma_pago", complemento.getForma_pago());
	        parametros.put("tipo_cambio", complemento.getTipo_cambio());
	        parametros.put("tasa_iva", complemento.getTasa_iva());
	        parametros.put("estatus", complemento.getEstatus());
	        //parametros.put("uuid_documentos_relacionados", complemento.getUuid_documentos_relacionados());
	       
	        simplejdbcinsert.setTableName("complemento_pago");
	        simplejdbcinsert.setColumnNames(columns);
	        simplejdbcinsert.setGeneratedKeyName("id_complemento");
	        
	        //log.info(parametros.toString());
	        Number id_complemento = simplejdbcinsert.executeAndReturnKey(parametros);
			
			return id_complemento.intValue();
		}
		
	@Override
	
	public List<Complemento> listarComplementos(String rfc) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("rfc", rfc);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE cp.rfc_emisor = :rfc");
	
		return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new ComplementoRM());
	
	}



	@Override
	public Complemento obtenerComplementoById(Integer id) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE cp.id_complemento = :id");
		
		return namedParameterJdbcTemplate.queryForObject(selectQuery, params, Complemento.class);
	}



	@Override
	public List<Complemento> buscarComplementos(String search, String rfc) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("search", String.format("%%%s%%",search));
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		
		/// asd
		if(rfc == null){
			selectPQuery.append("WHERE CONCAT_WS('',rfc_emisor,razonsocial_emisor) LIKE :search");
			return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new ComplementoRM());
		}
		//
		
		params.addValue("rfc_emisor", rfc);
		selectPQuery.append("WHERE CONCAT_WS('',rfc_emisor,razonsocial_emisor) LIKE :search AND cp.rfc_emisor = :rfc_emisor");
		
		
		
		return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new ComplementoRM());
		
		
	}







}
