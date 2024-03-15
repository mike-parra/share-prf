package com.prf.sap.catalogs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SapCatalogServiceImpl implements SapCatalogService {
	
	private static final Logger log = LoggerFactory.getLogger(SapCatalogServiceImpl.class);

	private SapCatalogOData catalogOData;
	
	@Autowired
	public SapCatalogServiceImpl(SapCatalogOData catalogOData) {
		this.catalogOData = catalogOData;
	}

	@Override
	public List<Banco> listClaveBanco() {
		
		return catalogOData.listClaveBanco();
	}

	@Override
	public List<Pais> listPaises() {
		return catalogOData.listPaises();
	}

}
