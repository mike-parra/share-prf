package com.prf.documento;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;


public interface DocumentoService {
	Documento buscarDocumentosPorNombre(String nombre_documento);
	    Documento obtenerDocumentoPorId(Integer iddocumento);
	    void actualizarDocumento(Documento documento);
	    void eliminarDocumento(Integer iddocumento);
	    List<Documento> listarDocumentos();
		int agregarDocumento(Documento documento) throws DuplicateKeyException;

}