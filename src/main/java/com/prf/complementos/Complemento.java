package com.prf.complementos;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Complemento {
 
	private int id_complemento;
	private String uuid;
	private String fecha_emision;
	private String fecha_timbrado;
	private Double monto;
	private String fecha_pago;
	private String moneda;
	//private String metodo_pago;
	private String razonsocial_emisor;
	private String rfc_emisor;
	private String razonsocial_receptor;
	private String rfc_receptor;
	private String forma_pago;
	private Double tipo_cambio;
    private Double tasa_iva;
    private String estatus;
    private List<String> uuid_documentos_relacionados;
	
}
