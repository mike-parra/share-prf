package com.prf.complementos;

import java.util.List;


public interface ComplementoDAO {
	List<Complemento> listarComplementos(String rfc);
	
	Integer crearComplemento(Complemento complemento);

	Complemento obtenerComplementoById(Integer id);

	List<Complemento> buscarComplementos(String search, String rfc);

	

}
