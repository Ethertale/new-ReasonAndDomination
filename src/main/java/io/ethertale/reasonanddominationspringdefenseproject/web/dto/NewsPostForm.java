package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsPostForm {
    private String title;
    private String content;

}
