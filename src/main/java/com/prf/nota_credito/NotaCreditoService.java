package com.prf.nota_credito;

import java.util.List;
import java.util.Map;

public interface NotaCreditoService {

	Integer agregarNotaCredito(int idusuario, NotaCredito nota_credito);

	List<NotaCredito> listarNotasCredito(int idusuario);

	List<NotaCredito> obtenerNotaCreditoPorFechaTimbrado(int idusuario, String fecha_timbrado);

	List<NotaCredito> obtenerNotaCreditoPorUuidFactura(int idusuario, String uuid_factura);

	void desactivarNotaCredito(Integer id_nota_credito);

	void activarNotaCredito(Integer id_nota_credito);

}
