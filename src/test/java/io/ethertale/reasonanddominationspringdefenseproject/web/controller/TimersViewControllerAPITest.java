package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.client.dto.TimerDTO;
import io.ethertale.reasonanddominationspringdefenseproject.client.service.TimerFetchService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TimersViewController.class)
class TimersViewControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TimerFetchService timerFetchService;

    @Test
    void callTimersPage_GET_returnsTimersPage() throws Exception {
        TimerDTO raid20Timer = new TimerDTO("RAID_20", LocalDateTime.now().plusDays(3));
        TimerDTO raid40Timer = new TimerDTO("RAID_40", LocalDateTime.now().plusDays(7));
        TimerDTO worldBossTimer = new TimerDTO("WORLD_BOSS", LocalDateTime.now().plusDays(5));

        when(timerFetchService.getTimer("RAID_20")).thenReturn(raid20Timer);
        when(timerFetchService.getTimer("RAID_40")).thenReturn(raid40Timer);
        when(timerFetchService.getTimer("WORLD_BOSS")).thenReturn(worldBossTimer);

        MockHttpServletRequestBuilder request = get("/timers")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                )));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("timers"))
                .andExpect(model().attribute("worldBossTimer", worldBossTimer.getEndTime()))
                .andExpect(model().attribute("raid20Timer", raid20Timer.getEndTime()))
                .andExpect(model().attribute("raid40Timer", raid40Timer.getEndTime()));
    }
    @Test
    void getUnauthenticatedRequestToTimers_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/timers");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

}