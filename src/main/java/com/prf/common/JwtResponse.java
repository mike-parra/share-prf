package com.prf.common;

import java.util.List;

import com.prf.perfil.Modulo;
import com.prf.perfil.Perfil;



public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> permisos;
	private String nombre;
	private String imagen_perfil;
	private String email_address;
	private List<Modulo> modulos;
	private List<Perfil> perfiles;

	public JwtResponse(String accessToken, Long id, String username, List<String> permisos, String nombre, String imgString, 
			String email_address , List<Modulo> modulos, List<Perfil> perfiles) {
		this.token = accessToken;
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.permisos = permisos;
		this.imagen_perfil = imgString;
		this.email_address = email_address;
		this.modulos = modulos;
		this.perfiles = perfiles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getPermisos() {
		return permisos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen_perfil() {
		return imagen_perfil;
	}

	public void setImagen_perfil(String imagen_perfil) {
		this.imagen_perfil = imagen_perfil;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setRoles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	
}
