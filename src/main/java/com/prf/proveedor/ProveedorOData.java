package com.prf.proveedor;

import java.util.HashMap;
import java.util.Map;

import com.prf.common.ODataResponse;
import com.prf.sap.catalogs.Banco;

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
public class ProveedorOData {

	private static final Logger log = LoggerFactory.getLogger(ProveedorOData.class);
	
	private final String endpoint_clave_bancos = "/GET_CLAVE_BANSet?";

	private RestTemplate oDataClient;

	private RetryTemplate retryTemplate;

	@Autowired
	public ProveedorOData(@Qualifier("oDataClient") RestTemplate oDataClient, RetryTemplate retryTemplate) {
		this.oDataClient = oDataClient;
		this.retryTemplate = retryTemplate;
	}

	public void testOdataCall() {
		
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

	}
}
