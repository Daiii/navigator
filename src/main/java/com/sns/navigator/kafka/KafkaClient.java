package com.sns.navigator.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;

import com.sns.navigator.model.MessageModel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * kafka操作类
 *
 * @author zhangbo
 * @since 1.0.0
 */
public class KafkaClient {

    /**
     * 发送kafka消息
     *
     * @param properties 生产者配置文件
     * @param model MessageModel
     * @return RecordMetadata
     */
    public static RecordMetadata sendMessage(Properties properties, MessageModel model) {
        String message = model.getMessage();
        String topic = model.getTopic();
        String headers = model.getHeaders();
        if (StrUtil.isEmpty(topic) || StrUtil.isEmpty(message)) {
            throw new RuntimeException("Topic and Message can not be empty.");
        }

        if (StrUtil.isNotEmpty(headers) && !JSONUtil.isTypeJSON(headers)) {
            throw new RuntimeException("Headers must be json type.");
        }

        RecordMetadata result = null;
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            if (StrUtil.isNotEmpty(headers)) {
                JSONObject jsonObject = JSONUtil.parseObj(headers);
                jsonObject.keySet().forEach(key -> {
                    record.headers().add(new Header() {
                        @Override
                        public String key() {
                            return key;
                        }

                        @Override
                        public byte[] value() {
                            return jsonObject.getStr(key).getBytes();
                        }
                    });
                });
            }
            result = producer.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}