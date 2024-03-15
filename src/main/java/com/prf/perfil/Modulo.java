package com.prf.perfil;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Modulo{
	private Integer id_modulo;
	private String descripcion; 
	private String codigo; 
	private List<Permiso> permisos;
}
