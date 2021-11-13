package com.verbitskiy.springbootmessage.service.interfaces;

import com.verbitskiy.springbootmessage.dto.PersonDTO;
import com.verbitskiy.springbootmessage.dto.form.RegistrationPersonForm;

public interface PersonService {

    PersonDTO registerPerson(RegistrationPersonForm personForm);
}
