package com.verbitskiy.springbootmessage.dto;

import com.verbitskiy.springbootmessage.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {

    String text;

    public MessageDTO(Message message) {
        text = message.getText();
    }


}
