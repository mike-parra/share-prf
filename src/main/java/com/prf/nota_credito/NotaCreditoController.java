package com.prf.nota_credito;

import java.util.List;
import java.util.Map;

import com.prf.common.CustomResponse;
import com.prf.proveedor.ProveedorController;
import com.prf.security.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/nota_credito")
@PropertySource("classpath:open-api-descriptions/openapi-nota-credito.properties")

public class NotaCreditoController {

	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);

	private NotaCreditoService nota_creditoService;

	@Autowired
	NotaCreditoController(NotaCreditoService nota_creditoService) {
		this.nota_creditoService = nota_creditoService;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Crear una nota de credito", description = "${nota_credito.post.description}")
	public ResponseEntity<CustomResponse> agregarNotaCredito(@RequestBody NotaCredito nota_credito) {

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		int idusuario = userDetailsImpl.getId().intValue();

		try {
			Integer iddocumento = this.nota_creditoService.agregarNotaCredito(idusuario, nota_credito);
			resp.setEstatus("OK");
			resp.setMensaje("Nota de credito creada con exito");
			resp.setObject(iddocumento);
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("Error");
			resp.setMensaje("Error al crear la nota de credito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Listar las notas de credito", description = "${nota_credito.get.single.description}")
	public ResponseEntity<CustomResponse> listarNotaCredito() {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		int idusuario = userDetailsImpl.getId().intValue();

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			List<NotaCredito> f = this.nota_creditoService.listarNotasCredito(idusuario);
			resp.setObject(f);
			if (f.size() > 0) {
				resp.setEstatus("OK");
				resp.setMensaje("Notas Credito recuperado con exito");
			} else {
				resp.setEstatus("OK");
				resp.setMensaje("Usurario sin notas de credito");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al listar las notas de credito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@PutMapping("/activar/{id_nota_credito}")
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Activar una nota de credito", description = "${nota_credito.put.activate.description}")
	public ResponseEntity<CustomResponse> activarNotaCredito(@PathVariable Integer id_nota_credito) {

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			this.nota_creditoService.activarNotaCredito(id_nota_credito);
			resp.setEstatus("OK");
			resp.setMensaje("Nota de crédito activada con éxito");
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al borrar activar nota de crédito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@DeleteMapping("/borrar/{id_nota_credito}")
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Borrar una nota de credito", description = "${nota_credito.delete.desactivate.description}")
	public ResponseEntity<CustomResponse> desactivarNotaCredito(@PathVariable Integer id_nota_credito) {

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			this.nota_creditoService.desactivarNotaCredito(id_nota_credito);
			resp.setEstatus("OK");
			resp.setMensaje("Nota de crédito borrada con éxito");
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al borrar la nota de crédito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@GetMapping("/{fecha_timbrado}")
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Listar notas de credito por fecha", description = "${nota_credito.get.date.description}")
	public ResponseEntity<CustomResponse> obtenerNotaCreditoPorFechaTimbrado(@PathVariable String fecha_timbrado) {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		int idusuario = userDetailsImpl.getId().intValue();

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			List<NotaCredito> doc = this.nota_creditoService.obtenerNotaCreditoPorFechaTimbrado(idusuario,
					fecha_timbrado);
			resp.setObject(doc);
			if (doc.size() > 0) {
				resp.setEstatus("OK");
				resp.setMensaje("Nota Credito recuperada con exito");
			} else {
				resp.setEstatus("OK");
				resp.setMensaje("Factura sin notas de credito apartir de esa fecha");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar las notas de credito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@GetMapping("/uuid_factura/{uuid_factura}")
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Listar notas de uuid de la factura", description = "${nota_credito.get.uuid_factura.description}")
	public ResponseEntity<CustomResponse> obtenerNotaCreditoPorUuidFactura(@PathVariable String uuid_factura) {
//@RequestBody Map<String, Object> jsonRequest  -  String uuid_factura = jsonRequest.get("uuid_factura").toString();
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		int idusuario = userDetailsImpl.getId().intValue();

		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			List<NotaCredito> doc = this.nota_creditoService.obtenerNotaCreditoPorUuidFactura(idusuario, uuid_factura);
			System.out.println(doc.size());
			resp.setObject(doc);
			if (doc.size() > 0) {
				resp.setEstatus("OK");
				resp.setMensaje("Nota Credito recuperado con exito");
			} else {
				resp.setEstatus("OK");
				resp.setMensaje("Factura sin notas de credito");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar las notas de credito");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		return new ResponseEntity<CustomResponse>(resp, httpStatus);
	}

	@GetMapping("/saludar")
	public String saludar() {
		return "Hola mundo";
	}

}
