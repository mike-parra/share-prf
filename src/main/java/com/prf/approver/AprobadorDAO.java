package com.prf.approver;

import java.util.List;

public interface AprobadorDAO {

	//Lista General
		List<UsuarioAprobador> listarUsuarioAprovadr();
		
		// Usuarios prospectos
		List<UsuarioAprobador> listarUsuariosProspectos(String search);
		
		// Creacion y edicion.
		void editarNivelApprover(Integer id,Integer idnivel);

		// Borrar aprobador.
		void borrarApproverUsuarioById(Integer id);
		
		// Listar niveles. 
		List<Nivel_Auth> listarNivelesAuth();
}
