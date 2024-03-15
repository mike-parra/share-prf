package com.prf.orden_compra;

import java.util.List;
import java.util.Optional;

import com.prf.factura.EntradaMercancia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class OrdenCompraJDBC implements OrdenCompraDAO{
	
	final String selectQuery = " SELECT * FROM orden_compra ";
	final String queryEntradaMercancia = " SELECT * FROM entrada_mercancia ";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public OrdenCompra obtenerOrdenCompraById(Integer id_oc) {
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		return jdbcTemplate.queryForObject(selectPQuery.toString(), new OrdenCompraRM());

	}

	@Override
	public Optional<OrdenCompra> findByOrdenCompra(Integer orden_compra) {
		
		return Optional.empty();
	}

	@Override
	public List<OrdenCompra> listarOrdenCompra() {
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		return jdbcTemplate.query(selectPQuery.toString(), new OrdenCompraRM());
	}

	@Override
	public List<OrdenCompra> obtenerOrdenCompraByQuery(String query) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("query", query);
				
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		selectPQuery.append("WHERE descripcion = :query");
		
		return jdbcTemplate.query(selectPQuery.toString(), new OrdenCompraRM());
	}

	@Override
	public List<OrdenCompra> obtenerEntradaMercanciaPorOrdenCompra(Integer orden_compra) {
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orden_compra", orden_compra);
		StringBuilder query = new StringBuilder(queryEntradaMercancia);
		query.append("JOIN");
		query.append("WHERE id_oc = :orden_compra");
		
		return jdbcTemplate.query(query.toString(), new OrdenCompraRM());
	}
	
	
	
}
