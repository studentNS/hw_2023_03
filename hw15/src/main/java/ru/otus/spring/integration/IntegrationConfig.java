package ru.otus.spring.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.PublishSubscribeChannelSpec;
import org.springframework.integration.dsl.QueueChannelSpec;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.domain.Caterpillar;
import ru.otus.spring.service.BirdService;
import ru.otus.spring.service.TransformService;

@RequiredArgsConstructor
@Configuration
public class IntegrationConfig {

    private final TransformService transformService;

    @Bean
    public QueueChannelSpec eggsChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public PublishSubscribeChannelSpec<?> caterpillarChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public PublishSubscribeChannelSpec<?> chrysalisChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public PublishSubscribeChannelSpec<?> butterflyChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public PublishSubscribeChannelSpec<?> birdChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(20);
    }

    @Bean
    public IntegrationFlow birdFlow(BirdService birdService) {
        return IntegrationFlow.from(birdChannel())
                .split()
                .handle(birdService, "attack")
                .get();
    }


    @Bean
    public IntegrationFlow eggToCaterpillarFlow() {
        return IntegrationFlow.from(eggsChannel())
                .split()
                .handle(transformService, "transformToCaterpillar")
                .<Caterpillar, Boolean> route(number -> number.getEggOrder() % 3 != 0,
                        mapping -> mapping
                                .channelMapping(true, "caterpillarChannel")
                                .channelMapping(false, "birdChannel")).get();

    }

    @Bean
    public IntegrationFlow caterpillarToChrysalisFlow() {
        return IntegrationFlow.from(caterpillarChannel())
                .handle(transformService, "transformToChrysalis")
                .channel(chrysalisChannel())
                .get();
    }

    @Bean
    public IntegrationFlow chrysalisToButterflyFlow() {
        return IntegrationFlow.from(chrysalisChannel())
                .handle(transformService, "transformToButterfly")
                .channel(butterflyChannel())
                .get();
    }
}