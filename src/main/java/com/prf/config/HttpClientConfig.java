package com.prf.config;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
public class HttpClientConfig {

	private static final Logger log = LoggerFactory.getLogger(HttpClientConfig.class);

	@Value("${sap.odata.baseurl}")
	private String ODATA_BASEURL;

	@Value("${validation.xmlurl}")
	private String VALIDATION_XML;

	@Value("${sap.odata.auth}")
	private String ODATA_AUTH;

	private static final int HTTP_MAX_IDLE = 50;
	private static final int HTTP_KEEP_ALIVE = 30;
	private static final int HTTP_CONNECTION_TIMEOUT = 20;

	private static final int RETRY_INIT_INTERVAL = 1000;
	private static final int RETRY_MULTIPLIER = 2;
	private static final int RETRY_MAX_ATTEMPTS = 3;

	private static final Map<Class<? extends Throwable>, Boolean> RETRY_EXCEPTIONS = new HashMap<>();

	// Exceptions that will be retried
	static {
		RETRY_EXCEPTIONS.put(HttpClientErrorException.class, true);
		RETRY_EXCEPTIONS.put(HttpServerErrorException.class, true);
		RETRY_EXCEPTIONS.put(SocketTimeoutException.class, true);
	}
	
	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(RETRY_INIT_INTERVAL);
		backOffPolicy.setMultiplier(RETRY_MULTIPLIER);
		retryTemplate.setBackOffPolicy(backOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(RETRY_MAX_ATTEMPTS, RETRY_EXCEPTIONS, true, false);
		retryTemplate.setRetryPolicy(retryPolicy);

		return retryTemplate;
	}

	@Bean
	@Qualifier("oDataClient")
	public RestTemplate okhttp3Template() {

		log.info("Intantiating oData HttpClient...");

		RestTemplate restTemplate = new RestTemplate();
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		ConnectionPool okHttpConnectionPool = new ConnectionPool(HTTP_MAX_IDLE, HTTP_KEEP_ALIVE, TimeUnit.SECONDS);

		builder.connectionPool(okHttpConnectionPool);
		builder.connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
		builder.retryOnConnectionFailure(false);

		// Embeded okhttp3 client to rest template
		restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(builder.build()));
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(ODATA_BASEURL));

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

		interceptors.add(this.generateAuthInterceptor());

		restTemplate.setInterceptors(interceptors);

		log.info("oData HttpClient Instantiated...");

		return restTemplate;
	}

	@Bean
	@Qualifier("validateXMLClient")
	public RestTemplate okhttp3TemplateXML() {

		log.info("Intantiating XML HttpClient...");

		RestTemplate restTemplate = new RestTemplate();
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		ConnectionPool okHttpConnectionPool = new ConnectionPool(HTTP_MAX_IDLE, HTTP_KEEP_ALIVE, TimeUnit.SECONDS);

		builder.connectionPool(okHttpConnectionPool);
		builder.connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
		builder.retryOnConnectionFailure(false);

		// Embeded okhttp3 client to rest template
		restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(builder.build()));
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(VALIDATION_XML));

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

		interceptors.add(this.generateXMLHeadersInterceptor());

		restTemplate.setInterceptors(interceptors);
		

		log.info("XML HttpClient Instantiated...");

		return restTemplate;
	}

	private ClientHttpRequestInterceptor generateAuthInterceptor() {
		return new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {

				request.getHeaders().add(HttpHeaders.AUTHORIZATION, ODATA_AUTH);

				return execution.execute(request, body);
			}
		};
	}

	private ClientHttpRequestInterceptor generateXMLHeadersInterceptor() {
		return new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {

				request.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
				
				return execution.execute(request, body);
			}
		};
	}


}
