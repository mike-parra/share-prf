package com.prf.orden_compra;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor

public class OrdenCompra{
	private Integer id_oc;
	private Integer orden_compra;
	private String descripcion;
	private Integer importe;
	private String moneda;
	private String fecha_creacion;
	private String material_doc;
	private String documento_contable;
//	private List<EntradaMercancia> entradas_mercancia; 

	
}