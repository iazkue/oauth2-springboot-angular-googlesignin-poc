package com.aghairsalon.resourceserver.controller;

import com.aghairsalon.resourceserver.services.PruebaRsrcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceController.class)
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PruebaRsrcService pruebaRsrcService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUserEndpoint() throws Exception {
        mockMvc.perform(get("/resource/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hola user"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void testAdminEndpoint() throws Exception {
        mockMvc.perform(get("/resource/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hola Mr.admin"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testPruebaEndpoint() throws Exception {
        mockMvc.perform(get("/resource/prueba"))
                .andExpect(status().isOk());
    }
}
