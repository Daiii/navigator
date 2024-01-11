package com.sns.navigator.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 静态properties配置类
 *
 * @author zhangbo
 * @since 1.0.0
 */
public class StaticPropertiesConfig implements InitializingBean {

    static final String PROPERTIES_PATH = System.getProperty("user.dir") + "/src/main/resources/static/kafka/";

    public static final Map<String, Properties> ALL = new LinkedHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        loadAll();
    }

    /**
     * 加载所有properties
     */
    private void loadAll() {
        List<String> fileNames = FileUtil.listFileNames(PROPERTIES_PATH);
        fileNames.forEach(fileName -> {
            if (fileName.contains(".properties")) {
                ALL.put(fileName, createProperties(fileName));
            }
        });
    }

    /***
     * 创建properties
     *
     * @param fileName 文件名
     * @return properties
     */
    private Properties createProperties(String fileName) {
        Properties properties = new Properties();
        String str = FileUtil.readUtf8String(PROPERTIES_PATH + fileName);
        try {
            properties.load(new ByteArrayInputStream(str.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * properties转hashmap
     *
     * @param propertiesName 文件名
     * @return LinkedHashMap
     */
    public Map<String, String> mapProperties(String propertiesName) {
        Map<String, String> props = new LinkedHashMap<>();
        Properties properties = ALL.get(propertiesName);
        properties.keySet().forEach(key -> {
            props.put(StrUtil.toString(key), properties.getProperty(StrUtil.toString(key)));
        });
        return props;
    }

    /**
     * 写入文件
     *
     * @param fileName 文件名
     * @param content  文件内容
     */
    public void writeProperties(String fileName, String content) {
        try {
            IoUtil.writeUtf8(new FileOutputStream(PROPERTIES_PATH + fileName), true, content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
