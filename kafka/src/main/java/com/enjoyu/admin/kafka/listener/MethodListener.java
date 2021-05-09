package com.enjoyu.admin.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.enjoyu.admin.kafka.config.KafkaConfig.GROUP_T_1;
import static com.enjoyu.admin.kafka.config.KafkaConfig.TOPIC_T_1;

@Component
public class MethodListener {
    @KafkaListener(
            id = "one",
            topics = {TOPIC_T_1},
            groupId = GROUP_T_1,
            errorHandler = "loggedErrorHandler",
            properties = {
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG + "=org.apache.kafka.common.serialization.StringDeserializer",
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG + "=org.springframework.kafka.support.serializer.JsonDeserializer"
            }
    )
    public void one() {

    }
}
