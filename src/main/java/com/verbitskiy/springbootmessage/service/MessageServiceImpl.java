package com.verbitskiy.springbootmessage.service;

import com.verbitskiy.springbootmessage.domain.Message;
import com.verbitskiy.springbootmessage.domain.Person;
import com.verbitskiy.springbootmessage.dto.MessageDTO;
import com.verbitskiy.springbootmessage.dto.form.RequestJsonForm;
import com.verbitskiy.springbootmessage.exception.PersonNotFoundException;
import com.verbitskiy.springbootmessage.exception.WrongMessageFormatException;
import com.verbitskiy.springbootmessage.repository.MessageRepository;
import com.verbitskiy.springbootmessage.repository.PersonRepository;
import com.verbitskiy.springbootmessage.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<MessageDTO> getMessages(RequestJsonForm form) {
        String messageValue = form.getMessage();
        //проверяем верный ли формат сообщения на получение полученный из JSON
        if (!messageValue.matches("history [0-9]+")) {
            throw new WrongMessageFormatException("Wrong format. should be for example \"history 10\"");
        }
        //получаем кол-во необходимых сообщения для пользователя
        int value = Integer.parseInt(messageValue.substring(messageValue.indexOf(" ") + 1));

        Person person = personRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new PersonNotFoundException("username doesn't exist"));

        return messageRepository.findByValue(person.getId(), value).stream()
                .map(MessageDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MessageDTO saveMessage(RequestJsonForm form) {
        Person person = personRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new PersonNotFoundException("username doesn't exist"));
        Message message = new Message();
        message.setText(form.getMessage());
        message.setPerson(person);
        return new MessageDTO(messageRepository.save(message));
    }
}
