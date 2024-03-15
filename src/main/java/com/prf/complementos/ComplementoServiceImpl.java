package com.prf.complementos;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import com.prf.factura.Factura;
import com.prf.factura.FacturaDAO;
import com.prf.proveedor.Proveedor;
import com.prf.proveedor.ProveedorController;
import com.prf.proveedor.ProveedorDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceImpl implements ComplementoService{

	private static final Logger log = LoggerFactory.getLogger(ComplementoService.class);
	
	@Autowired
	ComplementoDAO complementoDAO;
	
	@Autowired
	ProveedorDao proveedorDAO;
	
	@Autowired
	FacturaDAO facturaDAO;

	@Override
	public List<Complemento> listarComplementos(Integer idusuario) {
		Proveedor p = proveedorDAO.obtenerProveedorPorIdUsuario(idusuario);
		return complementoDAO.listarComplementos(p.getRfc());
	}	


	@Override
	public Integer crearComplemento(Complemento complemento, Integer idusuario) {
		// TODO Auto-generated method stub
		
		Proveedor p = proveedorDAO.obtenerProveedorPorIdUsuario(idusuario);
		if(!(complemento.getRfc_emisor().toLowerCase().equals(p.getRfc().toLowerCase()))){
			log.info("No paso el primer iff");
			throw new InputMismatchException("RFC mismatch");
		}
		List<Factura> facturas = facturaDAO.obtenerDocumentosRelacionados(complemento.getUuid_documentos_relacionados());
		if(!VerificarDocumentosRelacionados(facturas, complemento.getUuid_documentos_relacionados())) {
			log.info("No paso el segundo iff");
			throw new InputMismatchException("Complement uuid mismatch");
		}
		
		return complementoDAO.crearComplemento(complemento);
		
	}


	@Override
	public Complemento obtenerComplementoById(Integer id) {
	
		return complementoDAO.obtenerComplementoById(id);
	}


	@Override
	public List<Complemento> buscarComplementos(String search, Integer idusuario) {
		try {
			Proveedor p = proveedorDAO.obtenerProveedorPorIdUsuario(idusuario);
			return complementoDAO.buscarComplementos(search,p.getRfc());
		}catch(Exception e) 
		{
			log.info(e.toString());
			return complementoDAO.buscarComplementos(search,null);
		}
		
		
	}
	
	
	public boolean VerificarDocumentosRelacionados(List<Factura> documentosRelacionados,List<String> uids_complemento){
		
		
		List<String> ListaUUid = 
				documentosRelacionados.stream()
				.map(Factura::getUuid)
				.collect(Collectors.toList());
		 
		return ListaUUid.size() == uids_complemento.size() && ListaUUid.stream().allMatch(uids_complemento::contains);
		
	}


	


	

	

	

}