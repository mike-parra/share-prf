package com.prf.orden_compra;

import java.util.List;

import com.prf.common.CustomResponse;
import com.prf.factura.EntradaMercancia;
import com.prf.factura.Factura;
import com.prf.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/ordencompra")
public class OrdenCompraController {
	
    @Autowired
    OrdenCompraService ordenCompraService;

    @GetMapping("/entradas")
    public String saludar() {
        return "Obtener entradas";
    }
    @GetMapping("/ordenCompra")
    public String listarOrden() {
        return "Obtener OrdenCompra";
    } 
    @GetMapping("/proovedor")
    public String obtenerProv() {
        return "Obtener Proveedor";
    }
    @GetMapping("/listar")
    public ResponseEntity<CustomResponse> listarOrdenCompra() {
		CustomResponse resp = new CustomResponse();
		try {
			List<OrdenCompra> oc = ordenCompraService.listarOrdenCompra();
			resp.setObject(oc);
			resp.setEstatus("OK");
			resp.setMensaje("Orden de compra  recuperada con exito");
		}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al obtener el id de la orden de compra");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
    
	@GetMapping("/listar/{id_oc}")
	@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	public ResponseEntity<CustomResponse> obtenerOrdenCompraById(@PathVariable("id_oc")Integer id_oc){
		
		CustomResponse resp = new CustomResponse();
		try {
			OrdenCompra oc = ordenCompraService.obtenerOrdenCompraById(id_oc);
			resp.setObject(oc);
			resp.setEstatus("OK");
			resp.setMensaje("Orden de compra  recuperada con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al obtener el id de la orden de compra");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
	
    @GetMapping("/orden-compra")
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Obtener las ordenes de compra filtradas por termino", description = "## _Proceso_: \n1. Crea un complemento de pago")
	public ResponseEntity<CustomResponse> obtenerOrdenCompraByQuery(@RequestParam String query) {
    	CustomResponse resp = new CustomResponse();
		try {
			List<OrdenCompra> oc = ordenCompraService.obtenerOrdenCompraByQuery(query);
			resp.setObject(oc);
			resp.setEstatus("OK");
			resp.setMensaje("Orden de compra  recuperada con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al obtener el id de la orden de compra");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
    
    @GetMapping("/entrada-mercancia")
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Obtener bienes por orden de compra", description = "## _Proceso_: \n1. Crea un complemento de pago")
	public ResponseEntity<CustomResponse> obtenerEntradaMercanciaPorOrdenCompra(@RequestParam Integer ordenCompra) {
    	CustomResponse resp = new CustomResponse();
		try {
			List<OrdenCompra> entradaMercancia = ordenCompraService.obtenerEntradaMercanciaPorOrdenCompra(ordenCompra);
			resp.setObject(entradaMercancia);
			resp.setEstatus("OK");
			resp.setMensaje("Entrada/s de Mercancia recuperada/s con exito");
		} catch(Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al traer entrada/s de mercancia");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
				
		}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
}
             