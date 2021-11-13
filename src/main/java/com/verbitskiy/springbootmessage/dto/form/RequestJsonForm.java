package com.verbitskiy.springbootmessage.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestJsonForm {
    @NotBlank
    String username;
    @NotBlank
    String message;
}
