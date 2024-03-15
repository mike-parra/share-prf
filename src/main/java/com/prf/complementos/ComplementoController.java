package com.prf.complementos;


import java.util.List;

import com.prf.common.CustomResponse;
import com.prf.proveedor.ProveedorController;
import com.prf.security.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/complemento")	
public class ComplementoController {
	
	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
   
    private ComplementoService complementoService;
    
    
    
    @Autowired
    ComplementoController(ComplementoService complementoService){
    	this.complementoService = complementoService;
    }

  
    @PostMapping
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Crear un complemento de pago", description = "## _Proceso_: \n1. Crea un complemento de pago")
	public ResponseEntity<CustomResponse> crearComplemento(@RequestBody Complemento complemento) {
    	
    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
    			.getPrincipal();
    	
        int idusuario = userDetailsImpl.getId().intValue();
    	
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			
			Integer idproveedor = this.complementoService.crearComplemento(complemento, idusuario);
			resp.setEstatus("OK");
			resp.setMensaje("Complemento creado con exito");
			resp.setObject(idproveedor);

		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al crear el proveedor");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}
    
    
   @GetMapping("/listar")
   @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
   public ResponseEntity<CustomResponse> listarComplementos() {
	   
	   UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
   			.getPrincipal();
	   
	   int idusuario = userDetailsImpl.getId().intValue();
	   
	   CustomResponse resp = new CustomResponse();
		try {
			List<Complemento> oc = complementoService.listarComplementos(idusuario);
			resp.setObject(oc);
			resp.setEstatus("OK");
			resp.setMensaje("Complementos recuperado con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al listar complementos");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
   
   @GetMapping("/{id}")
   @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
   public ResponseEntity<CustomResponse> obtenerComplementoPorId(@PathVariable("id") Integer id) {
	   
	   CustomResponse resp = new CustomResponse();
		try {
			Complemento p = complementoService.obtenerComplementoById(id);
			resp.setObject(p);
			resp.setEstatus("OK");
			resp.setMensaje("Complemento recuperado con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al traer complemento");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
  
   @GetMapping("/buscar/{search}")
   //@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
   public ResponseEntity<CustomResponse> buscarComplementos(@PathVariable("search") String search) {
	    
	   UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	   			.getPrincipal();
		   
		   int idusuario = userDetailsImpl.getId().intValue();
	   
	   CustomResponse resp = new CustomResponse();
		try {
			List<Complemento> oc = complementoService.buscarComplementos(search,idusuario);
			resp.setObject(oc);
			resp.setEstatus("OK");
			resp.setMensaje("Complementos recuperados con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al listar complementos");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
	
	
}
             