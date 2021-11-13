package com.verbitskiy.springbootmessage.service;

import com.verbitskiy.springbootmessage.domain.Person;
import com.verbitskiy.springbootmessage.dto.PersonDTO;
import com.verbitskiy.springbootmessage.dto.form.RegistrationPersonForm;
import com.verbitskiy.springbootmessage.exception.PersonNotFoundException;
import com.verbitskiy.springbootmessage.repository.PersonRepository;
import com.verbitskiy.springbootmessage.service.interfaces.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private RegistrationPersonForm personForm;
    private Person person;

    @Before
    public void serUp() {

        personForm = new RegistrationPersonForm("Bob", "pass");

        person = new Person();
        person.setId(1L);
        person.setUsername(personForm.getUsername());
        person.setPassword(passwordEncoder.encode(personForm.getPassword()));
    }

    @Test
    public void testRegisterPersonWithNewUsername() {

        //даем поведение мок объекту - возвращаем пустой Optional, так как предполагаем, что в БД нет такого имени
        Mockito.when(personRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        //При обращении на сохранение к моку репозитория возвращаем уже созданный объект в setUp
        Mockito.when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDTO personDTO = personService.registerPerson(personForm);

        //Проверяем что полученный объект непустой и что имена совпадют
        Assert.assertNotNull(personDTO);
        Assert.assertEquals(personForm.getUsername(), personDTO.getUsername());
        //Проверям сколько вызывались методы мок объекта.
        Mockito.verify(personRepository, Mockito.times(1)).findByUsername(anyString());
        Mockito.verify(personRepository, Mockito.times(1)).save(any(Person.class));
    }

    @Test(expected = PersonNotFoundException.class)
    public void testRegisterPersonWithUsernameExistInDataBase() {
        //Имитируем поведение мок репозитория, будто он вернул на запрос по имени пользователя из БД
        Mockito.when(personRepository.findByUsername(anyString())).thenReturn(Optional.of(person));

        PersonDTO personDTO = personService.registerPerson(personForm);
    }
}
