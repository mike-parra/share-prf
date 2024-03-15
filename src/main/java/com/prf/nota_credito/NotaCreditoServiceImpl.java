package com.prf.nota_credito;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.prf.factura.Factura;
import com.prf.factura.FacturaService;
import com.prf.proveedor.Proveedor;
import com.prf.proveedor.ProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaCreditoServiceImpl implements NotaCreditoService {

	// AQUI VA LA LOGICA DE NEGOCIOS
	private NotaCreditoDAO note_creditDAO;

	private ProveedorService proveedorService;

	private FacturaService facturaService;

	@Autowired
	NotaCreditoServiceImpl(NotaCreditoDAO note_creditDAO, ProveedorService proveedorService,
			FacturaService facturaService) {
		this.note_creditDAO = note_creditDAO;
		this.proveedorService = proveedorService;
		this.facturaService = facturaService;
	}

	@Override
	public Integer agregarNotaCredito(int id_usuario, NotaCredito nota_credito) {

		Proveedor p = proveedorService.obtenerProveedorPorIdUsuario(id_usuario);
		if (!(nota_credito.getRfc_emisor().toLowerCase() != p.getRfc().toLowerCase())) {
			throw new InputMismatchException("Error en el RFC emisor");
		}

		if (!(verificarRfcReceptor(nota_credito))) {
			throw new InputMismatchException("Error en los RFC");
		}

		int idNotaCredito = this.note_creditDAO.agregarNotaCredito(nota_credito);

		return idNotaCredito;
	}

	@Override
	public List<NotaCredito> listarNotasCredito(int idusuario) {
		Proveedor p = this.proveedorService.obtenerProveedorPorIdUsuario(idusuario);
		return this.note_creditDAO.listarNotasCredito(p.getRfc());
	}

	@Override
	public void desactivarNotaCredito(Integer id_nota_credito) {
		this.note_creditDAO.desactivarNotaCredito(id_nota_credito);
	}

	@Override
	public void activarNotaCredito(Integer id_nota_credito) {
		this.note_creditDAO.activarNotaCredito(id_nota_credito);
	}

	@Override
	public List<NotaCredito> obtenerNotaCreditoPorFechaTimbrado(int idusuario, String fecha_timbrado) {
		Proveedor p = this.proveedorService.obtenerProveedorPorIdUsuario(idusuario);
		return this.note_creditDAO.obtenerNotaCreditoPorFechaTimbrado(p.getRfc(), fecha_timbrado);
	}

	@Override
	public List<NotaCredito> obtenerNotaCreditoPorUuidFactura(int idusuario, String uuid_factura) {
		Proveedor p = this.proveedorService.obtenerProveedorPorIdUsuario(idusuario);
		return this.note_creditDAO.obtenerNotaCreditoPorUuidFactura(p.getRfc(), uuid_factura);
	}

	public boolean verificarRfcReceptor(NotaCredito nota_credito) {
		Factura facturaRelacionada = this.facturaService.obtenerFacturaByUuid(nota_credito.getUuid_factura());
		String rfc_receptor = nota_credito.getRfc_receptor();

		Boolean validateRfcReceptor = Objects.nonNull(facturaRelacionada.getRfc_receptor().equals(rfc_receptor));

		return validateRfcReceptor;
	}

}
