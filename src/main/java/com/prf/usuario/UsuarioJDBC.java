package com.prf.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public class UsuarioJDBC implements UsuarioDAO {

	final String selectQuery = "SELECT u.* FROM usuario u";

	private JdbcTemplate jdbcTemplate;

	private PasswordEncoder encoder;

	@Autowired
	public UsuarioJDBC(JdbcTemplate jdbcTemplate, PasswordEncoder encoder) {
		this.jdbcTemplate = jdbcTemplate;
		this.encoder = encoder;
	}
	
	@Override
	public Optional<Usuario> findByUsername(String username) {

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("usr", username);

		StringBuilder query = new StringBuilder(selectQuery + " WHERE (u.usuario = :usr OR u.email = :usr)");
		Usuario usr = namedParameterJdbcTemplate.queryForObject(query.toString(), namedParameters, new UsuarioRM());
		return Optional.ofNullable(usr);

	}

	@Override
	public int crearUsuario(Usuario usuario) {

		SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(jdbcTemplate);

		// esto se va a usar para el login, pero por ahora queda atras jaja
		List<String> columnas = new ArrayList<>();
		columnas.add("usuario");
		columnas.add("email");
		columnas.add("nombre");
		columnas.add("password");

		simpleInsert.setTableName("usuario");
		simpleInsert.setColumnNames(columnas);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("usuario", usuario.getUsuario());
		parametros.put("email", usuario.getEmail());
		parametros.put("password", encoder.encode(usuario.getPassword()));
		parametros.put("nombre", usuario.getNombre());

		simpleInsert.setGeneratedKeyName("id_usuario");
		Number id_Usuario = simpleInsert.executeAndReturnKey(parametros);

		return id_Usuario.intValue();

	}

	@Override
	public List<Usuario> listarUsuario() {
		StringBuilder selectPQuery = new StringBuilder(this.selectQuery);
		return jdbcTemplate.query(selectPQuery.toString(), new UsuarioRM());
	}

	@Override
	public Usuario obtenerUsuarioById(int id) {
		StringBuilder query = new StringBuilder(selectQuery + " WHERE id_usuario = ?");
		Usuario usr = jdbcTemplate.queryForObject(query.toString(), new UsuarioRM(), id);
		return usr;
	}

	@Override
	public void editarUsuario(Usuario usuario) {
		StringBuilder updateQuery = new StringBuilder("UPDATE usuario SET ");
		updateQuery.append(String.format("usuario = '%s', ", usuario.getUsuario()));
		updateQuery.append(String.format("nombre = '%s', ", usuario.getNombre()));
		updateQuery.append(String.format("email = '%s', ", usuario.getEmail()));
		updateQuery.append(String.format("password = '%s' ", encoder.encode(usuario.getPassword())));
		updateQuery.append(String.format("WHERE id_usuario = '%d' ", usuario.getId_usuario()));
		jdbcTemplate.execute(updateQuery.toString());
	}

	@Override
	public List<UsuarioPerfil> listarUsuarioByPerfil(String perfil) {

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("perfil", perfil);

		StringBuilder selectPQuery = new StringBuilder("SELECT u.*, p.*  FROM reciboodb.usuario u "
				+ "JOIN reciboodb.usuario_perfil up on u.id_usuario = up.id_usuario "
				+ "JOIN reciboodb.perfil p on up.idperfil = p.idperfil "
				+ "WHERE p.descripcion  =  :perfil AND u.estatus = 'ACTIVO'" + "");

		return namedParameterJdbcTemplate.query(selectPQuery.toString(), namedParameters, new UsuarioPerfilRM());
	}

	@Override
	public void desactivarUsuario(int id) {
		StringBuilder updateQuery = new StringBuilder("UPDATE usuario SET estatus = 'INACTIVO' ");
		updateQuery.append(String.format("WHERE id_usuario = '%d' ", id));
		jdbcTemplate.execute(updateQuery.toString());

	}

}
