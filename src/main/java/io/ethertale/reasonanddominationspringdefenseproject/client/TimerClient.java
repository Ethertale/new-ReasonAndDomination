package io.ethertale.reasonanddominationspringdefenseproject.client;

import io.ethertale.reasonanddominationspringdefenseproject.client.dto.TimerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "timer-service", url = "http://localhost:8081/timers")
public interface TimerClient {

    @GetMapping("/{type}")
    TimerDTO getTimer(@PathVariable("type") String type);
}
