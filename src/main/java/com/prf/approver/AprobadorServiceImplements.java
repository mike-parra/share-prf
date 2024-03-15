package com.prf.approver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AprobadorServiceImplements implements AprobadorService {
	
	
	private AprobadorDAO aprobadorDAO;
	
	@Autowired
	public AprobadorServiceImplements(AprobadorDAO aprobadorDAO) {
		this.aprobadorDAO = aprobadorDAO;
	}
	
	@Override
	public List<UsuarioAprobador> listarUsuarioAprovadr() {
		return aprobadorDAO.listarUsuarioAprovadr();
	}

	@Override
	public List<UsuarioAprobador> listarUsuariosProspectos(String search) {
		return aprobadorDAO.listarUsuariosProspectos(search);
	}

	@Override
	public void editarNivelApprover(Integer id,Integer idnivel) {
		aprobadorDAO.editarNivelApprover(id,idnivel);
	}

	@Override
	public void borrarApproverUsuarioById(Integer  id) {
		aprobadorDAO.borrarApproverUsuarioById(id);
	}

	@Override
	public List<Nivel_Auth> listarNivelesAuth() {
		return aprobadorDAO.listarNivelesAuth();
	}

}
