package com.enjoyu.admin.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Slf4j
@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
public class KafkaConfig {

    public static final String TOPIC_T_1 = "test1";
    public static final String GROUP_T_1 = "group1";

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(TOPIC_T_1)
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate(ProducerFactory<String, String> producerFactory) {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        return new KafkaTemplate<>(producerFactory, configs);
    }

    /**
     * 异常处理器
     */
    @Bean
    public ConsumerAwareListenerErrorHandler loggedErrorHandler() {
        return (message, e, consumer) -> {
            log.info("message:{}", message.getPayload());
            log.error("exception while listening", e);
            return null;
        };
    }
}
