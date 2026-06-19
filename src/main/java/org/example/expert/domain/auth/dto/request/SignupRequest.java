package org.example.expert.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    //level 1-2 → 닉네임 추가
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String userRole;
    @NotBlank
    private String nickname;
}
