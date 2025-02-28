package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditProfile {
    @NotNull
    private String profilePicture;

}
