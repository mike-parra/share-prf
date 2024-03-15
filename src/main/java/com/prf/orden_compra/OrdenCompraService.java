package com.prf.orden_compra;

import java.util.List;
import java.util.Optional;

import com.prf.factura.EntradaMercancia;
import com.prf.factura.Factura;
import com.prf.usuario.Usuario;

import org.springframework.dao.DuplicateKeyException;

public interface OrdenCompraService {
	 Optional <OrdenCompra> findByOrdenCompra(Integer orden_compra);
	 OrdenCompra obtenerOrdenCompraById(Integer id_oc);
		
	 List<OrdenCompra>listarOrdenCompra();
	 List<OrdenCompra> obtenerOrdenCompraByQuery(String query);
	 
	 List<OrdenCompra> obtenerEntradaMercanciaPorOrdenCompra(Integer ordenCompra);

 }
