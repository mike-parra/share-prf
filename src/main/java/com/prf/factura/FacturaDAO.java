package com.prf.factura;

import java.util.List;

import com.prf.complementos.Complemento;

public interface FacturaDAO {

	Integer crearFactura(Factura factura);

	List<Factura> listarFacturas(String rfc);

	Factura obtenerFacturaById(Integer id);

	Factura obtenerFacturaByUuid(String uuid);

	List<Factura> obtenerDocumentosRelacionados(List<String> uuids);

}
