package com.prf.perfil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PerfilJDBC implements PerfilDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PerfilJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	/*
	@Override
	public List<String> getAutoridadesPerfil(Integer idusuario) {
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id",idusuario);
		
		String queryAutoridades = " SELECT DISTINCT(mp.codigo) " + 
				"	FROM usuario u JOIN usuario_perfil up on u.idusuario = up.idusuario " + 
				"   JOIN perfil_modulo pm on up.idperfil = pm.idperfil " + 
				"   JOIN modulo_permiso mp on pm.idmodulo_permiso = mp.idmodulo_permiso " + 
				"   WHERE u.idusuario = :id ";

		return namedParameterJdbcTemplate.queryForList(queryAutoridades,namedParameters,String.class);
	}
*/
	@Override
	public boolean isActive(Integer idperfil) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id",idperfil);
		
		String activeQuery = " SELECT EXISTS ( SELECT * FROM perfil p WHERE p.estatus = 'ACTIVO' AND p.id_perfil = :id ) ";
		return namedParameterJdbcTemplate.queryForObject(activeQuery,namedParameters,Boolean.class);
	}

	@Override
	public List<Modulo> getModulos(Integer id_usuario) {
		 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			namedParameters.addValue("id",id_usuario);
		
		String queryModulos = "SELECT DISTINCT(m.codigo), m.id_modulo, m.descripcion  "
				+ "					FROM reciboodb.usuario u JOIN reciboodb.usuario_perfil up on u.id_usuario = up.id_usuario "
				+ "				   JOIN reciboodb.perfil_modulo pm on up.id_perfil = pm.id_perfil "
				+ "				   JOIN reciboodb.modulo_permiso mp on pm.idmodulo_permiso = mp.idmodulo_permiso "
				+ "				   JOIN reciboodb.modulo m on mp.id_modulo = m.id_modulo "
				+ "				   WHERE u.id_usuario = :id";
		
		return namedParameterJdbcTemplate.query(queryModulos,namedParameters, new ModuloRM());
	}

	@Override
	public List<Perfil> getPerfiles(Integer id_usuario) {
		 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			namedParameters.addValue("id",id_usuario);
		
		String queryRoles = 
				"SELECT p.* FROM reciboodb.usuario u JOIN reciboodb.usuario_perfil up on u.id_usuario = up.id_usuario "
				+ "					JOIN reciboodb.perfil p on up.id_perfil = p.id_perfil "
				+ "				    WHERE u.id_usuario = :id";
		return namedParameterJdbcTemplate.query(queryRoles,namedParameters, new PerfilRM());
	}

	@Override
	public void insertarPerfilesUsuario(int id_usuario, List<Perfil> perfiles) {
			
		String queryRolesInsert = "INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES(?, ?)";
	
		jdbcTemplate.batchUpdate(queryRolesInsert, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Perfil perfil = perfiles.get(i);
				ps.setInt(1, id_usuario);
				ps.setInt(2, perfil.getId_perfil());
			}

			@Override
			public int getBatchSize() {
				return perfiles.size();
			}
		});
	}

	@Override
	public void resetPerfilesUsuario(int id_usuario) {
		 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id",id_usuario);
		
		String queryRolesDelete = "DELETE FROM usuario_perfil WHERE idusuario = :id";
		//jdbcTemplate.update(queryRolesDelete, idusuario);
		namedParameterJdbcTemplate.update(queryRolesDelete,namedParameters);
		
	}

	@Override
	public List<Perfil> getListaGeneralPerfiles() {
		
		 NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			namedParameters.addValue("estatus","ACTIVO");
		
		String queryRoles = 
				"SELECT p.* FROM reciboodb.perfil p "
				+ "	WHERE p.estatus = :estatus";
		return namedParameterJdbcTemplate.query(queryRoles,namedParameters, new PerfilRM());
		
	}

}
