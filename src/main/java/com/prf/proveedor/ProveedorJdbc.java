package com.prf.proveedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProveedorJdbc implements ProveedorDao {
	
	private final String info_bancaria_batch_query = " INSERT INTO informacion_bancaria (id_proveedor, codigo_pais, banco_clave, banco_denominacion, cuenta_bancaria, referencia, swift, titular_cuenta) ";
	
	private final String proveedor_select_query = " SELECT p.* FROM proveedor p ";
	
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public ProveedorJdbc (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = npJdbcTemplate;
	}

	@Override
	public Integer crearProveedor(Proveedor proveedor) {
		
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
		
		List<String> columns = new ArrayList<>();

        columns.add("id_usuario");
        columns.add("tratamiento");
        columns.add("nombre_razon_social");
        columns.add("rfc");
        columns.add("ramo_industrial_clave");
        columns.add("ramo_industrial_denominacion");
        columns.add("calle");
        columns.add("num_exterior");
        columns.add("num_interior");
        columns.add("colonia");
        columns.add("codigo_postal");
        columns.add("ciudad_poblacion");
        columns.add("estado_region_clave");
        columns.add("estado_region_denominacion");
        columns.add("pais_clave");
        columns.add("pais_denominacion");
        columns.add("nombre_contacto");
        columns.add("apellido_contacto");
        columns.add("email_contacto");
        columns.add("telefono_contacto");
        
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("id_usuario", proveedor.getId_usuario());
        parametros.put("tratamiento", proveedor.getTratamiento());
        parametros.put("nombre_razon_social", proveedor.getNombre_razon_social());
        parametros.put("rfc", proveedor.getRfc());
        parametros.put("ramo_industrial_clave", proveedor.getRamo_industrial_clave());
        parametros.put("ramo_industrial_denominacion", proveedor.getRamo_industrial_denominacion());
        parametros.put("calle", proveedor.getCalle());
        parametros.put("num_exterior", proveedor.getNum_exterior());
        parametros.put("num_interior", proveedor.getNum_interior());
        parametros.put("colonia", proveedor.getColonia());
        parametros.put("codigo_postal", proveedor.getCodigo_postal());
        parametros.put("ciudad_poblacion", proveedor.getCiudad_poblacion());
        parametros.put("estado_region_clave", proveedor.getEstado_region_clave());
        parametros.put("estado_region_denominacion", proveedor.getEstado_region_denominacion());
        parametros.put("pais_clave", proveedor.getPais_clave());
        parametros.put("pais_denominacion", proveedor.getPais_denominacion());
        parametros.put("nombre_contacto", proveedor.getNombre_contacto());
        parametros.put("apellido_contacto", proveedor.getApellido_contacto());
        parametros.put("email_contacto", proveedor.getEmail_contacto());
        parametros.put("telefono_contacto", proveedor.getTelefono_contacto());
        
        simpleJdbcInsert.setTableName("proveedor");
        simpleJdbcInsert.setColumnNames(columns);
        simpleJdbcInsert.setGeneratedKeyName("id_proveedor");
        
        Number id_proveedor = simpleJdbcInsert.executeAndReturnKey(parametros);
		
		return id_proveedor.intValue();
	}

	@Override
	public Proveedor obtenerProveedorPorId(Integer id_proveedor) {
		
		StringBuilder query = new StringBuilder(proveedor_select_query);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		query.append(" WHERE ");
		query.append(" p.id_proveedor = :id_proveedor ");
		params.addValue("id_proveedor", id_proveedor);
		
		return namedParameterJdbcTemplate.queryForObject(query.toString(), params, new ProveedorRM());
	}

	@Override
	public void guardarInformacionBancaria(ArrayList<Informacion_Bancaria> informacion_bancaria) {
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(informacion_bancaria.toArray());
		
		StringBuilder query = new StringBuilder(this.info_bancaria_batch_query);
		query.append(" VALUES ( ");
		query.append(" :id_proveedor, :codigo_pais, :banco_clave, :banco_denominacion, :cuenta_bancaria, :referencia, :swift, :titular_cuenta ");
		query.append(" ) ");
		
		this.namedParameterJdbcTemplate.batchUpdate(query.toString(), params);
		
	}

	@Override
	public Proveedor obtenerProveedorPorIdUsuario(Integer id_usuario) {
		
	StringBuilder query = new StringBuilder(proveedor_select_query);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		query.append(" WHERE ");
		query.append(" p.id_usuario = :id_usuario ");
		params.addValue("id_usuario", id_usuario);
		
		return namedParameterJdbcTemplate.queryForObject(query.toString(), params, new ProveedorRM());
		
	}

}
