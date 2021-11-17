package com.verbitskiy.springbootmessage.dto.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsernameAndPasswordRequestForm {

    private String username;
    private String password;
}

