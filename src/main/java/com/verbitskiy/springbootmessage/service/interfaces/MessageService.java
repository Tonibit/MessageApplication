package com.verbitskiy.springbootmessage.service.interfaces;

import com.verbitskiy.springbootmessage.domain.Message;
import com.verbitskiy.springbootmessage.dto.MessageDTO;
import com.verbitskiy.springbootmessage.dto.form.RequestJsonForm;

import java.util.List;

public interface MessageService {

    List<MessageDTO> getMessages(RequestJsonForm form);

    MessageDTO saveMessage(RequestJsonForm form);
}
