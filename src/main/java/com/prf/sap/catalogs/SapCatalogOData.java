package com.prf.sap.catalogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prf.common.ODataResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.core.util.Json;

@Repository
public class SapCatalogOData {
	
	private static final Logger log = LoggerFactory.getLogger(SapCatalogOData.class);

	private final String endpoint_clave_bancos = "/GET_CLAVE_BANSet?";
	
	private final String endpoint_paises = "/GET_PAISESSet?";

	private RestTemplate oDataClient;

	private RetryTemplate retryTemplate;
	
	@Autowired
	public SapCatalogOData(@Qualifier("oDataClient") RestTemplate oDataClient, RetryTemplate retryTemplate) {
		this.oDataClient = oDataClient;
		this.retryTemplate = retryTemplate;
	}

	public List<Banco> listClaveBanco() {
		StringBuilder endpoint = new StringBuilder(this.endpoint_clave_bancos);

		Map<String, Object> params = new HashMap<>();
		
		endpoint.append("$filter={filter}");
		params.put("filter", "Pais eq 'MX'");
		
		endpoint.append("&sap-client={sap-client}");
		params.put("sap-client", "200");
		
		endpoint.append("&$format={format}");
		params.put("format", "json");	

		ResponseEntity<ODataResponse<Banco>> resp = this.retryTemplate.execute(arg -> this.oDataClient
				.exchange(endpoint.toString(), HttpMethod.GET, null, new ParameterizedTypeReference <ODataResponse<Banco>>() {}, params) );

		log.debug("Response Object: {}",resp.getBody().getD().getResults().get(0).toString() );
		log.debug("Response Json: {}",Json.pretty(resp.getBody()));
		
		return resp.getBody().getD().getResults();
	}

	public List<Pais> listPaises() {
		StringBuilder endpoint = new StringBuilder(this.endpoint_paises);

		Map<String, Object> params = new HashMap<>();
		
		
		endpoint.append("&sap-client={sap-client}");
		params.put("sap-client", "200");
		
		endpoint.append("&$format={format}");
		params.put("format", "json");	

		ResponseEntity<ODataResponse<Pais>> resp = this.retryTemplate.execute(arg -> this.oDataClient
				.exchange(endpoint.toString(), HttpMethod.GET, null, new ParameterizedTypeReference <ODataResponse<Pais>>() {}, params) );

		log.debug("Response Object: {}",resp.getBody().getD().getResults().get(0).toString() );
		log.debug("Response Json: {}",Json.pretty(resp.getBody()));
		
		return resp.getBody().getD().getResults();
	}

}
