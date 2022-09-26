package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Implementacion de un Cliente http
 * 
 * @author fernandopaz
 *
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	public ClientHttpRequestFactory clientHttpRequestFactory() {
		// Pooling connection manager con un maximo de 100 conexiones totales y 20 de
		// maximo a una ruta especifica
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);

		// Configuracion de la reques: si toma mas de 3000 ms falla la conexion
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(3000).setSocketTimeout(3000)
				.build();

		// El httpclient lo configuramos con el poolingConnectionManager y el
		// requestConfig
		// por defecto la estrategia de keep alive que se le esta pasando solo se fija
		// en el tiempo de la request
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setDefaultRequestConfig(requestConfig)
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());

	}

}
