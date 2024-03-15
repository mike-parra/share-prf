package com.prf.approver;

import java.util.List;

import com.prf.usuario.UsuarioPerfilRM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AprobadorJDBC implements AprobadorDAO {
	
	final String selectQuery = "SELECT  u.idusuario ,u.usuario, na.idnivel_aprobacion  ,na.descripcion AS nivel_auth "
			+ " FROM reciboodb.nivel_aprobacion na "
			+ " JOIN reciboodb.usuario u on (u.idnivel_aprobacion = na.idnivel_aprobacion AND na.jerarquia >= 1 )";
	
	final String selectApr = "SELECT u.idusuario ,u.usuario, na.idnivel_aprobacion  ,na.descripcion AS nivel_auth "
			+ " FROM reciboodb.usuario u"
			+ " JOIN reciboodb.usuario_perfil up on (u.idusuario  = up.idusuario AND up.idperfil = 2) "
			+ " JOIN reciboodb.nivel_aprobacion na on (u.idnivel_aprobacion = na.idnivel_aprobacion AND na.idnivel_aprobacion = 4) ";
	
	final String selectNvAuth = "SELECT na.idnivel_aprobacion ,na.descripcion, na.jerarquia  "
			+ "FROM reciboodb.nivel_aprobacion na ";
	
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public AprobadorJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<UsuarioAprobador> listarUsuarioAprovadr() {
		
		 return jdbcTemplate.query(selectQuery, new AprobadorRM());
	}

	@Override
	public List<UsuarioAprobador> listarUsuariosProspectos(String search) {
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			
			StringBuilder selectPQuery = new StringBuilder(selectApr);
			
				selectPQuery.append(" WHERE ");
				selectPQuery.append("CONCAT_WS(' ',u.nombre ,u.usuario) like :search ");
				namedParameters.addValue("search", String.format("%%%s%%",search));
				selectPQuery.append(" AND u.estatus = :status ");
				namedParameters.addValue("status", "ACTIVO");
		
		return namedParameterJdbcTemplate.query(selectPQuery.toString(),namedParameters,new AprobadorRM() );
	}

	@Override
	public void editarNivelApprover(Integer id,Integer idnivel) {
		StringBuilder updateQuery = new StringBuilder("UPDATE usuario SET ");
		updateQuery.append(String.format("idnivel_aprobacion = '%d' ", idnivel.intValue()));
		updateQuery.append(String.format("WHERE idusuario = '%d' ", id.intValue()));
		jdbcTemplate.execute(updateQuery.toString());	
	}

	@Override
	public void borrarApproverUsuarioById(Integer id) {
		
		StringBuilder updateQuery = new StringBuilder("UPDATE usuario SET ");
		updateQuery.append("idnivel_aprobacion = 4 ");
		updateQuery.append(String.format("WHERE idusuario = '%d' ", id.intValue()));
		jdbcTemplate.execute(updateQuery.toString());	
		
	}

	@Override
	public List<Nivel_Auth> listarNivelesAuth() {
		StringBuilder select = new StringBuilder(selectNvAuth);
		select.append(" WHERE ");
		select.append(" na.jerarquia > 0 ");
	   return jdbcTemplate.query(select.toString(), new Nivel_AuthRM());
	}

}
