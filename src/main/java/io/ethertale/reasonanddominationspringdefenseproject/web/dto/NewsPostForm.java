package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsPostForm {
    @Size(min = 1, max = 100)
    private String title;
    @Size(min = 1, max = 100000)
    private String content;

}
