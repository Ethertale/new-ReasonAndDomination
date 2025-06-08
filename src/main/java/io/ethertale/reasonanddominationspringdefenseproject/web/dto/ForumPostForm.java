package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumPostForm {
    private String title;
    private String content;
    private UUID creatorId;
}
