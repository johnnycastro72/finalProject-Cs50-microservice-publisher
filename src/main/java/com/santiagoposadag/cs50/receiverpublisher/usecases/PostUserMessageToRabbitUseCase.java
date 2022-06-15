package com.santiagoposadag.cs50.receiverpublisher.usecases;

import com.google.gson.Gson;
import com.santiagoposadag.cs50.receiverpublisher.config.RabbitMQConfig;
import com.santiagoposadag.cs50.receiverpublisher.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class PostUserMessageToRabbitUseCase implements Function<UserDto, Mono<UserDto>>  {

    private RabbitTemplate rabbitTemplate;
    private Gson gson = new Gson();

    public PostUserMessageToRabbitUseCase(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<UserDto> apply(@Validated UserDto userDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, UserDto.getRoutingKey(), gson.toJson(userDto));
        return Mono.just(userDto);
    }
}
