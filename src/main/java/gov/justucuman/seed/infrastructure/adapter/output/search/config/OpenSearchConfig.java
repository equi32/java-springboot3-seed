package gov.justucuman.seed.infrastructure.adapter.output.search.config;


import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {
    
    @Value("${opensearch.scheme:http}") // Default to http if not specified
    private String scheme;

    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.port:9200}") // Default port
    private int port;

    @Value("${opensearch.username}")
    private String username;

    @Value("${opensearch.password}")
    private String password;

    @Bean
    public OpenSearchClient openSearchClient() {
        final HttpHost httpHost = new HttpHost(scheme, host, port);
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(httpHost),
                new UsernamePasswordCredentials(username, password.toCharArray()));

        ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(httpHost);
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        );

        OpenSearchTransport transport = builder.build();
        return new OpenSearchClient(transport);
    }
}
