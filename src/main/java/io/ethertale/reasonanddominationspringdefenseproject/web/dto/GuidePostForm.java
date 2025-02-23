package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuidePostForm {
    private String title;
    private String content;
    private PostType postType;

}
