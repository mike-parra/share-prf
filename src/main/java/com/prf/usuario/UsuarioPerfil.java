package com.prf.usuario;



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
public class UsuarioPerfil {

	private Integer idusuario;
	String usuario;
	String nombre; 
	String email;
	String img_perfil;
	String estatus;
	String perfil;
}
