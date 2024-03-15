package com.prf.proveedor;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

	private Integer id_proveedor;
	private Integer id_usuario;
	private String tratamiento;
	private String nombre_razon_social;
	private String rfc;
	private String ramo_industrial_clave;
	private String ramo_industrial_denominacion;
	private String calle;
	private Integer num_exterior;
	private Integer num_interior;
	private String colonia;
	private Integer codigo_postal;
	private String ciudad_poblacion;
	private String estado_region_clave;
	private String estado_region_denominacion;
	private String pais_clave;
	private String pais_denominacion;
	private String nombre_contacto;
	private String apellido_contacto;
	private String email_contacto;
	private Integer telefono_contacto;
	private String estatus;
	private String fecha_creacion;
	private String fecha_modificacion;
	private ArrayList<Informacion_Bancaria> informacion_bancaria;
	private ArrayList<Expediente> expediente;

}
