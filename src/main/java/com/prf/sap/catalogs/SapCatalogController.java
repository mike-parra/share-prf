package com.prf.sap.catalogs;

import java.util.List;

import com.prf.common.CustomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/sap-catalog")
@PropertySource("classpath:open-api-descriptions/openapi-sap-catalog.properties")
public class SapCatalogController {
	
	private static final Logger log = LoggerFactory.getLogger(SapCatalogController.class);
	
	private SapCatalogService catalogService;
	
	@Autowired
	public SapCatalogController(SapCatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@GetMapping("/clave-banco")
	@Operation(summary = "Recuperar lista de claves bancarias", description = "${clave-banco.get.description}" )
	public ResponseEntity<CustomResponse> listarProvedores() {
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			
			List<Banco> bancos = catalogService.listClaveBanco();
			
			resp.setEstatus("OK");
			resp.setMensaje("Lista recuperada con exito");
			resp.setObject(bancos);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al recuperar la lista ");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);	
	}
	
	@GetMapping("/paises")
	@Operation(summary = "Recuperar lista paises", description = "${paises.get.description}" )
	public ResponseEntity<CustomResponse> listarPaises() {
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			
			List<Pais> paises = catalogService.listPaises();
			
			resp.setEstatus("OK");
			resp.setMensaje("Lista recuperada con exito");
			resp.setObject(paises);
			
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
