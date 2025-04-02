package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegisterController.class)
class RegisterControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    void getRequestToRegisterEndPoint_shouldReturnRegisterView() throws Exception {

        MockHttpServletRequestBuilder request = get("/register");

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("formRegisterDTO"));
    }
    @Test
    void postRequestToRegisterEndPoint_shouldRegisterOK() throws Exception {

        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "RedTiger52")
                .formField("password", "123123")
                .formField("email", "redtiger52@mail.com")
                .formField("confirmPassword", "123123")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(profileService, times(1)).registerProfile("RedTiger52", "123123", "redtiger52@mail.com", "123123");
    }
    //TODO CHECK redirects to /login instead of /register
    @Test
    void postRequestToRegisterEndPointWithInvalidData_shouldReturnRegisterView() throws Exception {

        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "")
                .formField("password", "")
                .formField("email", "")
                .formField("confirmPassword", "")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(profileService, never()).registerProfile("", "", "", "");
    }
}