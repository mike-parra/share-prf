package com.prf.usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prf.perfil.Modulo;
import com.prf.perfil.Perfil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// todas las variables van aqui 

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
	private Integer id_usuario;
	String usuario;
	String nombre; 
	String email;
	@JsonIgnore
	String password;
	String img_perfil;
	String s3_directory;
	Boolean reset_password;
	String estatus;
	List<String> permisos;
	List<Modulo> modulos;
	List<Perfil> perfiles; 
}

