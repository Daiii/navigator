package com.sns.navigator.controller;

import com.sns.navigator.constant.Actions;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@DependsOn("staticPropertiesConfig")
public class IndexController {

    @GetMapping("/")
    public String index() {
        return Actions.INDEX;
    }
}
