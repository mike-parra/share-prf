package com.prf.nota_credito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prf.proveedor.ProveedorController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class NotaCreditoJDBC implements NotaCreditoDAO {
	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);

	final String selectQuery = "SELECT *  FROM  nota_credito nc join factura f on (nc.uuid_factura=f.uuid)";
	final String updateQuery = "UPDATE nota_credito SET estatus = 'INACTIVO' ";
	final String updateQueryActivate = "UPDATE nota_credito SET estatus = 'ACTIVO' ";
	final String QueryObtenerNotaCreditoId = "SELECT * FROM nota_credito WHERE id_nota_credito = ?";

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public NotaCreditoJDBC(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = npJdbcTemplate;
	}

	@Override
	public Integer agregarNotaCredito(NotaCredito nota_credito) {

		SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(jdbcTemplate);

		List<String> columnas = new ArrayList<>();
		columnas.add("uuid_factura");
		columnas.add("moneda");
		columnas.add("tipo_cambio");
		columnas.add("monto");
		columnas.add("fecha_timbrado");
		columnas.add("metodo_pago");
		columnas.add("forma_pago");
		columnas.add("uuid_nota_credito");

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("uuid_factura", nota_credito.getUuid_factura());
		parametros.put("moneda", nota_credito.getMoneda());
		parametros.put("tipo_cambio", nota_credito.getTipo_cambio());
		parametros.put("monto", nota_credito.getMonto());
		parametros.put("fecha_timbrado", nota_credito.getFecha_timbrado());
		parametros.put("metodo_pago", nota_credito.getMetodo_pago());
		parametros.put("forma_pago", nota_credito.getForma_pago());
		parametros.put("uuid_nota_credito", nota_credito.getUuid_nota_credito());

		simpleInsert.setTableName("nota_credito");
		simpleInsert.setColumnNames(columnas);
		simpleInsert.setGeneratedKeyName("id_nota_credito");

		Number idNotaCredito = simpleInsert.executeAndReturnKey(parametros);

		return idNotaCredito.intValue();
	}

	@Override
	public List<NotaCredito> listarNotasCredito(String rfc) {
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);

		selectPQuery.append(" WHERE ");
		selectPQuery.append("f.rfc_emisor = :rfc");
		params.addValue("rfc", rfc);

		return namedParameterJdbcTemplate.query(selectPQuery.toString(), params, new NotaCreditoRM());
	}

	@Override
	public void desactivarNotaCredito(Integer id_nota_credito) {
		StringBuilder updateQuery = new StringBuilder(this.updateQuery);
		updateQuery.append(" WHERE ");
		updateQuery.append(" id_nota_credito = :id_nota_credito ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_nota_credito", id_nota_credito);

		namedParameterJdbcTemplate.update(updateQuery.toString(), params);

	}

	@Override
	public void activarNotaCredito(Integer id_nota_credito) {
		StringBuilder updateQuery = new StringBuilder(this.updateQueryActivate);
		updateQuery.append(" WHERE ");
		updateQuery.append(" id_nota_credito = :id_nota_credito ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_nota_credito", id_nota_credito);

		namedParameterJdbcTemplate.update(updateQuery.toString(), params);

	}

	@Override
	public List<NotaCredito> obtenerNotaCreditoPorFechaTimbrado(String rfc, String fecha_timbrado) {
		StringBuilder query = new StringBuilder(this.selectQuery);
		query.append(" WHERE ");
		query.append(" nc.fecha_timbrado >= :fecha_timbrado ");
		query.append(" AND ");
		query.append("f.rfc_emisor = :rfc");

		System.out.println(fecha_timbrado);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("fecha_timbrado", fecha_timbrado);
		params.addValue("rfc", rfc);

		return namedParameterJdbcTemplate.query(query.toString(), params, new NotaCreditoRM());
	}

	@Override
	public List<NotaCredito> obtenerNotaCreditoPorUuidFactura(String rfc, String uuid_factura) {
		StringBuilder query = new StringBuilder(this.selectQuery);
		query.append(" WHERE ");
		query.append(" nc.uuid_factura = :uuid_factura ");
		query.append(" AND ");
		query.append("f.rfc_emisor = :rfc");

		System.out.println(uuid_factura);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("uuid_factura", uuid_factura);
		params.addValue("rfc", rfc);

		return namedParameterJdbcTemplate.query(query.toString(), params, new NotaCreditoRM());
	}

}
