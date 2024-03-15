package com.prf.factura;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FacturaService {

	Integer crearFactura(Factura factura,Integer idusuario);

	List<Factura> listarFacturas(int idusuario);

	Factura obtenerFacturaById(Integer id);
	
	Factura obtenerFacturaByUuid(String uuid);
	
	String subirFactura(MultipartFile factura);

	List<Factura> obtenerDocumentosRelacionados(List<String> uuids);
}
