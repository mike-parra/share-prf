package com.prf.proveedor;

import java.util.ArrayList;

public interface ProveedorDao {

	Integer crearProveedor(Proveedor proveedor);

	Proveedor obtenerProveedorPorId(Integer idproveedor);

	void guardarInformacionBancaria(ArrayList<Informacion_Bancaria> informacion_bancaria);

	Proveedor obtenerProveedorPorIdUsuario(Integer idusuario);

}
