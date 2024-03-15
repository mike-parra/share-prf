package com.prf.documento;

import java.util.List;

import com.prf.common.CustomResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/documento")
public class DocumentoController {
	@Autowired
     DocumentoService documentoService;

    @PostMapping("/agregar")
public ResponseEntity<CustomResponse> agregarDocumento(@RequestBody Documento documento) {
        
        CustomResponse resp = new CustomResponse();

        try {
			Integer iddocumento = documentoService.agregarDocumento(documento);
			resp.setEstatus("OK");
			resp.setMensaje("Documento creado con exito");
			resp.setObject(iddocumento);
		} catch (DuplicateKeyException e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("El nombre del Documento ya existe.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("Ocurrió un error al crear el Documento.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

        return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
    }
    

    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<CustomResponse> buscarDocumentosPorNombre(@PathVariable("nombre")String nombre){
    	CustomResponse resp = new CustomResponse();
    	
    	try {
    		Documento doc =documentoService.buscarDocumentosPorNombre(nombre);
			resp.setObject(doc);
			resp.setEstatus("OK");
			resp.setMensaje("Documentos recuperados con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar el documento");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);
				
    		
    	}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
    }
    
    
    @GetMapping("/listar")
    public ResponseEntity<CustomResponse> listarDocumentos(){
    	CustomResponse resp = new CustomResponse();
    	
    	try {
    		List<Documento> listarDocumentos= documentoService.listarDocumentos();
			resp.setObject(listarDocumentos);
			resp.setEstatus("OK");
			resp.setMensaje("Documentos recuperados con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar el documento");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);
				
    		
    	}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
    }

    @GetMapping("/listar/{idDocumento}")
public ResponseEntity<CustomResponse> obtenerUsuarioById(@PathVariable("idDocumento")Integer idDocumento){
		
		CustomResponse resp = new CustomResponse();
		try {
			Documento doc = documentoService.obtenerDocumentoPorId(idDocumento);
			resp.setObject(doc);
			resp.setEstatus("OK");
			resp.setMensaje("Documento recuperado con exito");
			}catch(Exception e) {
		//	log.error(e.getMessage());
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Error al recuperar el documento");
			
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.NO_CONTENT);
				
			}
		return new ResponseEntity<CustomResponse>(resp, HttpStatus.OK);
		}
    

    @PutMapping("/editar/{idDocumento}")
    public ResponseEntity<CustomResponse> actualizarDocumento(@RequestBody Documento documento, @PathVariable("idDocumento")Integer idDocumento)
	{
		CustomResponse resp = new CustomResponse();
		
		try {
			documentoService.actualizarDocumento(documento);
			resp.setEstatus("OK");
			resp.setMensaje("Documento editado con exito");
			//resp.setObject();
		}catch (DuplicateKeyException e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("El nombre del documento ya existe.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Ocurrio un error al editar el documento");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
	}
    


    
    @DeleteMapping("/eliminar/{idDocumento}")
    public ResponseEntity<CustomResponse> eliminarDocumento(@PathVariable Integer idDocumento) {
        
		CustomResponse resp = new CustomResponse();
		
		try {
			documentoService.eliminarDocumento(idDocumento);
			resp.setEstatus("OK");
			resp.setMensaje("Documento eliminado con exito");
			//resp.setObject();
		}catch (DuplicateKeyException e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.toString());
			resp.setMensaje("El documento ya no existe.");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(Exception e) {
			resp.setEstatus("ERROR");
			resp.setDescripcionError(e.getMessage());
			resp.setMensaje("Ocurrio un error al eliminar el documento");
			return new ResponseEntity<CustomResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		  return new ResponseEntity<CustomResponse>(resp, HttpStatus.CREATED);
   }

}