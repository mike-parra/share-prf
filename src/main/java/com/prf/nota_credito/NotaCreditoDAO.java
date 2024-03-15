package com.prf.nota_credito;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public interface NotaCreditoDAO {

	Integer agregarNotaCredito(NotaCredito notaCredito);

	List<NotaCredito> listarNotasCredito(String rfc);

	List<NotaCredito> obtenerNotaCreditoPorFechaTimbrado(String rfc, String fecha_timbrado);

	List<NotaCredito> obtenerNotaCreditoPorUuidFactura(String rfc, String uuid_factura);

	void desactivarNotaCredito(Integer id_nota_credito);

	void activarNotaCredito(Integer id_nota_credito);

}
