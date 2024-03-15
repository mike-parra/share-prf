package com.prf.proveedor;

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
public class Informacion_Bancaria {
	private Integer idinformacion_bancaria;
	private Integer idproveedor;
	private String codigo_pais;
	private String banco_clave;
	private String banco_denominacion;
	private String cuenta_bancaria;
	private String referencia;
	private String swift;
	private String titular_cuenta;
	private String estatus;
	private String fecha_creacion;
	private String fecha_modificacion;
}
