package com.aghairsalon.resourceserver.config;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class TestSecurityConfig {

    @Bean
    public NimbusJwtDecoder jwtDecoder() throws Exception {
        // create a simple RSA JWK for tests; keep the public key available for the
        // decoder
        RSAKey rsaJwk = new RSAKeyGenerator(2048)
                .keyID("test-key")
                .generate();

        // build a decoder from the public key
        return NimbusJwtDecoder.withPublicKey(rsaJwk.toRSAPublicKey()).build();
    }
}
