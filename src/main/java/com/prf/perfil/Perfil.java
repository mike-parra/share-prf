package com.prf.perfil;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Perfil{
	
	private int id_perfil; 
	private String descripcion;
	private String estatus;
	private String codigo;
	private List<Modulo> modulos;
	
}
