package com.prf.documento;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.Gson;
import org.springframework.jdbc.core.RowMapper;

public class DocumentoRM implements RowMapper<Documento> {

	@Override
	public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Documento doc = new Documento();
        String[] formatos = new Gson().fromJson(rs.getString("formato_aceptados"), String[].class);

				
		doc.setIddocumento(rs.getInt("iddocumento"));
		doc.setNombre_documento(rs.getString("nombre_documento"));
		doc.setDescripcion_documento(rs.getString("descripcion_documento"));
		doc.setFormato_aceptados(formatos);
		doc.setTamanyo_documento(rs.getString("tamanyo_documento"));
		return doc;
	}

} 