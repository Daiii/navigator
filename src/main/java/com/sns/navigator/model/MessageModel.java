package com.sns.navigator.model;

import lombok.Data;

@Data
public class MessageModel {

    private String propertiesName;

    private String topic;

    private String headers;

    private String message;
}
