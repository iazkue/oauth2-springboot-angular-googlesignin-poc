package com.aghairsalon.authorizationserver.service;

import com.aghairsalon.authorizationserver.dto.CreateClientDTO;
import com.aghairsalon.authorizationserver.dto.MessageDTO;
import com.aghairsalon.authorizationserver.entity.Client;
import com.aghairsalon.authorizationserver.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    @Test
    void create_Success() {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setClientId("client");
        dto.setClientSecret("secret");
        dto.setAuthenticationMethods(Set.of(ClientAuthenticationMethod.CLIENT_SECRET_BASIC));
        dto.setAuthorizationGrantTypes(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE));
        dto.setRedirectUris(Set.of("http://localhost:8080"));
        dto.setScopes(Set.of("read"));
        dto.setRequireProofKey(true);

        when(passwordEncoder.encode("secret")).thenReturn("encodedSecret");
        when(clientRepository.save(any(Client.class))).thenAnswer(i -> i.getArguments()[0]);

        MessageDTO result = clientService.create(dto);

        assertEquals("Cliente client guardado", result.message());
    }

    @Test
    void findByClientId_Success() {
        Client client = Client.builder()
                .clientId("client")
                .clientSecret("secret")
                .authenticationMethods(Set.of(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
                .authorizationGrantTypes(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE))
                .redirectUris(Set.of("http://localhost:8080"))
                .scopes(Set.of("read"))
                .requireProofKey(true)
                .build();

        when(clientRepository.findByClientId("client")).thenReturn(Optional.of(client));

        RegisteredClient result = clientService.findByClientId("client");

        assertNotNull(result);
        assertEquals("client", result.getClientId());
    }

    @Test
    void findByClientId_NotFound_ThrowsException() {
        when(clientRepository.findByClientId("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.findByClientId("unknown"));
    }

    @Test
    void findById_Success() {
        // The implementation uses findByClientId logic searching by ID string
        Client client = Client.builder()
                .clientId("client")
                .clientSecret("secret")
                .authenticationMethods(Set.of(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
                .authorizationGrantTypes(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE))
                .redirectUris(Set.of("http://localhost:8080"))
                .scopes(Set.of("read"))
                .requireProofKey(true)
                .build();

        when(clientRepository.findByClientId("client")).thenReturn(Optional.of(client));

        RegisteredClient result = clientService.findById("client");

        assertNotNull(result);
        assertEquals("client", result.getClientId());
    }
}
