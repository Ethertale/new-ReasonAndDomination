package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import lombok.Data;

@Data
public class EditProfile {
    private String profilePicture;
    private AccountRole role;

}
