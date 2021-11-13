package com.verbitskiy.springbootmessage.controller;

import com.verbitskiy.springbootmessage.dto.PersonDTO;
import com.verbitskiy.springbootmessage.dto.form.RegistrationPersonForm;
import com.verbitskiy.springbootmessage.repository.PersonRepository;
import com.verbitskiy.springbootmessage.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Добавлен эндпоинт для регистрации пользователя - происходит добавление в БД данных пользователя,
 * если имя уникально.
 * Логин происходит с помошью POST запросы на /login (дефолтный урл у spring security) - ждёт формат JSON
 * и возвращает JSON со сгенерырованным токеном, если пользователь есть в бд и соответствует пароль и логин.*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<PersonDTO> registerPerson(@RequestBody @Valid RegistrationPersonForm personForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.registerPerson(personForm));
    }
}
