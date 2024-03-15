package com.prf.orden_compra;

import java.util.List;
import java.util.Optional;

import com.prf.factura.EntradaMercancia;
import com.prf.factura.Factura;
import com.prf.usuario.Usuario;

public interface OrdenCompraDAO {
	OrdenCompra obtenerOrdenCompraById (Integer id_oc);
	Optional<OrdenCompra> findByOrdenCompra(Integer orden_compra);
	List<OrdenCompra> listarOrdenCompra();
	List<OrdenCompra> obtenerOrdenCompraByQuery(String query);
	List<OrdenCompra> obtenerEntradaMercanciaPorOrdenCompra(Integer orden_compra);
	
}
