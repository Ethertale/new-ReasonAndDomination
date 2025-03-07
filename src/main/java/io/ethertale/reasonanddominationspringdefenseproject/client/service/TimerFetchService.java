package io.ethertale.reasonanddominationspringdefenseproject.client.service;

import io.ethertale.reasonanddominationspringdefenseproject.client.TimerClient;
import io.ethertale.reasonanddominationspringdefenseproject.client.dto.TimerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerFetchService {

    private final TimerClient timerClient;

    @Autowired
    public TimerFetchService(TimerClient timerClient) {
        this.timerClient = timerClient;
    }

    public TimerDTO getTimer(String type) {
        return timerClient.getTimer(type);
    }
}
