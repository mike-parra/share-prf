package com.prf.perfil;

import java.util.List;


public interface PerfilDAO {


	//List<String> getAutoridadesPerfil(Integer idusuario);

	boolean isActive(Integer idperfil);

	List<Modulo> getModulos(Integer idusuario);

	List<Perfil> getPerfiles(Integer idusuario);

    void insertarPerfilesUsuario(int idusuario, List<Perfil> perfiles);
    
    void resetPerfilesUsuario(int idusuario);
    
    List<Perfil> getListaGeneralPerfiles();
}
