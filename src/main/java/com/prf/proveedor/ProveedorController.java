package com.prf.proveedor;

import com.prf.common.CustomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/proveedor")
@PropertySource("classpath:open-api-descriptions/openapi-proveedor.properties")
public class ProveedorController {
	
	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
	
	private ProveedorService proveedorService;
	
	@Autowired
	public ProveedorController(ProveedorService proveedorService ) {
		this.proveedorService = proveedorService;
	}

	@PostMapping
	// @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Crear un prospecto proveedor", description = "${prospecto.post.description}")
	public ResponseEntity<CustomResponse> crearProveedor(@RequestBody Proveedor proveedor) {
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			
			Integer id_proveedor = this.proveedorService.crearProveedor(proveedor);
			resp.setEstatus("OK");
			resp.setMensaje("Proveedor creado con exito");
			resp.setObject(id_proveedor);
			
		}catch (DuplicateKeyException e){
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Provider data already exists");
			httpStatus = HttpStatus.BAD_REQUEST;	
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al crear el proveedor");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}
	
	@GetMapping("/{id_proveedor}")
	@PreAuthorize("hasAuthority('EMPL')")
	@Operation(summary = "Recuperar un prospecto por id", description = "${prospecto.get.single.description}")
	public ResponseEntity<CustomResponse> obtenerPorveedor(@PathVariable Integer id_proveedor) {
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			
			Proveedor proveedor = this.proveedorService.obtenerProveedorPorId(id_proveedor);
			resp.setEstatus("OK");
			resp.setMensaje("Proveedor recuperado con exito");
			resp.setObject(proveedor);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al recuperar el proveedor ");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('EMPL')")
	@Operation(summary = "Recuperar lista de prospectos", description = "${prospecto.get.description}" )
	public ResponseEntity<CustomResponse> listarProvedores() {
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al recuperar la lista ");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);	
	}
	
}