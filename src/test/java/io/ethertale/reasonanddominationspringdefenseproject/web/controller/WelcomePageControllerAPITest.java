package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = WelcomePageController.class)
class WelcomePageControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRequestToWelcomePageEndpoint_shouldReturnWelcomePage() throws Exception {

        MockHttpServletRequestBuilder request = get("/");

        mockMvc.perform(request);
    }
}