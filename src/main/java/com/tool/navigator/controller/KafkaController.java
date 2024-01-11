package com.tool.navigator.controller;

import java.util.HashSet;
import java.util.Properties;

import com.tool.navigator.config.StaticPropertiesConfig;
import com.tool.navigator.kafka.KafkaClient;
import com.tool.navigator.model.MessageModel;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tool.navigator.constant.Actions;

@Controller
@DependsOn("staticPropertiesConfig")
public class KafkaController {

    @GetMapping(Actions.SEND)
    public String send(ModelMap model) {
        model.addAttribute("configs", new HashSet<String>(StaticPropertiesConfig.ALL.keySet()));
        return Actions.SEND;
    }

    @PostMapping(Actions.SEND_MESSAGE)
    public String sendMessage(@ModelAttribute("form") MessageModel formData, ModelMap model) {
        Properties properties = StaticPropertiesConfig.ALL.get(formData.getPropertiesName());
        RecordMetadata recordMetadata = KafkaClient.sendMessage(properties, formData);
        model.addAttribute("state", "success");
        model.addAttribute("message", recordMetadata.toString());
        return Actions.RESULT;
    }
}
