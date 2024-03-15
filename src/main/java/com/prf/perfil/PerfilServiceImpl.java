package com.prf.perfil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilServiceImpl implements PerfilService {

	private PerfilDAO perfilDAO;
	
	@Autowired
	public PerfilServiceImpl(PerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}
	
	/*
	 * @Override public List<String> getAutoridadesPerfil(Integer idusuario) {
	 * return perfilDAO.getAutoridadesPerfil(idusuario); }
	 */

	@Override
	public boolean isActive(Integer idperfil) {
		return perfilDAO.isActive(idperfil);
	}

	@Override
	public List<Modulo> getModulos(Integer idusuario) {
		return perfilDAO.getModulos(idusuario);
	}

	@Override
	public List<Perfil> getPerfiles(Integer idusuario) {
		return perfilDAO.getPerfiles(idusuario);
	}

	@Override
	public void insertarPerfilesUsuario(int idusuario, List<Perfil> perfiles) {
		perfilDAO.insertarPerfilesUsuario(idusuario, perfiles);
	}

	@Override
	public void resetPerfilesUsuario(int idusuario) {
		perfilDAO.resetPerfilesUsuario(idusuario);
	}

	@Override
	public List<Perfil> getListaGeneralPerfiles() {

		return perfilDAO.getListaGeneralPerfiles();
	}

}
