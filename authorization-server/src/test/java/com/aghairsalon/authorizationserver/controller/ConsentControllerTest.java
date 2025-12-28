package com.aghairsalon.authorizationserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsentController.class)
public class ConsentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisteredClientRepository registeredClientRepository;

    @MockBean
    private OAuth2AuthorizationConsentService authorizationConsentService;

    @Test
    @WithMockUser("mnick")
    public void consent_NoPreviousConsent_ReturnsConsentView() throws Exception {
        String clientId = "client";
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080")
                .scope("read")
                .build();

        when(registeredClientRepository.findByClientId(clientId)).thenReturn(registeredClient);
        when(authorizationConsentService.findById(eq(registeredClient.getId()), eq("mnick"))).thenReturn(null);

        mockMvc.perform(get("/oauth2/consent")
                .param(OAuth2ParameterNames.CLIENT_ID, clientId)
                .param(OAuth2ParameterNames.SCOPE, "read write")
                .param(OAuth2ParameterNames.STATE, "state123"))
                .andExpect(status().isOk())
                .andExpect(view().name("consent"))
                .andExpect(model().attribute("clientId", clientId))
                .andExpect(model().attribute("state", "state123"))
                .andExpect(model().attribute("principalName", "mnick"))
                .andExpect(model().attributeExists("scopes"));
    }

    @Test
    @WithMockUser("mnick")
    public void consent_WithPreviousConsent_ReturnsConsentView() throws Exception {
        String clientId = "client";
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080")
                .scope("read")
                .build();

        OAuth2AuthorizationConsent currentConsent = OAuth2AuthorizationConsent.withId(registeredClient.getId(), "mnick")
                .scope("read")
                .build();

        when(registeredClientRepository.findByClientId(clientId)).thenReturn(registeredClient);
        when(authorizationConsentService.findById(eq(registeredClient.getId()), eq("mnick")))
                .thenReturn(currentConsent);

        mockMvc.perform(get("/oauth2/consent")
                .param(OAuth2ParameterNames.CLIENT_ID, clientId)
                .param(OAuth2ParameterNames.SCOPE, "read write")
                .param(OAuth2ParameterNames.STATE, "state123"))
                .andExpect(status().isOk())
                .andExpect(view().name("consent"))
                .andExpect(model().attribute("clientId", clientId))
                .andExpect(model().attributeExists("scopes"))
                .andExpect(model().attributeExists("previouslyApprovedScopes"));
    }
}
