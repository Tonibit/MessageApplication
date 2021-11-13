package com.verbitskiy.springbootmessage.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationPersonForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
