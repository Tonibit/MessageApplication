package com.verbitskiy.springbootmessage.service;

import com.verbitskiy.springbootmessage.domain.Message;
import com.verbitskiy.springbootmessage.domain.Person;
import com.verbitskiy.springbootmessage.dto.MessageDTO;
import com.verbitskiy.springbootmessage.dto.form.RequestJsonForm;
import com.verbitskiy.springbootmessage.exception.PersonNotFoundException;
import com.verbitskiy.springbootmessage.exception.WrongMessageFormatException;
import com.verbitskiy.springbootmessage.repository.MessageRepository;
import com.verbitskiy.springbootmessage.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageServiceImpl messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private PersonRepository personRepository;

    private List<Message> messageList;

    @Before
    public void setUp(){
        //Лист для mock объекта репозитория
        messageList = new ArrayList<>();
        Message message1 = new Message();
        message1.setText("text");
        Message message2 = new Message();
        message2.setText("newtext");
        Message message3 = new Message();
        message3.setText("sometext");
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);

    }

    @Test
    public void testSaveMessageWithPersonInDB() {
        //создаем форму, которая приходит в метод сохранения сообщения на MessageService
        RequestJsonForm formRequest = new RequestJsonForm();
        formRequest.setMessage("Some message");
        formRequest.setUsername("MyName");

        Person person = new Person();
        person.setUsername(formRequest.getUsername());
        person.setMessages(new ArrayList<>());
        Mockito.when(personRepository.findByUsername(formRequest.getUsername())).thenReturn(Optional.of(person));
        Message message = new Message();
        message.setText(formRequest.getMessage());
        Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(message);

        //вызываем метод сервиса и получаем значение
        MessageDTO messageDTO = messageService.saveMessage(formRequest);

        Assert.assertEquals(formRequest.getMessage(),messageDTO.getText());
        Assert.assertNotNull(messageDTO);
        Mockito.verify(messageRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Message.class));
        Mockito.verify(personRepository, Mockito.times(1))
                .findByUsername(formRequest.getUsername());

    }

    @Test
    public void testGetMessagesWithRightRequestMessage() {
        //создаем форму, которая приходит в метод сохранения сообщения на MessageService
        RequestJsonForm formRequest = new RequestJsonForm();
        formRequest.setMessage("history 5");
        formRequest.setUsername("MyName");

        Person person = new Person();
        person.setUsername(formRequest.getUsername());
        person.setId(1L);
        person.setMessages(new ArrayList<>());

        //Поведение мок объектов при обращнии из сервиса.
        Mockito.when(personRepository.findByUsername(formRequest.getUsername()))
                .thenReturn(Optional.of(person));
        Mockito.when(messageRepository.findByValue(person.getId(), 5)).thenReturn(messageList);

        List<MessageDTO> list = messageService.getMessages(formRequest);

        //Проверяем что лист не пустой и вызваны методы в моках нужное кол-во раз
        Assert.assertFalse(list.isEmpty());
        Mockito.verify(messageRepository, Mockito.times(1)).findByValue(person.getId(), 5);
        Mockito.verify(personRepository, Mockito.times(1))
                .findByUsername(formRequest.getUsername());

        //Объекты и лист для проверки
        MessageDTO message1 = new MessageDTO();
        message1.setText("text");
        MessageDTO message2 = new MessageDTO();
        message2.setText("newtext");
        MessageDTO message3 = new MessageDTO();
        message3.setText("sometext");
        List<MessageDTO> checkList = List.of(message1, message2, message3);


        Assert.assertEquals(checkList.size(), list.size());

        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(checkList.get(i).getText(), list.get(i).getText());
        }
    }

    @Test(expected = WrongMessageFormatException.class)
    public void testGetMessagesWithWrongRequestMessage() {
        //создаем форму, которая приходит в метод сохранения сообщения на MessageService
        RequestJsonForm formRequest = new RequestJsonForm();
        formRequest.setMessage("history of smth");
        formRequest.setUsername("MyName");

        List<MessageDTO> list = messageService.getMessages(formRequest);
    }

    @Test(expected = PersonNotFoundException.class)
    public void testGetMessageWhenUserIsNotInDataBase() {
        RequestJsonForm formRequest = new RequestJsonForm();
        formRequest.setMessage("some message");
        formRequest.setUsername("MyName");

        Mockito.when(personRepository.findByUsername(formRequest.getUsername())).thenReturn(Optional.empty());

        MessageDTO messageDTO = messageService.saveMessage(formRequest);

    }
}


