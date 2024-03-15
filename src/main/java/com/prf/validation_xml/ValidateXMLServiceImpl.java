package com.prf.validation_xml;

import java.util.List;
import java.util.stream.Collectors;

import com.prf.validation_xml.ValidationXMLResponse.DetailHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.core.util.Json;

@Service
public class ValidateXMLServiceImpl implements ValidateXMLService{

	private static final Logger log = LoggerFactory.getLogger(ValidateXMLServiceImpl.class);

	private RestTemplate validateXMLClient;

	private RetryTemplate retryTemplate;
	
	@Autowired
	public ValidateXMLServiceImpl(@Qualifier("validateXMLClient") RestTemplate validateXMLClient, RetryTemplate retryTemplate) {
		this.validateXMLClient = validateXMLClient;
		this.retryTemplate = retryTemplate;
	}

	@Override
	public ValidationXMLResponse validarXML(ValidationXMLRequest xml) {
		
		StringBuilder endpoint = new StringBuilder();
		
		HttpEntity<ValidationXMLRequest> body = new HttpEntity<>(xml);

		ResponseEntity<ValidationXMLResponse> resp = this.retryTemplate.execute(arg -> this.validateXMLClient
				.exchange(endpoint.toString(), HttpMethod.POST, body, ValidationXMLResponse.class ) );

		log.debug("Response Json: {}",Json.pretty(resp));
		
		if (resp.getStatusCode() != HttpStatus.OK) 
			throw new HttpServerErrorException(resp.getStatusCode(), "Upstream server error");
		
		if (!resp.getBody().getStatus().equalsIgnoreCase("success")) 
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Invalid XML validation request");
		
		ValidationXMLResponse validate_xml = resp.getBody();
		
		Boolean invalid_xml = validate_xml.getDetail().stream().anyMatch(this::validateDetail);
		
		if (invalid_xml) {
			List<DetailHeader> error_details = validate_xml.getDetail().stream().filter(this::validateDetail).collect(Collectors.toList());
			validate_xml.setDetail(error_details);
			validate_xml.setStatus("error");
		}
		
		return validate_xml;
	}

	private boolean validateDetail(DetailHeader detailheader) {
				 
		// if type is different from 1 (information) means error (0) or warning (2)
		return detailheader.getDetail().get(0).getType() != 1; 
	}
	
}
