package com.prf.nota_credito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class NotaCredito {
	private Integer id_nota_credito;
	private String uuid_factura;
	private String moneda;
	private String tipo_cambio;
	private Double monto;
	private String fecha_timbrado;
	private String metodo_pago;
	private String forma_pago;
	private String fecha_creacion;
	private String fecha_modificacion;
	private String estatus;
	private String uuid_nota_credito;
	private String rfc_emisor;
	private String rfc_receptor;
}
