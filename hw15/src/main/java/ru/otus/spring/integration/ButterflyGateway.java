package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Egg;

import java.util.List;

@MessagingGateway
public interface ButterflyGateway {

    @Gateway(requestChannel = "eggsChannel", replyChannel = "butterflyChannel")
    List<Butterfly> process(List<Egg> egg);
}
