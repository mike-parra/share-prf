package com.prf.orden_compra;


import java.util.List;
import java.util.Optional;

import com.prf.factura.EntradaMercancia;
import com.prf.factura.Factura;
import com.prf.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

	private OrdenCompraDAO ordenCompraDAO;
	
	@Autowired
	OrdenCompraServiceImpl(OrdenCompraDAO ordenCompraDAO) {
		this.ordenCompraDAO = ordenCompraDAO;
	}
	

	@Override
	public List<OrdenCompra> listarOrdenCompra() {
		return ordenCompraDAO.listarOrdenCompra();
	}
	
	@Override
	public Optional<OrdenCompra> findByOrdenCompra(Integer orden_compra) {
		return ordenCompraDAO.findByOrdenCompra(orden_compra);
	}
	
	@Override
	public OrdenCompra obtenerOrdenCompraById(Integer id_oc) {
		OrdenCompra oc= ordenCompraDAO.obtenerOrdenCompraById(id_oc);
		return oc;
	}
	
	@Override
	public List<OrdenCompra> obtenerOrdenCompraByQuery(String query) {
		// TODO Auto-generated method stub
		if(query.length() == 0) {
			return null;
		}
		return ordenCompraDAO.obtenerOrdenCompraByQuery(query);
	}

	@Override
	public List<OrdenCompra> obtenerEntradaMercanciaPorOrdenCompra(Integer ordenCompra) {
		return ordenCompraDAO.obtenerEntradaMercanciaPorOrdenCompra(ordenCompra);
	}
	

}
