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
public class Expediente {
	private Integer idexpediente;
	private Integer idproveedor;
	private Integer iddocumento;
	private String nombre_archivo;
	private String path;
	private String fecha_creacion;
	private String fecha_modificacion;
}
