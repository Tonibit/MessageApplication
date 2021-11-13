package com.verbitskiy.springbootmessage.config.filter;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//Класс чтобы скрыть/убрать из кода секретный ключ для генерации токена
@Configuration
@Getter
public class JwtKey {
    @Value("${secret.key}")
    private String jwtSecret;
}
