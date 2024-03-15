package com.prf.approver;

import java.util.List;

public interface AprobadorService {

	//Lista General
	List<UsuarioAprobador> listarUsuarioAprovadr();
	
	// Usuarios prospectos
	List<UsuarioAprobador> listarUsuariosProspectos(String search);
	
	// Creacion y edicion.
	void editarNivelApprover(Integer id,Integer idnivel);

	// Borrar aprobador.
	void borrarApproverUsuarioById(Integer id);
	
	// Listar Niveles de Auth
	List<Nivel_Auth> listarNivelesAuth();


}
