package com.prf.validation_xml;

import com.prf.common.CustomResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/validate-xml")
@PropertySource("classpath:open-api-descriptions/openapi-sap-catalog.properties")
public class ValidateXMLController {

private static final Logger log = LoggerFactory.getLogger(ValidateXMLController.class);
	
	private ValidateXMLService validate_XMLService;
	
	@Autowired
	public ValidateXMLController(ValidateXMLService validate_XMLService) {
		this.validate_XMLService = validate_XMLService;
	}
	
	@PostMapping
	@Operation(summary = "Validar XML", description = "${clave-banco.get.description}" )
	public ResponseEntity<CustomResponse> validarXML(@RequestBody ValidationXMLRequest xml) {
		
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			
			ValidationXMLResponse xml_validated = validate_XMLService.validarXML(xml);
			
			resp.setEstatus("OK");
			resp.setMensaje("Validacion realizada con exito");
			resp.setObject(xml_validated);
			
			if (xml_validated.getStatus() == "error") {
				resp.setEstatus("ERROR");
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
		} catch (HttpServerErrorException e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al realizar la validacion ");
			httpStatus = (HttpStatus) e.getStatusCode();	
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setDescripcionError(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al realizar la validacion ");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		
		return new ResponseEntity<CustomResponse>(resp, httpStatus);	
	}

}
