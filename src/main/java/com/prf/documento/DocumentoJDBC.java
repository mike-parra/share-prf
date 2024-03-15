 package com.prf.documento;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;





 @Repository
 public class DocumentoJDBC implements DocumentoDAO{
	 
	 final String QueryObtenerDocumentoId = "SELECT * FROM documento WHERE iddocumento = ?";
	 final String QueryObtenerDocumentoname = "SELECT * FROM documento WHERE nombre_documento = ?";
	 final String QueryListarDocumento = "SELECT * FROM documento";
	 final String QueryEliminarDocumento = "DELETE FROM documento WHERE iddocumento = ?";

	 @Autowired
	 JdbcTemplate jdbcTemplate;


	     @Override
	     public Documento buscarDocumentosPorNombre(String nombre) {
	 	 		StringBuilder query = new StringBuilder(QueryObtenerDocumentoname);
	 	         Documento doc= jdbcTemplate.queryForObject(query.toString(), new DocumentoRM(), nombre);
	 	         return doc;
	 	     }

	     @Override
	     public Documento obtenerDocumentoPorId(Integer id) {
	 		StringBuilder query = new StringBuilder(QueryObtenerDocumentoId);
	         Documento doc= jdbcTemplate.queryForObject(query.toString(), new DocumentoRM(), id);
	         return doc;
	     }

	     @Override
	     public int agregarDocumento(Documento documento) {
	    		SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(jdbcTemplate);

	    		List<String> columnas = new ArrayList<>();
	    		columnas.add("nombre_documento");
	    		columnas.add("descripcion_documento");
	    		columnas.add("formato_aceptados");
	    		columnas.add("tamanyo_documento");

	    		
	    		simpleInsert.setTableName("documentos");
	    		simpleInsert.setColumnNames(columnas);
	    		Gson gson = new Gson();
	    		
	    		Map<String, Object> parametros = new HashMap<>();
	    		parametros.put("nombre_documento", documento.getNombre_documento());
	    		parametros.put("descripcion_documento", documento.getDescripcion_documento());
	    		parametros.put("formato_aceptados",gson.toJson(documento.getFormato_aceptados()));
	    		parametros.put("tamanyo_documento", documento.getTamanyo_documento());

	    		simpleInsert.setGeneratedKeyName("idDocumento");
	    		Number idDocumento = simpleInsert.executeAndReturnKey(parametros);
	    		
	    		return idDocumento.intValue();
	    		
	    	}

	     @Override
	     public void actualizarDocumento(Documento documento) {
	    	 Gson gson = new Gson();
	 		StringBuilder updateQuery = new StringBuilder("UPDATE documentos SET ");
			updateQuery.append(String.format("nombre_documento = '%s', ", documento.getNombre_documento()));
			updateQuery.append(String.format("descripcion_documento = '%s', ", documento.getDescripcion_documento()));
			updateQuery.append(String.format("formato_aceptados = '%s', ",gson.toJson(documento.getFormato_aceptados())));
			updateQuery.append(String.format("tamanyo_documento = '%s' ", documento.getTamanyo_documento()));
			updateQuery.append(String.format("WHERE idDocumento = '%d' ", documento.getIddocumento()));
			jdbcTemplate.execute(updateQuery.toString());
	     }

	     @Override
	     public void eliminarDocumento(Integer iddocumento) {
	    	 StringBuilder selectPQuery = new StringBuilder(this.QueryEliminarDocumento);
	         jdbcTemplate.update(selectPQuery.toString(), iddocumento);
	     }

	     @Override
	     public List<Documento> listarDocumentos() {
	 		StringBuilder selectPQuery = new StringBuilder(this.QueryListarDocumento);
			 return jdbcTemplate.query(selectPQuery.toString(), new DocumentoRM());
	     }
	 }
