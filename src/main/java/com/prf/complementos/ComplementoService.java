package com.prf.complementos;

import java.util.List;



public interface ComplementoService {

	List<Complemento> listarComplementos(Integer idusuario);
	
	Integer crearComplemento(Complemento complemento, Integer idusuario);
	
	Complemento obtenerComplementoById(Integer id);

	List<Complemento> buscarComplementos(String search, Integer idusuario);

	

	
}
