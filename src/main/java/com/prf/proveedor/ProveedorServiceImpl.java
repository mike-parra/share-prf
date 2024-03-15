package com.prf.proveedor;

import java.util.ArrayList;
import java.util.List;

import com.prf.email.ProviderCredentialsMail;
import com.prf.perfil.Perfil;
import com.prf.usuario.Usuario;
import com.prf.usuario.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
		
	private static final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);
	
	private ProveedorDao proveedorDao;
	
	private ProviderCredentialsMail providerCredentialsMail;
	
	private UsuarioService usuarioService;
	
	private ProveedorOData proveedorOData;
	
	@Autowired
	public ProveedorServiceImpl (ProveedorDao proveedorDao, UsuarioService usuarioService, ProviderCredentialsMail providerCredentialsMail, ProveedorOData proveedorOData) {
		this.proveedorDao = proveedorDao;
		this.usuarioService = usuarioService;
		this.providerCredentialsMail = providerCredentialsMail;
		this.proveedorOData = proveedorOData;
	}

	@Override
	@Transactional
	public Integer crearProveedor(Proveedor proveedor) {
		
		Perfil perfil_proveedor = new Perfil();
		perfil_proveedor.setId_perfil(3);
		
		List<Perfil> array_perfil = new ArrayList<>();
		array_perfil.add(perfil_proveedor);
		
		String username = usuarioService.generateUsername(proveedor.getNombre_contacto(), proveedor.getApellido_contacto(), proveedor.getNombre_razon_social());
		
		log.debug("Generated username: {}",username);
		
		Usuario usuario_proveedor = Usuario.builder()
				.usuario(proveedor.getNombre_contacto().trim())
				.nombre(String.format("%s %s", proveedor.getNombre_contacto(), proveedor.getApellido_contacto()))
				.email(proveedor.getEmail_contacto())
				.password(usuarioService.generatePassayPassword(10))
				.perfiles(array_perfil)
				.build();
		
		Integer id_usuario = usuarioService.crearUsuario(usuario_proveedor);
		
		proveedor.setId_usuario(id_usuario);
		
		Integer id_proveedor = proveedorDao.crearProveedor(proveedor);
		
		this.guardarInformacionBancaria(id_proveedor,proveedor.getInformacion_bancaria());
		
		providerCredentialsMail.sendCredentialEmail(proveedor.getEmail_contacto(), usuario_proveedor.getUsuario(), usuario_proveedor.getPassword());
		
		return id_proveedor;
	}

	private void guardarInformacionBancaria(Integer id_proveedor, ArrayList<Informacion_Bancaria> informacion_bancaria) {
		
		informacion_bancaria.forEach(item -> item.setIdproveedor(id_proveedor));
		
		proveedorDao.guardarInformacionBancaria(informacion_bancaria);
	}

	@Override
	public Proveedor obtenerProveedorPorId(Integer id_proveedor) {
		
		Proveedor proveedor = proveedorDao.obtenerProveedorPorId(id_proveedor);
		
		return proveedor;
	}

	@Override
	public Proveedor obtenerProveedorPorIdUsuario(Integer id_usuario) {
		
		Proveedor proveedor = proveedorDao.obtenerProveedorPorIdUsuario(id_usuario);
		
		return proveedor;
	}

}
