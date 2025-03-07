package io.ethertale.reasonanddominationspringdefenseproject.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerDTO {
    private String type;
    private LocalDateTime endTime;
}
