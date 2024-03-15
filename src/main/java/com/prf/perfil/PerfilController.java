package com.prf.perfil;

import java.util.List;

import com.prf.common.CustomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	// private static final Logger log = LoggerFactory.getLogger();

	private PerfilService perfilService;

	@Autowired
	public PerfilController(PerfilService perfilService) {
		this.perfilService = perfilService;
	}

	@GetMapping("/listar")
//	@PreAuthorize("hasAuthority('USR_VER')")
	public ResponseEntity<CustomResponse> listarUsuario() {

		CustomResponse resp = new CustomResponse();
		try {
			List<Perfil> listarPerfiles = perfilService.getListaGeneralPerfiles();
			resp.setObject(listarPerfiles);
			resp.setEstatus("OK");
			resp.setMensaje("Perfiles recuperados con exito");
		} catch (Exception e) {
			// log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar usuarios");

			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}

}