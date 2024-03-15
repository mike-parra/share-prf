package com.prf.proveedor;

public interface ProveedorService {

	Integer crearProveedor(Proveedor proveedor);

	Proveedor obtenerProveedorPorId(Integer id_proveedor);

	Proveedor obtenerProveedorPorIdUsuario(Integer id_usuario);

}
