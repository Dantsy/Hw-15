package ru.otus.services;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;

@Service
public class SlaveService {

    @Value("${server.truststore.store}")
    private Resource trustStore;

    @Value("${server.truststore.password}")
    private String trustStorePassword;

    @Value("${service.slave-service-test-port}")
    private String slaveServicePort;

    @Value("${service.slave-service-test-path}")
    private String slaveServicePath;

    public String getSlaveServiceResponse() throws Exception {
        File truststoreFile = new File(trustStore.getURI().getPath());

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(truststoreFile, trustStorePassword.toCharArray())
                .build();

        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);

        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory).build();

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate template = new RestTemplate(requestFactory);

        ResponseEntity<String> result =
                template.getForEntity("https://localhost:%s/%s".formatted(slaveServicePort, slaveServicePath), String.class);
        return result.getBody();
    }

}
