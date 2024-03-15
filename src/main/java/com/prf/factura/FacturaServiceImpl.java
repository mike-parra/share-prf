package com.prf.factura;

import java.util.List;

import com.prf.complementos.Complemento;
import com.prf.complementos.ComplementoService;
import com.prf.proveedor.Proveedor;
import com.prf.proveedor.ProveedorDao;
import com.prf.proveedor.ProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class FacturaServiceImpl implements FacturaService {
	
	private FacturaDAO facturaDAO;

	private ProveedorService proveedorService;
	 
    @Autowired
    FacturaServiceImpl (FacturaDAO facturaDAO, ProveedorService proveedorService){
    	this.facturaDAO = facturaDAO;
    	this.proveedorService = proveedorService;
    }
	
	@Override
	public Integer crearFactura(Factura factura, Integer idusuario) {
		Proveedor p = proveedorService.obtenerProveedorPorIdUsuario(idusuario);
		if(factura.getRfc_emisor().toLowerCase() != p.getRfc().toLowerCase() ){
			return null;
		}
		return facturaDAO.crearFactura(factura);
	}

	@Override
	public List<Factura> listarFacturas(int idusuario) {
		Proveedor p = proveedorService.obtenerProveedorPorIdUsuario(idusuario);
		if(p == null) {
			return null;
		}
		return facturaDAO.listarFacturas(p.getRfc());
	}

	@Override
	public Factura obtenerFacturaById(Integer id) {
		return facturaDAO.obtenerFacturaById(id);
	}

	@Override
	public Factura obtenerFacturaByUuid(String uuid) {
		return facturaDAO.obtenerFacturaByUuid(uuid);
	}

	@Override
	public String subirFactura(MultipartFile factura) {
		// TODO generar service de s3
		return "aws-link";
	}

	@Override
	public List<Factura> obtenerDocumentosRelacionados(List<String> uuids) {
		// TODO Auto-generated method stub
		return facturaDAO.obtenerDocumentosRelacionados(uuids);
	}

}
