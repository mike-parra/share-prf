package com.prf.usuario;

import java.util.List;

import com.prf.common.CustomResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
//import com.salazar.rest.paquete.PaqueteController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	 private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/crear")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que crea un nuevo usuario y retorna su id", summary = "Usuario - Crear")
	public ResponseEntity<CustomResponse> createUsuario(@RequestBody Usuario usuario) {

		CustomResponse resp = new CustomResponse();

		try {
			int idusuario = usuarioService.crearUsuario(usuario);
			resp.setEstatus("OK");
			resp.setMensaje("Usuario creado con exito");
			resp.setObject(idusuario);
			// usuarioMail.sendUsuarioMail(idusuario);
		} catch (DuplicateKeyException e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("El nombre de usuario o correo ya existe.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("Ocurrió un error al crear el usuario.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/listar")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que lista de forma general los usuarios", summary = "Usuario - Lista general")
	public ResponseEntity<CustomResponse> listarUsuario() {


		CustomResponse resp = new CustomResponse();
		try {
			List<Usuario> listarUsuario = usuarioService.listarUsuario();
			resp.setObject(listarUsuario);
			resp.setEstatus("OK");
			resp.setMensaje("Usuarios recuperados con exito");
		} catch (Exception e) {
			// log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar usuarios");

			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}

	@GetMapping("/listar/{id}")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que trae un usuario segun el id", summary = "Usuario - Busqueda id")
	public ResponseEntity<CustomResponse> obtenerUsuarioById(@PathVariable("id") int id) {

		CustomResponse resp = new CustomResponse();
		try {
			Usuario usr = usuarioService.obtenerUsuarioById(id);
			resp.setObject(usr);
			resp.setEstatus("OK");
			resp.setMensaje("Usuario recuperado con exito");
		} catch (Exception e) {
			// log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar el usuario");

			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}

	@PutMapping("/editar")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que edita los campos de un usuario", summary = "Usuario - Editar")
	public ResponseEntity<CustomResponse> editarUsuario(@RequestBody Usuario usuario) {
		CustomResponse resp = new CustomResponse();

		try {
			usuarioService.editarUsuario(usuario);
			resp.setEstatus("OK");
			resp.setMensaje("Usuario editado con exito");

			// resp.setObject();
		} catch (DuplicateKeyException e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("El nombre de usuario o correo ya existe.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Ocurrio un error al editar el usuario");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/listar/perfiles/{perfil}")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que lista por perfil los usuarios", summary = "Usuario - Lista por perfil")
	public ResponseEntity<CustomResponse> obtenerUsuariosByPerfil(@PathVariable("perfil") String perfil) {

		CustomResponse resp = new CustomResponse();
		try {
			List<UsuarioPerfil> usuarios = usuarioService.listarUsuarioByPerfil(perfil);
			resp.setObject(usuarios);
			resp.setEstatus("OK");
			resp.setMensaje("Usuarios recuperado con exito");
		} catch (Exception e) {
			// log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar el usuario");

			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/desactivar/{id}")
	@PreAuthorize("hasAuthority('ADMI')")
	@Operation(description = "Metodo que borra de borma logica un usuario", summary = "Usuario - Desactivar")
	public ResponseEntity<CustomResponse> desactivarUsuarioById(@PathVariable("id") int id) {

		CustomResponse resp = new CustomResponse();
		try {
			usuarioService.desactivarUsuarioById(id);
			resp.setEstatus("OK");
			resp.setMensaje("Usuario desactivado con exito");
		} catch (Exception e) {
			// log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al desactivar el usuario");

			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}

}