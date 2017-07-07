package com.raytrex;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class TomcatConfig {

//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//		tomcat.addAdditionalTomcatConnectors(createSslConnector());
//		return tomcat;
//	}
//
//	private Connector createSslConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//		try {
//			ClassPathResource classLoader = new ClassPathResource("keystore.p12");
//			File keystore = classLoader.getFile();
//			File truststore = new ClassPathResource("keystore").getFile();
//			connector.setScheme("https");
//			connector.setSecure(true);
//			connector.setPort(443);
//			protocol.setSSLEnabled(true);
//			protocol.setKeystoreFile(keystore.getAbsolutePath());
//			protocol.setKeystorePass("1q2w3e4r");
//			protocol.setTruststoreFile(truststore.getAbsolutePath());
//			protocol.setTruststorePass("1q2w3e4r");
//			protocol.setKeyAlias("apitester");
//			return connector;
//		} catch (Exception ex) {
//			throw new IllegalStateException(
//					"can't access keystore: [" + "keystore" + "] or truststore: [" + "keystore" + "]", ex);
//		}
//	}
}
