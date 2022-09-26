package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Implementacion de un Cliente http non blocking
 * 
 * @author fernandopaz
 *
 */
//@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

	public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
		// En el caso non blocking hay que especificar la catidad de theads soportados
		final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(
				IOReactorConfig.custom().setConnectTimeout(3000).setIoThreadCount(4).setSoTimeout(3000).build());

		// Pooling connection manager con un maximo de 100 conexiones totales y 20 de
		// maximo a una ruta especifica
		final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
				ioReactor);
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(100);

		// El httpclient lo configuramos con el poolingConnectionManager y el //
		// requestConfig
		CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(connectionManager)
				.build();

		return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		try {
			restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		} catch (IOReactorException e) {
			e.printStackTrace();
		}

	}

}
