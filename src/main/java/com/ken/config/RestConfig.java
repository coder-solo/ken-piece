package com.ken.config;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestConfig {

	@Bean("restTemplate")
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.getMessageConverters().add(new Jackson2HttpMessageConverter());

		return restTemplate;
	}

	@Bean("restTemplateDes")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	// 调过https校验访问url
	@Bean("restTemplateOutSsl")
	public RestTemplate buildRestTemplate(List<RequestHeaderInterceptor> interceptors) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		HttpComponentsClientHttpRequestFactory factory = new
				HttpComponentsClientHttpRequestFactory();
//		factory.setConnectionRequestTimeout(requestTimeout);
//		factory.setConnectTimeout(connectTimeout);
//		factory.setReadTimeout(readTimeout);
		// https
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, (X509Certificate[] x509Certificates, String s) -> true);
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory())
				.register("https", socketFactory).build();
		PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager(registry);
		phccm.setMaxTotal(200);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).setConnectionManager(phccm).setConnectionManagerShared(true).build();
		factory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(factory);
		List<ClientHttpRequestInterceptor> clientInterceptorList = new ArrayList<>();
		for (RequestHeaderInterceptor i : interceptors) {
			ClientHttpRequestInterceptor interceptor = i;
			clientInterceptorList.add(interceptor);
		}
		restTemplate.setInterceptors(clientInterceptorList);

		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000); // ms
		factory.setConnectTimeout(10000); // ms
		return factory;
	}

	public class Jackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		public Jackson2HttpMessageConverter() {
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.TEXT_PLAIN);
			setSupportedMediaTypes(mediaTypes);
		}
	}
}
