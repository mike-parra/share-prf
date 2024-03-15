package com.prf.documento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.util.Optional;



@Service
public class DocumentoServiceImpl implements DocumentoService {
	@Autowired
	DocumentoDAO documentoDAO;
	

    @Override
    public Documento buscarDocumentosPorNombre(String nombre_documento) {
        return documentoDAO.buscarDocumentosPorNombre(nombre_documento);
    }

    @Override
    public Documento obtenerDocumentoPorId(Integer iddocumento) {
        return documentoDAO.obtenerDocumentoPorId(iddocumento);
    }

    @Override
    public int agregarDocumento(Documento documento)throws DuplicateKeyException {
    	int idDocumento = documentoDAO.agregarDocumento(documento);
    	return idDocumento;
    }

    @Override
    public void actualizarDocumento(Documento documento) {
    	documentoDAO.actualizarDocumento(documento);
    }

    @Override
    public void eliminarDocumento(Integer iddocumento) {
    	documentoDAO.eliminarDocumento(iddocumento);
    }

    @Override
    public List<Documento> listarDocumentos() {
        return documentoDAO.listarDocumentos();
    }
}
