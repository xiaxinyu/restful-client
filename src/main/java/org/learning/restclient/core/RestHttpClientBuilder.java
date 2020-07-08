package org.learning.restclient.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class RestHttpClientBuilder {

    public CloseableHttpClient acceptsUntrustedCertsHttpClient(String keyPath, String password) throws KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException, CertificateException, FileNotFoundException, IOException {

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(new FileInputStream(keyPath), password.toCharArray());

        // setup a Trust
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        TrustManager[] tms = tmf.getTrustManagers();

        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tms, new SecureRandom());

        HttpClientBuilder b = HttpClientBuilder.create();
        b.setSSLContext(sslContext);

        // don't check Hostnames, either.
        // -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if
        // you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        // -- need to create an SSL Socket Factory, to use our weakened "trust
        // strategy";
        // -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                .build();
        // now, we create connection-manager using our Registry.
        // -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        b.setConnectionManager(connMgr);

        // finally, build the HttpClient;
        // -- done!
        CloseableHttpClient client = b.build();

        return client;
    }
}
