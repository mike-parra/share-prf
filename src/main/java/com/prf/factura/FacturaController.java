package com.prf.factura;

import java.util.List;

import com.prf.common.CustomResponse;
import com.prf.complementos.Complemento;
import com.prf.complementos.ComplementoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/factura")	
public class FacturaController {
	
	private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
	   
    private FacturaService facturaService;
    
    @Autowired
    FacturaController(FacturaService facturaService){
    	this.facturaService = facturaService;
    }
  
    @PostMapping
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
	@Operation(summary = "Crear un complemento de pago", description = "## _Proceso_: \n1. Crea un complemento de pago")
	public ResponseEntity<CustomResponse> crearFactura(@RequestBody Factura factura) {
    	
    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
    			.getPrincipal();
    	
        int idusuario = userDetailsImpl.getId().intValue();
    	
		CustomResponse resp = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			
			Integer idproveedor = this.facturaService.crearFactura(factura,idusuario);
			resp.setEstatus("OK");
			resp.setMensaje("Factura creada con exito");
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
    public ResponseEntity<CustomResponse> listarFacturas() {
 	   
 	   UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
    			.getPrincipal();
 	   
 	   int idusuario = userDetailsImpl.getId().intValue();
 	   
 	   CustomResponse resp = new CustomResponse();
 		try {
 			List<Factura> f = facturaService.listarFacturas(idusuario);
 			resp.setObject(f);
 			resp.setEstatus("OK");
 			resp.setMensaje("Facturas creadas con exito");
		}catch(Exception e) {
 		//	log.error(e.getMessage());
 			resp.setEstatus("ERROR");
 			resp.setDescripcionError(e.getMessage());
 			resp.setMensaje("Error al listar facturas");
 			
 			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
 				
		}
 		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}
    
    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
    public ResponseEntity<CustomResponse> obtenerFacturaPorId(@PathVariable("id") Integer id) {
 	   
 	   CustomResponse resp = new CustomResponse();
 		try {
 			Factura f = facturaService.obtenerFacturaById(id);
 			resp.setObject(f);
 			resp.setEstatus("OK");
 			resp.setMensaje("Factura recuperada con exito");
		}catch(Exception e) {
 		//	log.error(e.getMessage());
 			resp.setEstatus("ERROR");
 			resp.setDescripcionError(e.getMessage());
 			resp.setMensaje("Error al traer factura");
 			
 			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
 				
		}
 		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);

	}
    
    @GetMapping("/listar-uuid/{uuid}")
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
    public ResponseEntity<CustomResponse> obtenerFacturaPorUuid(@PathVariable("uuid") String uuid) {
 	   
 	   CustomResponse resp = new CustomResponse();
 		try {
 			Factura f = facturaService.obtenerFacturaByUuid(uuid);
 			resp.setObject(f);
 			resp.setEstatus("OK");
 			resp.setMensaje("Factura recuperada con exito");
		}catch(Exception e) {
 		//	log.error(e.getMessage());
 			resp.setEstatus("ERROR");
 			resp.setDescripcionError(e.getMessage());
 			resp.setMensaje("Error al traer factura");
 			
 			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
 				
		}
 		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);

	}
    
    @PostMapping("/subir")
    @PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
    public ResponseEntity<CustomResponse> subirFactura(@RequestBody MultipartFile factura) {
 	   
 	   CustomResponse resp = new CustomResponse();
 		try {
 			String url = facturaService.subirFactura(factura);
 			resp.setObject(url);
 			resp.setEstatus("OK");
 			resp.setMensaje("Factura recuperada con exito");
		} catch(Exception e) {
 		//	log.error(e.getMessage());
 			resp.setEstatus("ERROR");
 			resp.setDescripcionError(e.getMessage());
 			resp.setMensaje("Error al traer factura");
 			
 			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
 				
		}
 		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
	}   
    

 		}
   
    /*
    @GetMapping("/documentosrelacionados")
    //@PreAuthorize("hasAuthority('EMPL') or hasAuthority('PROV')")
    public ResponseEntity<CustomResponse> listarDocumentos(@RequestBody String[] uuids) {
 	   
 	   CustomResponse resp = new CustomResponse();
 		try {
 			List<Factura> f = facturaService.obtenerDocumentosRelacionados(uuids);
 			resp.setObject(f);
 			resp.setEstatus("OK");
 			resp.setMensaje("Facturas creadas con exito");
 			}catch(Exception e) {
 		//	log.error(e.getMessage());
 			resp.setEstatus("ERROR");
 			resp.setDescripcionError(e.getMessage());
 			resp.setMensaje("Error al listar facturas");
 			
 			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
 				
 			}
 		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
 		}*/

//}
