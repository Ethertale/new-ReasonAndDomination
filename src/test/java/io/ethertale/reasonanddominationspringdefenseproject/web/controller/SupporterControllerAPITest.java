package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SupporterController.class)
class SupporterControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    void getUnauthenticatedRequestToDatabase_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/support-us");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());

    }
    @Test
    void getRequestToSupportUsEndpoint_shouldReturnSupportUsView() throws Exception {

        MockHttpServletRequestBuilder request = get("/support-us");
        //TODO CHECK WHY 3XX REDIRECT INSTEAD OF 2XX SUCCESSFUL
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("supporterPage"))
                .andExpect(model().attributeExists("user"));
    }
}