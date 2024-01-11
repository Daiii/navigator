package com.sns.navigator.controller;

import com.sns.navigator.config.StaticPropertiesConfig;
import com.sns.navigator.constant.Actions;
import com.sns.navigator.model.PropertiesMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
@DependsOn("staticPropertiesConfig")
public class StaticPropertiesController {

    private final StaticPropertiesConfig staticPropertiesConfig;

    @GetMapping(Actions.LIST)
    public String list(ModelMap model) {
        model.addAttribute("configs", StaticPropertiesConfig.ALL.keySet());
        return Actions.LIST;
    }

    @GetMapping(Actions.INFO)
    public String info(String propertiesName, ModelMap model) {
        Map<String, String> properties = staticPropertiesConfig.mapProperties(propertiesName);
        model.addAttribute("properties", properties);
        return Actions.INFO;
    }

    @GetMapping(Actions.CREATE)
    public String create() {
        return Actions.CREATE;
    }

    @PostMapping(Actions.CREATE_PROPERTIES)
    public String createProperties(@ModelAttribute("form") PropertiesMode formData, ModelMap model) {
        String fileName = formData.getFileName();
        String content = formData.getContent();
        Properties properties = new Properties();
        if (!fileName.contains(".properties")) {
            throw new RuntimeException("fileName must be xxx.properties.");
        }

        if (StaticPropertiesConfig.ALL.containsKey(fileName)) {
            throw new RuntimeException("file name it's repeated.");
        }

        try {
            properties.load(new ByteArrayInputStream(content.getBytes()));
            StaticPropertiesConfig.ALL.put(fileName, properties);
            staticPropertiesConfig.writeProperties(fileName, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list(model);
    }

    @Autowired
    public StaticPropertiesController(StaticPropertiesConfig staticPropertiesConfig) {
        this.staticPropertiesConfig = staticPropertiesConfig;
    }
}
