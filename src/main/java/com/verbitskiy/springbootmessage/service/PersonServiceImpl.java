package com.verbitskiy.springbootmessage.service;

import com.verbitskiy.springbootmessage.domain.Person;
import com.verbitskiy.springbootmessage.dto.PersonDTO;
import com.verbitskiy.springbootmessage.dto.form.RegistrationPersonForm;
import com.verbitskiy.springbootmessage.exception.PersonNotFoundException;
import com.verbitskiy.springbootmessage.repository.PersonRepository;
import com.verbitskiy.springbootmessage.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, UserDetailsService {


    @Autowired
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("no username"));
        return new User(person.getUsername(), person.getPassword(), new ArrayList<GrantedAuthority>());
    }

    @Override
    @Transactional
    public PersonDTO registerPerson(RegistrationPersonForm personForm) {
        if (personRepository.findByUsername(personForm.getUsername()).isPresent()) {
            throw new PersonNotFoundException("Person with username " + personForm.getUsername() +
                    " exist. Create another one.");
        }
        Person person = new Person();
        person.setUsername(personForm.getUsername());
        person.setPassword(passwordEncoder.encode(personForm.getPassword()));
        person.setMessages(new ArrayList<>());
        return new PersonDTO(personRepository.save(person));
    }
}
