package com.prf.approver;

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
public class UsuarioAprobador {
	private Integer idusuario;
	private String usuario;
	private String nivel_auth;
	private Integer idNivel_auth;
}
