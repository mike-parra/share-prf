package com.prf.approver;

import java.util.List;

import com.prf.common.CustomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aprobadores")
public class AprobadorController {

	private static final Logger log = LoggerFactory.getLogger(AprobadorController.class);
	 
	
    private AprobadorService aprobadorService;
    
    @Autowired
	 public AprobadorController(AprobadorService aprobadorService) {
		 this.aprobadorService = aprobadorService;
	 }
	 
	@GetMapping("/listar")
	@PreAuthorize("hasAuthority('ADMI')")
	public ResponseEntity<CustomResponse> listarUsuario(){
		
		CustomResponse resp = new CustomResponse();
		try {
			List<UsuarioAprobador> listarUsuario = aprobadorService.listarUsuarioAprovadr();
			resp.setObject(listarUsuario);
			resp.setEstatus("OK");
			resp.setMensaje("Aprobadores recuperados con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar aprobadores");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
	
	@GetMapping("/buscar/{search}")
	@PreAuthorize("hasAuthority('ADMI')")
	public ResponseEntity<CustomResponse> listarUsuariosProspectos(@PathVariable("search")String search){
		
		CustomResponse resp = new CustomResponse();
		try {
			List<UsuarioAprobador> listarUsuario = aprobadorService.listarUsuariosProspectos(search);
			resp.setObject(listarUsuario);
			resp.setEstatus("OK");
			resp.setMensaje("Aprobadores recuperados con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar aprobadores");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
	
	@PutMapping("/editar/{id}/{idnivel}")
	@PreAuthorize("hasAuthority('ADMI')")
	public ResponseEntity<CustomResponse> editarUsuario(@PathVariable("id")Integer id,@PathVariable("idnivel")Integer idnivel)
	{
		CustomResponse resp = new CustomResponse();
		try {
			aprobadorService.editarNivelApprover(id,idnivel);
			resp.setEstatus("OK");
			resp.setMensaje("Nivel editado con exito");
			//resp.setObject();
		}catch(Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Ocurrio un error al editar el nivel");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/borrar/{id}")
	@PreAuthorize("hasAuthority('ADMI')")
	public ResponseEntity<CustomResponse> borrarUsuarioAprobador(@PathVariable("id")Integer id)
	{
		CustomResponse resp = new CustomResponse();
		try {
			aprobadorService.borrarApproverUsuarioById(id);
			resp.setEstatus("OK");
			resp.setMensaje("Aprobador borrado con exito");
			//resp.setObject();
		}catch(Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Ocurrio un error al borrar el aprovador");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
	
	@GetMapping("/listar/nv_auth")
	@PreAuthorize("hasAuthority('ADMI')")
	public ResponseEntity<CustomResponse> listarNivelAuth(){
		
		CustomResponse resp = new CustomResponse();
		try {
			List<Nivel_Auth> listNivel = aprobadorService.listarNivelesAuth();
			resp.setObject(listNivel);
			resp.setEstatus("OK");
			resp.setMensaje(" Listar niveles ");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar niveles");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}

}