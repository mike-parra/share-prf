package com.prf.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prf.perfil.Modulo;
import com.prf.perfil.Perfil;
import com.prf.usuario.Usuario;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;
  
  private String img_perfil;

  private String nombre;
  


private List<Modulo> modulos;
  
private List<Perfil> perfiles;
  

@JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password, String nombre, String img_perfil,
      Collection<? extends GrantedAuthority> authorities,List<Modulo> modulos, List<Perfil> perfiles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.nombre = nombre;
    this.img_perfil = img_perfil;
    this.authorities = authorities;
    this.modulos = modulos;
    this.perfiles = perfiles;
  }

  public static UserDetailsImpl build(Usuario user) {
    List<GrantedAuthority> authorities = user.getPerfiles().stream()
      .map(perfil -> new SimpleGrantedAuthority(perfil.getCodigo()))
      .collect(Collectors.toList());
    
    
	 // List<GrantedAuthority> authorities = new ArrayList<>();
	  
    return new UserDetailsImpl(
        user.getId_usuario().longValue(), 
        user.getUsuario(), 
        user.getEmail(),
        user.getPassword(), 
        user.getNombre(),
        user.getImg_perfil(),
        authorities,
        user.getModulos(),
        user.getPerfiles());
  }

  // String accessToken, Long id, String username, List<String> permisos, String nombre, String imgString, 
		//String email_address , List<Modulo> modulos, List<Perfil> perfiles
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
  
  public String getImg_perfil() {
	return img_perfil;
}

public void setImg_perfil(String img_perfil) {
	this.img_perfil = img_perfil;
}

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
  
  public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
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

public void setPerfiles(List<Perfil> perfiles) {
	this.perfiles = perfiles;
}
}