package com.tool.navigator.controller;

import com.tool.navigator.constant.ActionsMapping;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@DependsOn("staticPropertiesConfig")
public class IndexController {

    @GetMapping("/")
    public String index() {
        return ActionsMapping.INDEX;
    }
}
