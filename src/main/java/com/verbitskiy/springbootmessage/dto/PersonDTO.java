package com.verbitskiy.springbootmessage.dto;

import com.verbitskiy.springbootmessage.domain.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {

    private String username;

    public PersonDTO(Person person) {
        username = person.getUsername();
    }

}
